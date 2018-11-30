package com.zhihangjia.mainmodule.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.util.DateUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.SetCouponCB;
import com.zhihangjia.mainmodule.databinding.ActivitySetCouponBinding;
import com.zhihangjia.mainmodule.viewmodel.SetCouponVM;
import java.util.Calendar;
import java.util.Date;

public class SetCouponActivity extends BaseTitleActivity implements SetCouponCB {
    private final String TAG = SetCouponActivity.class.getSimpleName();

    private SetCouponVM vm;
    private ActivitySetCouponBinding binding;
    private WheelPickerWindow useConditionWindow;
    private WheelPickerWindow timeTypeWindow;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new SetCouponVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "设置优惠券";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySetCouponBinding) baseViewBinding;
        binding.setVm(vm);
        vm.getData();
        binding.rvTimeBegin.setVisibility(View.GONE);
        binding.rvTimeEnd.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(vm.coupon_id)) {
            baseTitleBinding.tTitle.inflateMenu(R.menu.delete);
        }
        addAnimation();
        setListener();
        binding.swStatus.setChecked("1".equals(vm.couponSeller.get().getStatus())?true:false);
    }

    private void setListener() {
        binding.swStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vm.couponSeller.get().setStatus(isChecked?"1":"0");
        });
        baseTitleBinding.tTitle.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.delete) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("是否删除").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        vm.delete();
                    }
                }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
                int width = DensityUtil.dpToPx(this, 340);
                alertDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            return true;
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_coupon;
    }

    @Override
    public void showUseConditionWindow() {
        if (useConditionWindow == null) {
            useConditionWindow = new WheelPickerWindow(this, vm.getUseConditionList(), vm);
        }

        useConditionWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void chooseTimeTypeWindow() {
        if (timeTypeWindow == null) {
            timeTypeWindow = new WheelPickerWindow(this, vm.getTimeTypeList(), vm);
        }

        timeTypeWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择开始日期
     * inp 日期间连接符，如 '/' 或者 '-'
     */
    @Override
    public void showDateStartSelected(final String inp) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance((datePickerDialog, i, i1, i2) -> {
                    String temp = i + inp + ((i1 + 1) < 10 ? "0" + (i1 + 1) : (i1 + 1)) + inp + (i2 < 10 ? "0" + i2 : i2);
                    String format = "yyyy-MM-dd";
                    if ("/".equals(inp)) {
                        format = "yyyy/MM/dd";
                    }
                    //设置开始时间
                    Date date = DateUtil.strToDate(temp, format);
                    if (!TextUtils.isEmpty(vm.couponSeller.get().getEndtime())) {
                        Date enddate = DateUtil.strToDate(vm.couponSeller.get().getEndtime(), format);
                        //判断开始时间是否大于结束时间,清空结束时间
                        if (date.getTime() > enddate.getTime()) {
                            vm.couponSeller.get().setEndtime("");
                        }
                    }
                    vm.couponSeller.get().setStarttime(temp);
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "日期选择");
    }

    /**
     * 选择结束日期
     * inp 日期间连接符，如 '/' 或者 '-'
     */
    @Override
    public void showDateEndSelected(final String inp) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance((datePickerDialog, i, i1, i2) -> {
                    String temp = i + inp + ((i1 + 1) < 10 ? "0" + (i1 + 1) : (i1 + 1)) + inp + (i2 < 10 ? "0" + i2 : i2);
                    String format = "yyyy-MM-dd";
                    if ("/".equals(inp)) {
                        format = "yyyy/MM/dd";
                    }
                    //设置结束时间
                    Date date = DateUtil.strToDate(temp, format);
                    if (!TextUtils.isEmpty(vm.couponSeller.get().getStarttime())) {
                        Date enddate = DateUtil.strToDate(vm.couponSeller.get().getStarttime(), format);
                        //判断开始时间是否大于结束时间
                        if (date.getTime() < enddate.getTime()) {
                            showToast("结束时间不可大于开始时间");
                            return;
                        }
                    }
                    vm.couponSeller.get().setEndtime(temp);
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "日期选择");
    }
}

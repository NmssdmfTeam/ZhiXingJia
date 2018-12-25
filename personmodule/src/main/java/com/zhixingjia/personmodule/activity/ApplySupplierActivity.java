package com.zhixingjia.personmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.view.ImageSelectView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.ApplySupplierCB;
import com.zhixingjia.personmodule.databinding.ActivityApplySupplierBinding;
import com.zhixingjia.personmodule.viewmodule.ApplySupplierVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 成为卖家
 */
public class ApplySupplierActivity extends BaseTitleActivity implements ApplySupplierCB, WheelPickerWindowCB {
    private final String TAG = ApplySupplierActivity.class.getSimpleName();
    private ApplySupplierVM vm;
    private WheelPickerWindow businessCirclePopWindow;
    private ActivityApplySupplierBinding binding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ApplySupplierVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "申请店铺";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityApplySupplierBinding) baseViewBinding;
        businessCirclePopWindow = new WheelPickerWindow(this,new ArrayList<String>(),this);
        if (vm.isFromSupplier.get()) {
            setTitle("公司信息");
        }
        if (vm.frontIDCardPathUploadBean != null && !TextUtils.isEmpty(vm.frontIDCardPathUploadBean.getS_url())) {
            loadFrontIDCardImg(vm.frontIDCardPathUploadBean.getM_url());
        }
        if (vm.backIDCardPathUploadBean != null && !TextUtils.isEmpty(vm.backIDCardPathUploadBean.getS_url())) {
            loadBackIDCardImg(vm.backIDCardPathUploadBean.getM_url());
        }
        if (vm.businessLicenseCardPathUploadBean != null && !TextUtils.isEmpty(vm.businessLicenseCardPathUploadBean.getS_url())) {
            loadLicenseImg(vm.businessLicenseCardPathUploadBean.getM_url());
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_supplier;
    }

    @Override
    public void showBusinessCirclePopWindow() {
        businessCirclePopWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM,0,0);
    }

    @Override
    public void setPickViewData(List<String> tradeArea) {
        businessCirclePopWindow.changeDataList(tradeArea);
        binding.setVm(vm);

    }

    @Override
    public void loadFrontIDCardImg(String filepath) {
        GlideUtil.load(binding.ivFrontIDCard,filepath);
    }

    @Override
    public void loadBackIDCardImg(String filepath) {
        GlideUtil.load(binding.ivBackIDCard,filepath);
    }

    @Override
    public void loadLicenseImg(String filepath) {
        GlideUtil.load(binding.ivLiscense,filepath);
    }

    @Override
    public void tvSureClick(String item, int position) {
        vm.applySupplier.get().setTrade_area_name(item);
        vm.applySupplier.get().setTrade_area(vm.businessCircleList.get(position).getTrade_id());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        vm.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void showSelectImageDialog(String filepath) {
        ImageSelectView.showAddImageDialog(this, 1, filepath);
    }
}

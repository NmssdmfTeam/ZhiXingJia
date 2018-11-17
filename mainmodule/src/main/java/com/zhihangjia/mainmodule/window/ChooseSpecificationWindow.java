package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.ChooseSpecificationWindowCB;
import com.zhihangjia.mainmodule.databinding.WindowChooseSpecificationBinding;
import com.zhihangjia.mainmodule.viewmodel.ChooseShopCouponWindowVM;
import com.zhihangjia.mainmodule.viewmodel.ChooseSpecificationWindowVM;

/**
 * 商品详情 选择规格
 */
public class ChooseSpecificationWindow extends PopupWindow implements ChooseSpecificationWindowCB{

    private ChooseSpecificationWindowVM vm;

    public ChooseSpecificationWindow(final Context context){
        WindowChooseSpecificationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_choose_specification, null, false);
        setContentView(binding.getRoot());
        vm = new ChooseSpecificationWindowVM(this);
        binding.setVm(vm);
        setWidth(DensityUtil.dpToPx(context, 375));
        setHeight(DensityUtil.dpToPx(context, 477.5f));

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowUtil.setBackgroundAlpha((Activity) context, 1);
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }

    @Override
    public void closeWindow() {
        dismiss();
    }
}

package com.zhihangjia.mainmodule.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.bean.Payment;
import com.nmssdmf.commonlib.util.AlipayUtil;
import com.nmssdmf.commonlib.util.WeChatPayUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.ConfirmPayCB;
import com.zhihangjia.mainmodule.databinding.ActivityConfirmPayBinding;
import com.zhihangjia.mainmodule.viewmodel.ConfirmPayVM;
import com.zhihangjia.mainmodule.window.ChooseCouponWindow;

public class ConfirmPayActivity extends BaseTitleActivity implements ConfirmPayCB {
    private final String TAG = ConfirmPayActivity.class.getSimpleName();
    private ConfirmPayVM vm;
    private ActivityConfirmPayBinding binding;
    private ChooseCouponWindow chooseCouponWindow;


    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ConfirmPayVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "确认付款";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityConfirmPayBinding) baseViewBinding;
        vm.payInfo();
    }

    @Override
    public void showCouponWindow(boolean refresh) {
        if (chooseCouponWindow == null) {
            chooseCouponWindow = new ChooseCouponWindow(this, ChooseCouponWindow.TYPE_MERCHANT,  vm);
        }
        chooseCouponWindow.refreshAdapter(refresh, vm.coupons);
        chooseCouponWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void aliPay(String payment) {
        AlipayUtil.getInstance().pay(this, payment);
    }

    @Override
    public void wechatPay(Payment.Weixin payReq) {
        WeChatPayUtil.getInstance().pay(this, payReq);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_pay;
    }

    @Override
    public void setListener() {
        binding.setVm(vm);
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AlipayUtil.PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    showToast("无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        showToast("无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                        return;
                    }
                }

                // 所需的权限均正常获取
                showToast("支付宝 SDK 所需的权限已经正常获取");
            }
        }
    }
}

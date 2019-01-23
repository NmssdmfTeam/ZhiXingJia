package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.viewmodel.CouponDescriptionVM;
import com.zhihangjia.mainmodule.databinding.ActivityCouponDescriptionBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CouponDescriptionActivity extends BaseTitleActivity {
    private final String TAG = CouponDescriptionActivity.class.getSimpleName();
    private CouponDescriptionVM vm;
    private ActivityCouponDescriptionBinding binding;

    @Override
    public String setTitle() {
        return "票券说明";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityCouponDescriptionBinding) baseViewBinding;
        binding.setVm(vm);
        float persent = 100;
        if (!vm.centerCoupon.get().getAllsum().equals(vm.centerCoupon.get().getReceive_sum())) {
            persent = new BigDecimal(vm.centerCoupon.get().getReceive_sum())
                    .divide(new BigDecimal(vm.centerCoupon.get().getAllsum()), 3, RoundingMode.DOWN)
                    .multiply(new BigDecimal(100)).floatValue();
        }
        if (persent < 1) {
            persent = 1;
        }
        persent = new BigDecimal(persent).setScale(0, RoundingMode.DOWN).floatValue();
        binding.tvRecieveNum.setText("已抢"+(int)persent+"%");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_coupon_description;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new CouponDescriptionVM(this);
        return vm;
    }

    @Override
    public void onBackPressed() {
        if (vm.isNeedFeedBack) {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setIndex(vm.position);
            eventInfo.setContent(vm.centerCoupon.get());
            RxBus.getInstance().send(RxEvent.CouponEvent.COUPON_RECIEVE, eventInfo);
        }
        super.onBackPressed();
    }
}

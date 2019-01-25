package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.MyCouponCardAdapter;
import com.zhihangjia.mainmodule.callback.MyCouponCardFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.MyCouponCardFragmentVM;
import com.zhihangjia.mainmodule.window.QrCodeWindow;
import com.zhixingjia.bean.mainmodule.CouponCard;

import java.util.List;

/**
* @description 我的卡券
* @author chenbin
* @date 2019/1/24 14:34
* @version v3.2.0
*/
public class MyCouponCardFragmentFragment extends BaseRecyclerViewFragment implements MyCouponCardFragmentCB,MyCouponCardAdapter.OnItemClickListener {
    private final String TAG = MyCouponCardFragmentFragment.class.getSimpleName();
    private MyCouponCardFragmentVM vm;
    private MyCouponCardAdapter adapter;
    private int type = 0;
    private QrCodeWindow qrCodeWindow;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MyCouponCardFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MyCouponCardAdapter(list,this);
        return adapter;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(IntentConfig.TYPE);
        }
        vm.setType(type);
        super.initAll(view, savedInstanceState);
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.initData(true);
                RxBus.getInstance().send(RxEvent.CouponEvent.REFRESH_MY_COUPON_TICKET_NUM, null);
            }

            @Override
            public void onLoadMore() {
                vm.initData(false);
            }
        });
        qrCodeWindow = new QrCodeWindow(getActivity());
        binding.getRoot().setBackgroundResource(R.color.activity_grey_bg);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public String getPage() {
        if (adapter.getData() == null || adapter.getData().size() == 0)
            return "0";
        return adapter.getData().get(adapter.getData().size() - 1).getCode_id();
    }

    @Override
    public void onUseClick(CouponCard item) {
        qrCodeWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
        qrCodeWindow.loadImage(item.getQrcode());
    }
}

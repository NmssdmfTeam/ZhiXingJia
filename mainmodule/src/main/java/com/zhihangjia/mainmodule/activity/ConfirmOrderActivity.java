package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ConfirmOrderAdapter;
import com.zhihangjia.mainmodule.callback.ConfirmOrderAdapterCB;
import com.zhihangjia.mainmodule.callback.ConfirmOrderCB;
import com.zhihangjia.mainmodule.databinding.ActivityConfirmOrderBinding;
import com.zhihangjia.mainmodule.databinding.HeaderConfirmOrderBinding;
import com.zhihangjia.mainmodule.viewmodel.ConfirmOrderVM;
import com.zhihangjia.mainmodule.window.ChooseCouponWindow;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;
import com.zhixingjia.bean.personmodule.Coupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseTitleActivity implements ConfirmOrderCB, ConfirmOrderAdapterCB, WheelPickerWindowCB {
    private final String TAG = ConfirmOrderActivity.class.getSimpleName();
    private ActivityConfirmOrderBinding binding;
    private ConfirmOrderVM vm;

    private ConfirmOrderAdapter adapter;
    private WheelPickerWindow deliveryMethodWindow;
    private ChooseCouponWindow chooseCouponWindow;

    private HeaderConfirmOrderBinding headerBinding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ConfirmOrderVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "确认订单";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityConfirmOrderBinding) baseViewBinding;
        binding.setVm(vm);

        vm.getIntentData();
        adapter = new ConfirmOrderAdapter(new ArrayList<>(), this);
        headerBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.header_confirm_order, null, false);
        headerBinding.setData(new CommodityComfirm.AddressInfoBean());
        headerBinding.setVm(vm);
        headerBinding.setData(new CommodityComfirm.AddressInfoBean());
        adapter.addHeaderView(headerBinding.getRoot());
        binding.crv.setAdapter(adapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void chooseDeliveryMethod(int position) {
        if (deliveryMethodWindow == null) {
            deliveryMethodWindow = new WheelPickerWindow(this, vm.getDeliveryMethodList(), this);
        }
        vm.position = position;
        deliveryMethodWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void chooseCoupon(int position, String id, String money) {
        vm.position = position;
        vm.getCoupons(true, id, money);
    }

    @Override
    public void showCouponWindow(boolean refresh) {
        if (chooseCouponWindow == null) {
            chooseCouponWindow = new ChooseCouponWindow(this, ChooseCouponWindow.TYPE_MERCHANT, vm.getCouponMap().get(vm.position), vm);
        }
        chooseCouponWindow.refreshAdapter(refresh, vm.getCouponMap().get(vm.position));
        chooseCouponWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void setData(List<CommodityComfirm.InfoListBean> infoListBeans, boolean isRefresh) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh,infoListBeans);
    }

    @Override
    public void setAddressData(CommodityComfirm.AddressInfoBean addressData) {
        if (addressData == null) {
            headerBinding.ivAddAddress.setVisibility(View.VISIBLE);
        } else {
            headerBinding.ivAddAddress.setVisibility(View.GONE);
            headerBinding.setData(addressData);
        }
    }

    @Override
    public List<CommodityComfirm.InfoListBean> getPayData() {
        return adapter.getData();
    }

    @Override
    public void refreshCouponList(boolean isRefresh, List<Coupon> list) {

    }

    @Override
    public void tvSureClick(String item, int FreightTypePosition) {
        CommodityComfirm.InfoListBean infoListBean = adapter.getData().get(vm.position - 1);
        adapter.getData().get(vm.position - 1).setFreight_type(FreightTypePosition);
        //重新计算金额
        BigDecimal amount;
        BigDecimal total;
        if (infoListBean.getFreight_type() == 0) {
            amount = new BigDecimal(infoListBean.getProduct_amount()).add(new BigDecimal(infoListBean.getCost_freight()));
            total = new BigDecimal(vm.totalAmount.get()).add(new BigDecimal(infoListBean.getCost_freight()));
        } else {
            amount = new BigDecimal(infoListBean.getProduct_amount()).subtract(new BigDecimal(infoListBean.getCost_freight()));
            total = new BigDecimal(vm.totalAmount.get()).subtract(new BigDecimal(infoListBean.getCost_freight()));
        }
        adapter.getData().get(vm.position - 1).setProduct_amount(amount.toString());
        vm.totalAmount.set(total.toString());
        adapter.notifyItemChanged(vm.position);
    }
}

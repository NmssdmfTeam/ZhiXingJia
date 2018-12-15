package com.zhixingjia.goodsmanagemodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.bean.SepcStockPrice;
import com.zhixingjia.goodsmanagemodule.callback.LadderPriceSettingCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityLadderPriceSettingBinding;
import com.zhixingjia.goodsmanagemodule.databinding.ItemLadderPriceSettingBinding;
import com.zhixingjia.goodsmanagemodule.databinding.ItemLadderStockSettingBinding;
import com.zhixingjia.goodsmanagemodule.viewmodel.LadderPriceSettingVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 有规格价格库存
* @author chenbin
* @date 2018/11/21 14:50
* @version v3.2.0
*/
public class LadderPriceSettingActivity extends BaseTitleActivity implements WheelPickerWindowCB,LadderPriceSettingCB {
    private final String TAG = LadderPriceSettingActivity.class.getSimpleName();
    private LadderPriceSettingVM vm;
    private ActivityLadderPriceSettingBinding binding;
    private List<SepcStockPrice> priceSet = new ArrayList<>();
    private List<SepcStockPrice> stockSet = new ArrayList<>();
    private WheelPickerWindow unitWheelPickerWindow;

    @Override
    public String setTitle() {
        return "设置价格库存";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLadderPriceSettingBinding) baseViewBinding;
        binding.setVm(vm);
        unitWheelPickerWindow = new WheelPickerWindow(this, vm.units, this);
        initView();
    }

    private void initView() {
        int i = 0;
        for (String key : vm.stock.keySet()) {
            ItemLadderPriceSettingBinding priceSettingBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_ladder_price_setting, null,false);
            ItemLadderStockSettingBinding stockSettingBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_ladder_stock_setting,null, false);
            String name = vm.stockName.get(i);
            SepcStockPrice price = new SepcStockPrice();
            SepcStockPrice stock = new SepcStockPrice();
            price.setName(name);
            stock.setName(name);

            priceSettingBinding.setData(price);
            stockSettingBinding.setData(stock);
            priceSet.add(price);
            stockSet.add(stock);
            binding.llPrice.addView(priceSettingBinding.getRoot());
            binding.llStock.addView(stockSettingBinding.getRoot());
            i++;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_ladder_price_setting;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new LadderPriceSettingVM(this);
        return vm;
    }

    @Override
    public void tvSureClick(String item, int position) {
        vm.unit.set(item);
    }

    @Override
    public void showUnitWheelPickWindow() {
        unitWheelPickerWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }
}

package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.view.TagView;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.ChooseSpecificationWindowCB;
import com.zhihangjia.mainmodule.databinding.ItemPostTagBinding;
import com.zhihangjia.mainmodule.databinding.ItemStockTypeBinding;
import com.zhihangjia.mainmodule.databinding.WindowChooseSpecificationBinding;
import com.zhihangjia.mainmodule.viewmodel.ChooseSpecificationWindowVM;
import com.zhixingjia.bean.mainmodule.CommodityDetail;
import com.zhixingjia.bean.mainmodule.SkuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情 选择规格
 */
public class ChooseSpecificationWindow extends PopupWindow implements ChooseSpecificationWindowCB{
    public interface ViewClickListener {
        void onAddCartClick();

        void onBuyClick();
    }
    private ChooseSpecificationWindowVM vm;
    private WindowChooseSpecificationBinding binding;
    private Context context;
    private List<ItemStockTypeBinding> itemStockTypeBindings = new ArrayList<>();
    private Map<String,String> stock = new HashMap<>();
    private String productSkuId;                //所选的规格ID
    private ViewClickListener viewClickListener;
    private String selectedStock;

    public ChooseSpecificationWindow(final Context context, ViewClickListener viewClickListener){
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_choose_specification, null, false);
        setContentView(binding.getRoot());
        this.context = context;
        vm = new ChooseSpecificationWindowVM(this);
        binding.setVm(vm);
        setWidth(DensityUtil.dpToPx(context, 375));
        setHeight(DensityUtil.dpToPx(context, 477.5f));

        setOnDismissListener(() -> WindowUtil.setBackgroundAlpha((Activity) context, 1));
        this.viewClickListener = viewClickListener;
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

    @Override
    public void addCart() {
        viewClickListener.onAddCartClick();
    }

    @Override
    public void buyNow() {
        viewClickListener.onBuyClick();
    }

    public void setData(final CommodityDetail commodityDetail) {
        if (commodityDetail != null){
            binding.setData(commodityDetail);
            if (commodityDetail.getImgs() != null && commodityDetail.getImgs().size() > 0) {
                GlideUtil.load(binding.ivIcon, commodityDetail.getImgs().get(0));
            }
            //动态添加规格
            itemStockTypeBindings.clear();
            binding.llStock.removeAllViews();
            if (commodityDetail.getSepc_val() != null && commodityDetail.getSepc_val().size() > 0) {
                for (CommodityDetail.SepcValBean sepcValBean : commodityDetail.getSepc_val()) {
                    final ItemStockTypeBinding itemStockTypeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_stock_type, null, false);
                    itemStockTypeBinding.tvColor.setText(sepcValBean.getSepc_name());
                    for (String tagname : sepcValBean.getChild()) {
                        final ItemPostTagBinding itemPostTagBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_post_tag, null, false);
                        itemPostTagBinding.tvTag.setText(tagname);
                        itemPostTagBinding.tvTag.setMode(TagView.SINGLEMODE);
                        itemPostTagBinding.tvTag.setTagId(sepcValBean.getSepc_name());
                        itemPostTagBinding.tvTag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int p = 0; p < itemStockTypeBinding.tlColor.getChildCount(); p++) {
                                    ItemPostTagBinding itembinding = DataBindingUtil.getBinding(itemStockTypeBinding.tlColor.getChildAt(p));
                                    itembinding.tvTag.setSelected(false);
                                }
                                itemPostTagBinding.tvTag.setSelected(true);
                                stock.put(itemPostTagBinding.tvTag.getTagId(), itemPostTagBinding.tvTag.getText().toString());
                                boolean isComplete = true;
                                for (CommodityDetail.SepcValBean sepcValBean : commodityDetail.getSepc_val()) {//判断是否规格选择完了
                                    if (TextUtils.isEmpty(stock.get(sepcValBean.getSepc_name()))) {
                                        isComplete = false;
                                        break;
                                    }
                                }
                                if (isComplete) {//显示库存个数
                                    for (SkuBean skuBean : commodityDetail.getSku()) {
                                        boolean isEqual = true;
                                        for (SkuBean.SpecInfoBean specInfoBean : skuBean.getSpec_info()) {
                                            if (!specInfoBean.getValue().equals(stock.get(specInfoBean.getKey()))) {
                                                isEqual = false;
                                                break;
                                            }
                                        }
                                        if (isEqual) {
                                            try {
                                                if (Integer.valueOf(skuBean.getStock()) > 0) {
                                                    binding.amv.setMaxNum(Integer.valueOf(skuBean.getStock()));
                                                    if (Integer.valueOf(binding.amv.getCurrentNum()) > Integer.valueOf(skuBean.getStock())) {
                                                        binding.amv.setCurrentNum(skuBean.getStock());
                                                    } else {
                                                        binding.amv.setCurrentNum(binding.amv.getCurrentNum());
                                                    }
                                                } else {
                                                    binding.amv.setMaxNum(1);
                                                    binding.amv.setCurrentNum("1");
                                                }
                                                binding.amv.setVisibility(View.VISIBLE);
                                            } catch (Exception e) {

                                            }
                                            binding.tvPrice.setText(skuBean.getPrice());
                                            binding.tvStock.setText(context.getText(R.string.stock) + skuBean.getStock());
                                            selectedStock = skuBean.getStock();
                                            productSkuId = skuBean.getProduct_id();
                                        }
                                    }
                                }
                            }
                        });
                        itemStockTypeBinding.tlColor.addView(itemPostTagBinding.getRoot());
                    }
                    itemStockTypeBindings.add(itemStockTypeBinding);
                    binding.llStock.addView(itemStockTypeBinding.getRoot());
                    setHeight(DensityUtil.dpToPx(context, 477.5f));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)binding.sv.getLayoutParams();
                    layoutParams.height = DensityUtil.dpToPx(context, 300.0f);
                    binding.sv.setLayoutParams(layoutParams);
                }
            } else {
                binding.tvStock.setText(commodityDetail.getStock());
                binding.tvPrice.setText(commodityDetail.getPrice());
                binding.amv.setMaxNum(Integer.valueOf(commodityDetail.getStock()));
                if (Integer.valueOf(commodityDetail.getStock()) > 0) {
                    binding.amv.setMaxNum(Integer.valueOf(commodityDetail.getStock()));
                } else {
                    binding.amv.setMaxNum(1);
                }
                binding.amv.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)binding.sv.getLayoutParams();
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                binding.sv.setLayoutParams(layoutParams);
                selectedStock = commodityDetail.getPrice();
                setHeight(DensityUtil.dpToPx(context, 260.5f));
            }
        }
    }


    public String getProductSkuId() {
        return productSkuId;
    }

    public String goodsSum() {
        return binding.amv.getCurrentNum();
    }

    public String getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(String selectedStock) {
        this.selectedStock = selectedStock;
    }
}

package com.zhixingjia.goodsmanagemodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.view.TagView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.callback.SelectStandardCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivitySelectStandardBinding;
import com.zhixingjia.goodsmanagemodule.databinding.ItemSepcInfoBinding;
import com.zhixingjia.goodsmanagemodule.databinding.ItemStandardTagBinding;
import com.zhixingjia.goodsmanagemodule.viewmodel.SelectStandardVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 选择规格
* @author chenbin
* @date 2018/11/21 15:49
* @version v3.2.0
*/
public class SelectStandardActivity extends BaseTitleActivity implements SelectStandardCB {
    private final String TAG = SelectStandardActivity.class.getSimpleName();
    private SelectStandardVM vm;
    private ActivitySelectStandardBinding binding;
    private List<List<ItemStandardTagBinding>> tags = new ArrayList<>();
    private List<ItemStandardTagBinding> standardItemPostTagBindings = new ArrayList<>();

    @Override
    public String setTitle() {
        return "选择规格";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySelectStandardBinding) baseViewBinding;
        binding.setVm(vm);
        addViews();
//        for (int i=0; i < 4; i++) {
//            ItemStandardTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_standard_tag,null,false);
//            itemPostTagBinding.tvTag.setText("原木色"+i);
//            standardItemPostTagBindings.add(itemPostTagBinding);
//            binding.tlStandard.addView(itemPostTagBinding.getRoot());
//        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_select_standard;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new SelectStandardVM(this);
        return vm;
    }

    public void addViews() {
        tags.clear();
        for (int i=0; i < vm.sepcInfos.size(); i++) {
            ArrayList<ItemStandardTagBinding> tagBindings = new ArrayList<>();
            ItemSepcInfoBinding itemSepcInfoBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_sepc_info,null,false);
            itemSepcInfoBinding.tvCateName.setText(vm.sepcInfos.get(i).getType_name());
            for (String typeValue : vm.sepcInfos.get(i).getType_val()) {
                boolean isSelected = false;
                List<String> selectedValues = vm.selectedSpecInfos.get(vm.sepcInfos.get(i).getNorms_id());
                if (selectedValues != null) {
                    for (int j = 0; j < selectedValues.size(); j++) {
                        if (selectedValues.get(j).equals(typeValue)) {
                            isSelected = true;
                        }
                    }
                }
                final ItemStandardTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_standard_tag,null,false);
                itemPostTagBinding.tvTag.setText(typeValue);
                itemPostTagBinding.tvTag.setSelected(isSelected);
                itemPostTagBinding.tvTag.setTagId(vm.sepcInfos.get(i).getNorms_id());
                itemPostTagBinding.tvTag.setTagClickListener(new TagView.TagClickListener() {
                    @Override
                    public void onTagClick() {
                        List<String> selectedValue = vm.selectedSpecInfos.get(itemPostTagBinding.tvTag.getTagId());
                        if (selectedValue == null) {
                            selectedValue = new ArrayList<>();
                        }
                        if (itemPostTagBinding.tvTag.isSelected()) {
                            selectedValue.add(itemPostTagBinding.tvTag.getText().toString());
                        } else {
                            selectedValue.remove(itemPostTagBinding.tvTag.getText().toString());
                        }
                        vm.selectedSpecInfos.put(itemPostTagBinding.tvTag.getTagId(), selectedValue);
                        setSelectedStandardView();
                    }
                });
                tagBindings.add(itemPostTagBinding);
                itemSepcInfoBinding.tl.addView(itemPostTagBinding.getRoot());
            }
            binding.llContent.addView(itemSepcInfoBinding.getRoot(),i);
            tags.add(tagBindings);
        }
        setSelectedStandardView();
    }

    private void setSelectedStandardView() {
        boolean isShowSelectedStandardView = true;
        if (vm.selectedSpecInfos.size() == vm.sepcInfos.size()) {
            for (String key : vm.selectedSpecInfos.keySet()) {
                if (vm.selectedSpecInfos.get(key).size() == 0) {
                    isShowSelectedStandardView = false;
                    break;
                }
            }
        } else {
            isShowSelectedStandardView = false;
        }
        if (isShowSelectedStandardView) {
            addSelectedStandardView();
        }
        binding.llSelectedStandard.setVisibility(isShowSelectedStandardView? View.VISIBLE:View.GONE);
    }

    /**
     * 添加选中规格
     */
    private void addSelectedStandardView() {
        binding.tlStandard.removeAllViews();
        List<String> selectedStandardValues = new ArrayList<>();
        for (CommodityInitialize.CateInfo.SepcInfo sepcInfo : vm.sepcInfos) {
            if (selectedStandardValues.size() != 0) {
                List<String> selectedValues = vm.selectedSpecInfos.get(sepcInfo.getNorms_id());
                List<String> result = new ArrayList();
                for (String value : selectedStandardValues) {
                    for (String selectedValue : selectedValues) {
                        StringBuffer stringBuffer = new StringBuffer(value);
                        stringBuffer.append("+");
                        stringBuffer.append(selectedValue);
                        result.add(stringBuffer.toString());
                    }
                }
                selectedStandardValues = result;
            } else {
                selectedStandardValues.addAll(vm.selectedSpecInfos.get(sepcInfo.getNorms_id()));
            }
        }
        for (String value : selectedStandardValues) {
            ItemStandardTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_standard_tag,null,false);
            itemPostTagBinding.tvTag.setText(value);
            itemPostTagBinding.tvTag.setClickable(false);
            itemPostTagBinding.tvTag.setBackgroundResource(R.drawable.shape_standard_tag);
            itemPostTagBinding.tvTag.setTextColor(getResources().getColor(R.color.text_orange));
            standardItemPostTagBindings.add(itemPostTagBinding);
            binding.tlStandard.addView(itemPostTagBinding.getRoot());
        }
    }
}

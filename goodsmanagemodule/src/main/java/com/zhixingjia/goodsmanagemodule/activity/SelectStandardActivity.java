package com.zhixingjia.goodsmanagemodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.databinding.ActivitySelectStandardBinding;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
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
public class SelectStandardActivity extends BaseTitleActivity {
    private final String TAG = SelectStandardActivity.class.getSimpleName();
    private SelectStandardVM vm;
    private ActivitySelectStandardBinding binding;
    private List<ItemStandardTagBinding> colorItemPostTagBindings = new ArrayList<>();
    private List<ItemStandardTagBinding> sizeItemPostTagBindings = new ArrayList<>();
    private List<ItemStandardTagBinding> standardItemPostTagBindings = new ArrayList<>();

    @Override
    public String setTitle() {
        return "选择规格";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySelectStandardBinding) baseViewBinding;
        for (int i=0; i < 10; i++) {
            ItemStandardTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_standard_tag,null,false);
            itemPostTagBinding.tvTag.setText("黑色"+i);
            colorItemPostTagBindings.add(itemPostTagBinding);
            binding.tlColor.addView(itemPostTagBinding.getRoot());
        }

        for (int i=0; i < 6; i++) {
            ItemStandardTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_standard_tag,null,false);
            itemPostTagBinding.tvTag.setText("80cm"+i);
            sizeItemPostTagBindings.add(itemPostTagBinding);
            binding.tlSize.addView(itemPostTagBinding.getRoot());
        }

        for (int i=0; i < 4; i++) {
            ItemStandardTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_standard_tag,null,false);
            itemPostTagBinding.tvTag.setText("原木色"+i);
            standardItemPostTagBindings.add(itemPostTagBinding);
            binding.tlStandard.addView(itemPostTagBinding.getRoot());
        }
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
}

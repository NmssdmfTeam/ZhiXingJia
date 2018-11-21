package com.zhixingjia.goodsmanagemodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.viewmodel.AddOrEditProductVM;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityAddOrEditProductBinding;

/**
* @description 发布商品activity
* @author chenbin
* @date 2018/11/21 11:22
* @version v3.2.0
*/
public class AddOrEditProductActivity extends BaseTitleActivity {
    private final String TAG = AddOrEditProductActivity.class.getSimpleName();
    private AddOrEditProductVM vm;
    private ActivityAddOrEditProductBinding binding;

    @Override
    public String setTitle() {
        return "发布商品";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityAddOrEditProductBinding) baseViewBinding;
        binding.isv.setImage_max_size(4);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_or_edit_product;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AddOrEditProductVM(this);
        return vm;
    }
}

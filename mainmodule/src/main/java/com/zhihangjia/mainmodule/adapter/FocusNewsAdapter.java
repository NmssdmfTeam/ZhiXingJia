package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemHeadlineBinding;
import com.zhixingjia.bean.mainmodule.New;

import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 要闻动态
 * @date 2018/11/16 17:09
 */
public class FocusNewsAdapter extends BaseDataBindingAdapter<New, ItemHeadlineBinding> {
    public FocusNewsAdapter(@Nullable List<New> data) {
        super(R.layout.item_headline, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHeadlineBinding> helper, New item, int position) {
        ItemHeadlineBinding binding = helper.getBinding();
        binding.ivPic.setVisibility(View.GONE);
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.LINK, item.getLink_url());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }
}

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
import com.zhihangjia.mainmodule.databinding.ItemPromotionBinding;
import com.zhihangjia.mainmodule.databinding.ItemXyTelecomBinding;
import com.zhixingjia.bean.mainmodule.Promotion;
import com.zhixingjia.bean.mainmodule.YXTelecom;

import java.util.List;

/**
* @description 展示中心--促销活动list adapter
* @author chenbin
* @date 2019/1/21 17:51
* @version v3.2.0
*/
public class PromotionsActivityAdapter extends BaseDataBindingAdapter<Promotion, ItemPromotionBinding> {

    public PromotionsActivityAdapter(@Nullable List<Promotion> data) {
        super(R.layout.item_promotion, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemPromotionBinding> helper, Promotion item, int position) {
        ItemPromotionBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.getInfo_img()))
                    return;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.LINK, item.getShare_url());
                intent.putExtras(bundle);
                intent.setClass(mContext, WebViewActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}

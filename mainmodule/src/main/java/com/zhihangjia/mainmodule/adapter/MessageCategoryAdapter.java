package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MessageCenterModuleActivity;
import com.zhihangjia.mainmodule.databinding.ItemMessageCategoryInfoBinding;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.BbsInfoList;

import java.util.List;

public class MessageCategoryAdapter extends BaseDataBindingAdapter<BbsCategory, ItemMessageCategoryInfoBinding> {
    public MessageCategoryAdapter(@Nullable List<BbsCategory> data) {
        super(R.layout.item_message_category_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMessageCategoryInfoBinding> helper, BbsCategory item, int position) {
        ItemMessageCategoryInfoBinding itemMessageCategoryInfoBinding = helper.getBinding();
        itemMessageCategoryInfoBinding.setData(item);
        itemMessageCategoryInfoBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MessageCenterModuleActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}

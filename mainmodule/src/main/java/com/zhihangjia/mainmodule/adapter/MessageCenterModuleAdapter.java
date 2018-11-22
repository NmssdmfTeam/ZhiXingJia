package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMessageBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageModuleBinding;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
* @description 消息中心分类模块adapter
* @author chenbin
* @date 2018/11/14 15:14
* @version v3.2.0
*/
public class MessageCenterModuleAdapter extends BaseDataBindingAdapter<BbsInfoList, ItemMessageModuleBinding> {
    public MessageCenterModuleAdapter(@Nullable List<BbsInfoList> data) {
        super(R.layout.item_message_module, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMessageModuleBinding> helper, BbsInfoList item, int position) {
        ItemMessageModuleBinding itemMessageModuleBinding = helper.getBinding();
        itemMessageModuleBinding.setData(item);
        if (item.getImgs()== null || item.getImgs().size() == 0) {
            itemMessageModuleBinding.llImgs.setVisibility(View.GONE);
            itemMessageModuleBinding.ivImg.setVisibility(View.GONE);
        } else if (item.getImgs().size() == 1) {
            itemMessageModuleBinding.llImgs.setVisibility(View.GONE);
            itemMessageModuleBinding.ivImg.setVisibility(View.VISIBLE);
            GlideUtil.load(itemMessageModuleBinding.ivImg,item.getImgs().get(0));
        } else {
            itemMessageModuleBinding.ivImg.setVisibility(View.GONE);
            itemMessageModuleBinding.llImgs.setVisibility(View.VISIBLE);
            itemMessageModuleBinding.llImgs.removeAllViews();
            int index = 0;
            for (String img:item.getImgs()) {
                if (index > 2)
                    break;
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext,111),DensityUtil.dpToPx(mContext,75));
                if (index != 2)
                    layoutParams.rightMargin = DensityUtil.dpToPx(mContext,5);
                imageView.setLayoutParams(layoutParams);
                GlideUtil.load(imageView,img);
                itemMessageModuleBinding.llImgs.addView(imageView);
                index++;
            }
        }
    }
}

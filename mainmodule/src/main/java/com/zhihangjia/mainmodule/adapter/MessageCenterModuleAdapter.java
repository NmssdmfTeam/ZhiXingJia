package com.zhihangjia.mainmodule.adapter.message;

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
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
* @description 消息中心adapter
* @author chenbin
* @date 2018/11/14 15:14
* @version v3.2.0
*/
public class MessageAdapter extends BaseDataBindingAdapter<IndexBean.ForumBean, ItemMessageBinding> {
    public MessageAdapter(@Nullable List<IndexBean.ForumBean> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMessageBinding> helper, IndexBean.ForumBean item, int position) {
        ItemMessageBinding itemMessageBinding = helper.getBinding();
        itemMessageBinding.setData(item);
        if (item.getImgs()== null || item.getImgs().size() == 0) {
            itemMessageBinding.llImgs.setVisibility(View.GONE);
            itemMessageBinding.ivImg.setVisibility(View.GONE);
        } else if (item.getImgs().size() == 1) {
            itemMessageBinding.llImgs.setVisibility(View.GONE);
            itemMessageBinding.ivImg.setVisibility(View.VISIBLE);
            GlideUtil.load(itemMessageBinding.ivImg,item.getImgs().get(0));
        } else {
            itemMessageBinding.ivImg.setVisibility(View.GONE);
            itemMessageBinding.llImgs.setVisibility(View.VISIBLE);
            itemMessageBinding.llImgs.removeAllViews();
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
                itemMessageBinding.llImgs.addView(imageView);
                index++;
            }
        }
    }
}

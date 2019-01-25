package com.zhihangjia.mainmodule.adapter;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.HeadLineDetailInfo;
import com.zhihangjia.mainmodule.databinding.ItemImageContentBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxcommentFilterBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxheadlineDetailZanBinding;

import java.util.List;

/**
* @description 宜兴头题详情adapter
* @author chenbin
* @date 2019/1/25 20:13
* @version v3.2.0
*/
public class XYHeadLineDetailAdapter extends BaseDataBindingMultiItemQuickAdapter<HeadLineDetailInfo> {

    public final ObservableBoolean onlyLookBuilder = new ObservableBoolean(false);//是否只看楼主，默认是0，0=否 1=是
    public final ObservableBoolean isHot = new ObservableBoolean(false);//最赞
    public final ObservableBoolean isSortDesc = new ObservableBoolean(false);//最赞

    private int yxCommentFilterPosition = 0;

    public XYHeadLineDetailAdapter(@Nullable List data) {
        super(data);
        addItemType(0,R.layout.item_image_content);
        addItemType(1,R.layout.item_yxheadline_detail_zan);
        addItemType(2,R.layout.item_yxcomment_filter);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, HeadLineDetailInfo item, int position) {
        if (item.getItemType() == 0) {
            ItemImageContentBinding binding = (ItemImageContentBinding) helper.getBinding();
            binding.setData(item);
        } else if (item.getItemType() == 1) {
            ItemYxheadlineDetailZanBinding binding = (ItemYxheadlineDetailZanBinding) helper.getBinding();
        } else if (item.getItemType() == 2) {
            ItemYxcommentFilterBinding binding = (ItemYxcommentFilterBinding) helper.getBinding();
        }
    }

    public void tvSortClick(View view) {

    }

    public int getYxCommentFilterPosition() {
        return yxCommentFilterPosition;
    }

    public void setYxCommentFilterPosition(int yxCommentFilterPosition) {
        this.yxCommentFilterPosition = yxCommentFilterPosition;
    }

    public void notifyGiveInfo(String giveNames) {
        getData().get(yxCommentFilterPosition).setGiveNames(giveNames);
        notifyItemChanged(yxCommentFilterPosition + 1);
    }
}

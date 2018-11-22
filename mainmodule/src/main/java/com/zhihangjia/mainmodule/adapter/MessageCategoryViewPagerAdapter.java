package com.zhihangjia.mainmodule.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.rollviewpager.RollPagerView;
import com.nmssdmf.commonlib.rollviewpager.adapter.LoopPagerAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.MessageCategory;
import com.zhihangjia.mainmodule.databinding.ItemMessageCategoryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by chenbin on 2017/11/22
 * <p>
 * 循环滑动adapter
 */
public class MessageCategoryViewPagerAdapter extends LoopPagerAdapter {
    public static final int MAIN_PAGER = 1, PART_PAGER = 2, CAPACITY_PAGER =3;
    private int type = MAIN_PAGER;

    private List<MessageCategory> messageCategory;

    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private Context context;
    // SetScaleType(ImageView.ScaleType.CENTER_CROP);
    // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

    public MessageCategoryViewPagerAdapter(int type, List<MessageCategory> messageCategory, RollPagerView viewPager) {
        super(viewPager);
        this.type = type;
        this.messageCategory = messageCategory;
        this.context = viewPager.getContext();
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        ItemMessageCategoryBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_message_category,null,false);
        MessageCategoryAdapter adapter = new MessageCategoryAdapter(messageCategory.get(position).getCategories());
        binding.crv.setLayoutManager(new GridLayoutManager(context,4));
        binding.crv.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public int getRealCount() {
        return messageCategory.size();
    }

    public List<MessageCategory> getMessageCategory() {
        return messageCategory;
    }

    public void setMessageCategory(List<MessageCategory> messageCategory) {
        this.messageCategory = messageCategory;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }
}

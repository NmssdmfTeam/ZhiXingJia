package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemFlipOverBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public class FlipOverAdapter extends BaseDataBindingAdapter<String, ItemFlipOverBinding> {
    public interface OnItemClickListener {
        void onPageClick(int page);
    }

    private int currentPage = 1;
    private OnItemClickListener onItemClickListener;

    public FlipOverAdapter(@Nullable List<String> data,OnItemClickListener onItemClickListener) {
        super(R.layout.item_flip_over, data);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemFlipOverBinding> helper, String item, final int position) {
        helper.getBinding().tvPage.setText(item);
        if (currentPage - 1 == position) {
            helper.getBinding().tvPage.setTextColor(mContext.getResources().getColor(R.color.text_orange));
        } else {
            helper.getBinding().tvPage.setTextColor(mContext.getResources().getColor(R.color.text_black));
        }
        helper.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    currentPage = position + 1;
                    onItemClickListener.onPageClick(position + 1);
                }
            }
        });

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemTradeAreaBinding;
import com.zhixingjia.bean.mainmodule.TradeArea;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public class TradeAreaAdapter extends BaseDataBindingAdapter<TradeArea, ItemTradeAreaBinding> {

    private int currentItem = 0;//默认全部
    private TradeAreaAdapterListener listener;

    public TradeAreaAdapter(@Nullable List<TradeArea> data, TradeAreaAdapterListener listener) {
        super(R.layout.item_trade_area, data);
        this.listener = listener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemTradeAreaBinding> helper, final TradeArea item, final int position) {
        final ItemTradeAreaBinding binding = helper.getBinding();
        binding.setData(item);
        if (currentItem  == position) {
            binding.tvArea.setTextColor(mContext.getResources().getColor(R.color.text_orange));
        } else {
            binding.tvArea.setTextColor(mContext.getResources().getColor(R.color.text_black));
        }

        helper.getBinding().tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = position;
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onItemClick(item, position);
                }
            }
        });
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public interface  TradeAreaAdapterListener{
        void onItemClick(TradeArea item, int position);
    }
}

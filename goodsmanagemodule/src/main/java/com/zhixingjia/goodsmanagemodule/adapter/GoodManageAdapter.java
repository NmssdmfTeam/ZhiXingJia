package com.zhixingjia.goodsmanagemodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.databinding.ItemGoodManageBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class GoodManageAdapter extends BaseDataBindingAdapter<Commodity, ItemGoodManageBinding> {
    private GoodsManageOption goodsManageOption;
    private int state;      //上下架状态

    public interface GoodsManageOption {
        void onTvPullOffClick(View view, Commodity commodity, int position);
        void onTvDeleteClick(View view, Commodity commodity, int position);
    }

    public GoodManageAdapter(@Nullable List<Commodity> data, GoodsManageOption goodsManageOption, int state) {
        super(R.layout.item_good_manage, data);
        this.goodsManageOption = goodsManageOption;
        this.state = state;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemGoodManageBinding> helper, final Commodity item, final int position) {
        final ItemGoodManageBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setIs_opened(!item.isIs_opened());
            }
        });
        if (state == 1) {
            binding.tvPullOff.setText("下架");
        } else {
            binding.tvPullOff.setText("上架");
        }
        binding.tvPullOff.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                goodsManageOption.onTvPullOffClick(binding.getRoot(), item, position);
            }
        });
        binding.tvDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                goodsManageOption.onTvDeleteClick(binding.getRoot(), item, position);
            }
        });
    }
}

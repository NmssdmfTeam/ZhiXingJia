package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemHeadlineBinding;

import java.util.List;

/**
* @description 宜兴头条列表adatper
* @author chenbin
* @date 2018/11/16 15:33
* @version v3.2.0
*/
public class YXHeadLineAdapter extends BaseDataBindingAdapter<Base,ItemHeadlineBinding> {
    public YXHeadLineAdapter(@Nullable List<Base> data) {
        super(R.layout.item_headline, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHeadlineBinding> helper, Base item, int position) {

    }
}

package com.zhihangjia.mainmodule.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.widget.FullyGridLayoutManager;
import com.nmssdmf.commonlib.widget.FullyLinearLayoutManager;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ServiceAdapter;
import com.zhihangjia.mainmodule.adapter.message.MessageAdapter;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.databinding.ItemLifeServiceBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageCenterBinding;
import com.zhihangjia.mainmodule.databinding.ItemRecommendGoodsBinding;
import com.zhihangjia.mainmodule.databinding.ItemRecommendGoodsInfoBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页list adapter
 * Create by chenbin on 2018/11/13
 * <p>
 * <p>
 */
public class MainAdapter extends BaseDataBindingMultiItemQuickAdapter<MainBean> {
    public MainAdapter(@Nullable List data) {
        super(data);
        addItemType(1, R.layout.item_recommend_goods);
        addItemType(2, R.layout.item_life_service);
        addItemType(3, R.layout.item_message_center);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, MainBean item, int position) {
        if (item.getItemType() == 1) {//推荐商品
            ItemRecommendGoodsBinding itemRecommendGoodsBinding = (ItemRecommendGoodsBinding) helper.getBinding();
            int childeCount = itemRecommendGoodsBinding.llContent.getChildCount();
            for (int i=1; i < childeCount - 1; i++) {
                itemRecommendGoodsBinding.llContent.removeViewAt(1);
            }
            for (int i = 0; i < 3; i++) {
                ItemRecommendGoodsInfoBinding itemRecommendGoodsInfoBinding = DataBindingUtil
                        .inflate(LayoutInflater.from(mContext),R.layout.item_recommend_goods_info,null,false);
                itemRecommendGoodsBinding.llContent.addView(itemRecommendGoodsInfoBinding.getRoot(),1);
            }
        } else if (item.getItemType() == 2) {
            ItemLifeServiceBinding itemLifeServiceBinding = (ItemLifeServiceBinding) helper.getBinding();
            if (itemLifeServiceBinding.rvService.getLayoutManager() == null)
                itemLifeServiceBinding.rvService.setLayoutManager(new FullyGridLayoutManager(mContext, 5));
            List<Base> list = new ArrayList<>();
            Base base = new Base();
            base.setMessage("宜兴紫砂");
            list.add(base);
            base = new Base();
            base.setMessage("政务信息");
            list.add(base);
            base = new Base();
            base.setMessage("特色美食");
            list.add(base);
            base = new Base();
            base.setMessage("医疗养老");
            list.add(base);
            base = new Base();
            base.setMessage("教育培训");
            list.add(base);
            base = new Base();
            base.setMessage("汽车");
            list.add(base);
            base = new Base();
            base.setMessage("百货");
            list.add(base);
            base = new Base();
            base.setMessage("房产");
            list.add(base);
            base = new Base();
            base.setMessage("旅游");
            list.add(base);
            base = new Base();
            base.setMessage("找工作");
            list.add(base);
            ServiceAdapter adapter;
            if (itemLifeServiceBinding.rvService.getAdapter() == null) {
                adapter = new ServiceAdapter(list);
                itemLifeServiceBinding.rvService.setAdapter(adapter);
                adapter.loadMoreEnd(false);
            } else {
                adapter = (ServiceAdapter) itemLifeServiceBinding.rvService.getAdapter();
                adapter.setNewData(list);
            }
            adapter.notifyDataSetChanged();
        } else if (item.getItemType() == 3) {
            ItemMessageCenterBinding binding = (ItemMessageCenterBinding)helper.getBinding();
            if (binding.rvMessage.getLayoutManager() == null) {
                binding.rvMessage.setLayoutManager(new LinearLayoutManager(mContext
                        , LinearLayoutManager.VERTICAL, false));
            }
            List<Base> list = new ArrayList<>();
            for (int i =0 ; i < 10; i++)
                list.add(new Base());
            MessageAdapter adapter;
            if (binding.rvMessage.getAdapter() == null) {
                adapter = new MessageAdapter(list);
                binding.rvMessage.setAdapter(adapter);
                adapter.loadMoreEnd(false);
            } else {
                adapter = (MessageAdapter) binding.rvMessage.getAdapter();
                adapter.setNewData(list);
            }
            adapter.notifyDataSetChanged();
        }
    }
}

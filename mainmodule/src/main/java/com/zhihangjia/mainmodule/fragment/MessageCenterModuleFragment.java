package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MessageCenterModuleAdapter;
import com.zhihangjia.mainmodule.callback.MessageCenterModuleCB;
import com.zhihangjia.mainmodule.viewmodel.MessageCenterModuleFragmentVM;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
 * 信息中心分类新闻fragment
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class MessageCenterModuleFragment extends BaseRecyclerViewFragment implements MessageCenterModuleCB {
    private MessageCenterModuleFragmentVM vm;
    private MessageCenterModuleAdapter adapter;
    private String TAG = MessageCenterModuleFragment.class.getSimpleName();

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        setTypes(bundle.getString("types"));
        setCategory(bundle.getString("cat_id","1"));
        super.initAll(view, savedInstanceState);
    }

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MessageCenterModuleFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MessageCenterModuleAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void stopFreshAction() {
        binding.crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(false);
    }

    @Override
    public void setData(List<IndexBean.ForumBean> data, boolean isRefresh) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh,data);
    }

    @Override
    public void removeItemNotify(int position) {
        adapter.getData().remove(position);
        if (adapter.getData().size() > 0)
            vm.pages = adapter.getData().get(adapter.getData().size() - 1).getBbs_id();
        adapter.notifyItemRemoved(position);
    }

    public void setTypes(String types) {
        vm.types = types;
    }

    public void setCategory(String category) {
        vm.cate_id = category;
    }
}

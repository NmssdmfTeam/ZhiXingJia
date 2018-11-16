package com.zhihangjia.mainmodule.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.BaseViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;

import java.util.List;

/**
* @description 公告公示
* @author chenbin
* @date 2018/11/16 16:50
* @version v3.2.0
*/
public class NoticeFragment extends BaseRecyclerViewFragment {
    private final String TAG = NoticeFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        return new BaseRecyclerViewFragmentVM(this) {
            @Override
            public void initData(boolean isRefresh) {

            }
        };
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        return new BaseDataBindingAdapter(list) {
            @Override
            protected void convert2(BaseBindingViewHolder helper, Object item, int position) {

            }

            @Override
            protected void convert(BaseViewHolder helper, Object item, int position) {

            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }
        };
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}

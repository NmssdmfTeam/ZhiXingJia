package com.zhihangjia.mainmodule.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.MerchantEvaluateAdapter;
import com.zhihangjia.mainmodule.callback.MerchantEvaluateFragmentCB;
import com.zhihangjia.mainmodule.databinding.HeaderMerchantEvaluateBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchantEvaluateFragmentVM;
import com.zhixingjia.bean.mainmodule.ShopInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 店铺主页 口碑评价
 */
public class MerchantEvaluateFragment extends BaseRecyclerViewFragment implements MerchantEvaluateFragmentCB {

    private final String TAG = MerchantEvaluateFragment.class.getSimpleName();

    private MerchantEvaluateFragmentVM vm;
    private MerchantEvaluateAdapter adapter;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchantEvaluateFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        HeaderMerchantEvaluateBinding headerMerchantEvaluateBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.header_merchant_evaluate, null, false);
        adapter = new MerchantEvaluateAdapter(list);
        headerMerchantEvaluateBinding.setData(vm.memberInfo.get());
        adapter.setHeaderView(headerMerchantEvaluateBinding.getRoot());
        return adapter;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.memberId = bundle.getString(IntentConfig.ID);
            vm.memberInfo.set((ShopInfo.MemberBean) bundle.getSerializable(IntentConfig.MEMBER_INFO));
        }
        super.initAll(view, savedInstanceState);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void setMemberData(ShopInfo.MemberBean memberData) {
        vm.memberInfo.set(memberData);
    }
}

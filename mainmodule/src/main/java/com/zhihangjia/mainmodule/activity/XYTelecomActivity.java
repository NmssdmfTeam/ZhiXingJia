package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.XYTelecomAdapter;
import com.zhihangjia.mainmodule.callback.XYTelecomCB;
import com.zhihangjia.mainmodule.databinding.ActivityXytelecomBinding;
import com.zhihangjia.mainmodule.viewmodel.XYTelecomVM;
import com.zhixingjia.bean.mainmodule.YXTelecom;

import java.util.ArrayList;
import java.util.List;

/**
 * 宜兴广告
 */
public class XYTelecomActivity extends BaseActivity implements XYTelecomCB{
    private final String TAG = XYTelecomActivity.class.getSimpleName();

    private XYTelecomVM vm;
    private ActivityXytelecomBinding binding;
    private XYTelecomAdapter adapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_xytelecom;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new XYTelecomVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {

        WindowUtil.setWindowStatusBarTransParent(this);

        binding = (ActivityXytelecomBinding) baseBinding;
        vm.getData(true);
        adapter = new XYTelecomAdapter(new ArrayList<>());

        binding.crv.setAdapter(adapter);
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        setListener();
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getData(true);
            }

            @Override
            public void onLoadMore() {
                vm.getData(false);
            }
        });
    }

    @Override
    public void setData(List<YXTelecom> telecoms, boolean isRefresh) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh, telecoms);
    }

    @Override
    public void endFresh() {
        binding.crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(true);
    }
}

package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.YXTelecomViewPagerAdapter;
import com.zhihangjia.mainmodule.callback.XYTelecomCB;
import com.zhihangjia.mainmodule.databinding.ActivityXytelecomBinding;
import com.zhihangjia.mainmodule.databinding.ItemXyTelecomBinding;
import com.zhihangjia.mainmodule.viewmodel.XYTelecomVM;
import com.zhixingjia.bean.mainmodule.YXTelecom;
import java.util.List;

/**
 * 宜兴广告 宜兴电信营业厅
 */
public class XYTelecomActivity extends BaseActivity implements XYTelecomCB{
    private final String TAG = XYTelecomActivity.class.getSimpleName();

    private XYTelecomVM vm;
    private ActivityXytelecomBinding binding;
    private YXTelecomViewPagerAdapter rollPageViewAdapter;

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
        rollPageViewAdapter = new YXTelecomViewPagerAdapter(binding.rpv);
        binding.rpv.setAdapter(rollPageViewAdapter);
        binding.rpv.pause();
        vm.getData(true);
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        setListener();
    }

    private void setListener() {
        binding.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vm.getData(true);
            }
        });
    }

    @Override
    public void setData(List<YXTelecom> telecoms, boolean isRefresh) {
        if (isRefresh) {
            binding.llContent.removeAllViews();
        }
        int size = telecoms.size() > 3 ? 3:telecoms.size();
        for (int i = 0; i < size; i++) {
            ItemXyTelecomBinding itemXyTelecomBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_xy_telecom, null, false);
            int finalI = i;
            itemXyTelecomBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(telecoms.get(finalI).getLink_url()))
                        return;
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.LINK, telecoms.get(finalI).getLink_url());
                    intent.putExtras(bundle);
                    intent.setClass(XYTelecomActivity.this, WebViewActivity.class);
                    XYTelecomActivity.this.startActivity(intent);
                }
            });
            itemXyTelecomBinding.setData(telecoms.get(i));
            binding.llContent.addView(itemXyTelecomBinding.getRoot());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemXyTelecomBinding.getRoot().getLayoutParams();
            layoutParams.topMargin = DensityUtil.dpToPx(this, 16);
            itemXyTelecomBinding.getRoot().setLayoutParams(layoutParams);
        }
        binding.rpv.pause();
    }

    @Override
    public void endFresh() {
        binding.srl.setRefreshing(false);
    }
}

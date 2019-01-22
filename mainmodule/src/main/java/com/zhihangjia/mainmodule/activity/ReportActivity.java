package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityReportBinding;
import com.zhihangjia.mainmodule.viewmodel.ReportVM;

/**
* @description 举报
* @author chenbin
* @date 2019/1/21 14:33
* @version v3.2.0
*/
public class ReportActivity extends BaseTitleActivity {
    private final String TAG = ReportActivity.class.getSimpleName();
    private ReportVM vm;
    private ActivityReportBinding binding;

    @Override
    public String setTitle() {
        return "举报";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityReportBinding) baseViewBinding;
        binding.setVm(vm);
        baseTitleBinding.tTitle.inflateMenu(R.menu.commit);
        setListener();
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.commit) {
                    vm.report();
                }
                return false;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_report;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ReportVM(this);
        return vm;
    }
}

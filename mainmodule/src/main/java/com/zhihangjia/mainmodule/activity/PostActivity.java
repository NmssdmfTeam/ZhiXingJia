package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

/**
 * 发帖页面
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class PostActivity extends BaseActivity {

    private final String TAG = PostActivity.class.getSimpleName();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {

    }
}

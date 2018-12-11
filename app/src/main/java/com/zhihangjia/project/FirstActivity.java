package com.zhihangjia.project;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.util.DataCleanManager;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.project.viewmodel.FirstVM;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirstActivity extends BaseActivity {
    private final String TAG = FirstActivity.class.getSimpleName();
    private FirstVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_first;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new FirstVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        try {
            WindowUtil.setStatusbarColor(this, R.color.text_orange);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

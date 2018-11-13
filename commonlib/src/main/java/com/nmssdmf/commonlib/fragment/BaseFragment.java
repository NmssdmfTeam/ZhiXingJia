package com.nmssdmf.commonlib.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.KeyBoardUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.view.LoadingDialog.LoadingDialog;
import com.nmssdmf.commonlib.viewmodel.BaseVM;


/**
 * @author nmssdmf
 * @version v3.2.1
 * @descriptionb 作为最基本的databinding的framgment, 在基层BaseNewLibFragment的基础上, 新增了datadinding的对象和vm的对象
 * 添加了databinding的动画供子类调用,同时提供了最基本的接口,供子类实现
 * @date 2018/7/4 0004 15:23
 */
public abstract class BaseFragment extends Fragment implements BaseCB {
    /**
     * 基础view model
     */
    protected BaseVM vm;
    /**
     * 基础binding view
     */
    protected ViewDataBinding baseBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (baseBinding != null) {
            ViewGroup parent = (ViewGroup) baseBinding.getRoot().getParent();
            if (parent != null) {
                parent.removeView(baseBinding.getRoot());
            }
            return baseBinding.getRoot();
        }

        vm = initViewModel();

        baseBinding = DataBindingUtil.inflate(inflater, setLayout(), container, false);

        initAll(baseBinding.getRoot(), savedInstanceState);

        if (StringUtil.isEmpty(getTAG())) {
            JLog.e("TAG", "TAG == null");
            throw new NullPointerException();
        }
        JLog.d(getTAG(), "onCreateView");
        return baseBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        JLog.d(getTAG(), "onAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.d(getTAG(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        JLog.d(getTAG(), "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JLog.d(getTAG(), "onDestroy");
    }

    /**
     * 添加data binding动画
     */
    protected void addAnimation() {
        baseBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(sceneRoot);
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        JLog.d(getTAG(), "onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        JLog.d(getTAG(), "onDetach");
        if (vm != null) {
            vm.unregisterRx();
            vm.unRegisterRxBus();
        }
        super.onDetach();
    }

    /**
     * 子类实例化View Model，并返回.
     *
     * @return View Model
     */
    public abstract BaseVM initViewModel();

    /**
     * 设置activity对应的布局文件
     *
     * @return 布局文件的id
     */
    public abstract int setLayout();

    /**
     * 初始化
     * @param view
     * @param savedInstanceState
     */
    public abstract void initAll(View view, Bundle savedInstanceState);

    public abstract String getTAG();

    @Override
    public void showToast(String msg) {
        ToastUtil.getInstance().showToast(msg);
    }

    @Override
    public void showLoaddingDialog() {
        LoadingDialog.show(getActivity(), R.string.wait);
    }

    @Override
    public void dismissLoaddingDialog() {
        LoadingDialog.dismiss();
    }

    @Override
    public void doIntent(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    @Override
    public void doIntentClassName(String clsname, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClassName(getActivity(), clsname);
        if ( bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    @Override
    public void doIntentForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void doInentClassNameForResult(String clsname, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName(getActivity(), clsname);
        if ( bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public Resources getAppResource() {
        return getResources();
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void setResultCode(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().setResult(resultCode, intent);
    }


    @Override
    public String getStringFromId(int stringId) {
        return getString(stringId);
    }

    @Override
    public Bundle getIntentData() {
        Bundle bundle = null;
        if (getActivity().getIntent() != null) {
            bundle = getActivity().getIntent().getExtras();
        }
        return bundle;
    }

    @Override
    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyWords(getActivity());
    }
}

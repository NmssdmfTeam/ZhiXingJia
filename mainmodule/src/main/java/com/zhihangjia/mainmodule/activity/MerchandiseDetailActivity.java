package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.MerchandiseDetailFragment;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.fragment.CommentDetailListFragment;

/**
 * 商品详情
 */
public class MerchandiseDetailActivity extends BaseActivity {

    private final String TAG = MerchandiseDetailActivity.class.getSimpleName();

    private MerchandiseDetailFragment merchandiseDetailFragment;
    private CommentDetailListFragment commentDetailListFragment;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_merchandise_detail;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        WindowUtil.setWindowStatusBarTransParent(this);

        merchandiseDetailFragment = new MerchandiseDetailFragment();
        commentDetailListFragment = new CommentDetailListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.fl, merchandiseDetailFragment);
        transaction.addToBackStack("merchandiseDetailFragment");
        transaction.commit();
    }

    /**
    * @description 评论详情
    * @author cbb
    * @date 2018/11/17 2:55 PM
    * @version v3.2.0
    */
    public void switchToCommentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.fl, commentDetailListFragment);
        transaction.commit();
    }
}

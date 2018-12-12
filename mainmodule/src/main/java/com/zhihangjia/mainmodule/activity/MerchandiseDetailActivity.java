package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityMerchandiseDetailBinding;
import com.zhihangjia.mainmodule.fragment.MerchandiseDetailFragment;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.fragment.CommentDetailListFragment;
import com.zhihangjia.mainmodule.viewmodel.MerchandiseDetailVM;

/**
 * 商品详情
 */
public class MerchandiseDetailActivity extends BaseActivity implements MerchandiseDetailCB {

    private final String TAG = MerchandiseDetailActivity.class.getSimpleName();
    private MerchandiseDetailVM vm;
    private ActivityMerchandiseDetailBinding binding;

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
        vm = new MerchandiseDetailVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        WindowUtil.setWindowStatusBarTransParent(this);
        binding = (ActivityMerchandiseDetailBinding) baseBinding;
        binding.setVm(vm);
        merchandiseDetailFragment = new MerchandiseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.COMMODITY_ID, vm.commodityId);
        merchandiseDetailFragment.setArguments(bundle);
        commentDetailListFragment = new CommentDetailListFragment();
        commentDetailListFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.fl, merchandiseDetailFragment);
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
        transaction.addToBackStack("commentDetailListFragment");
        transaction.commit();
    }

    @Override
    public void addCart() {
        merchandiseDetailFragment.onAddCartClick();
    }

    @Override
    public void callPhone() {
        CommonUtils.callPhone(this, merchandiseDetailFragment.getPhoneNum());
    }

    @Override
    public void buyNow() {
        merchandiseDetailFragment.onBuyClick();
    }

    @Override
    public void toMerchants() {
        if (merchandiseDetailFragment != null)
            merchandiseDetailFragment.toMerchants();
    }
}

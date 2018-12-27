package com.zhihangjia.mainmodule.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.adapter.MerchandiseDetailViewPagerAdapter;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMerchandiseDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemCommodityDetailCommentBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageDetailBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchandiseDetailFragmentVM;
import com.zhihangjia.mainmodule.window.ChooseSpecificationWindow;
import com.zhihangjia.mainmodule.window.GetShopCouponWindow;
import com.zhixingjia.bean.mainmodule.CommodityDetail;
import com.zhixingjia.bean.mainmodule.ContentsBean;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseDetailFragment extends BaseFragment implements MerchandiseDetailFragmentCB, ChooseSpecificationWindow.ViewClickListener,UMShareListener {

    private final String TAG = MerchandiseDetailFragment.class.getSimpleName();
    private MerchandiseDetailFragmentVM vm;

    private FragmentMerchandiseDetailBinding binding;

    private GetShopCouponWindow getShopCouponWindow;
    private ChooseSpecificationWindow chooseSpecificationWindow;
    private MerchandiseDetailViewPagerAdapter viewPagerAdapter;

    @Override
    public BaseVM initViewModel() {
        vm = new MerchandiseDetailFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_merchandise_detail;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMerchandiseDetailBinding) baseBinding;
        binding.setVm(vm);
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.commodityId = bundle.getString(IntentConfig.COMMODITY_ID);
        }

        //初始化商品图片轮播图
        viewPagerAdapter = new MerchandiseDetailViewPagerAdapter(new ArrayList<String>(), binding.rpv);
        binding.rpv.setAdapter(viewPagerAdapter);
        vm.getCommondityDetail();
        chooseSpecificationWindow = new ChooseSpecificationWindow(getActivity(), this);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void showChooseCouponWindow() {
        if (getShopCouponWindow == null) {
            getShopCouponWindow = new GetShopCouponWindow(getActivity(), vm.commodityDetail.get().getSeller_coupon(), vm);
        }
        getShopCouponWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showChooseSpecificationWindow() {
        chooseSpecificationWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void gotoCommentDetail() {
        if (getActivity() instanceof MerchandiseDetailActivity) {
            MerchandiseDetailActivity merchandiseDetailActivity = (MerchandiseDetailActivity) getActivity();
            merchandiseDetailActivity.switchToCommentFragment();
        }
    }

    @Override
    public void setCommodityImgs(List<String> imgs) {
        viewPagerAdapter.getImgs().clear();
        viewPagerAdapter.getImgs().addAll(imgs);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        try {
            binding.acrbStore.setRating(Float.valueOf(vm.commodityDetail.get().getProvider_info().getScore()));
        } catch (Exception e) {

        }
        //设置商品详情内容
        binding.llCommodityDetail.removeAllViews();
        for (ContentsBean contentsBean : vm.commodityDetail.get().getContent()) {
            ItemMessageDetailBinding itemMessageDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_message_detail, null, false);
            itemMessageDetailBinding.content.setText(contentsBean.getNote());
            itemMessageDetailBinding.content.setVisibility(TextUtils.isEmpty(contentsBean.getNote())?View.GONE:View.VISIBLE);
            if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
                for (String img : contentsBean.getImgs()) {
                    GlideImageView imageView = new GlideImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.topMargin = DensityUtil.dpToPx(getContext(), 12);
//                    layoutParams.bottomMargin = DensityUtil.dpToPx(getContext(), 12);
                    imageView.setLayoutParams(layoutParams);
                    GlideUtil.load(imageView, img);
                    itemMessageDetailBinding.llContent.addView(imageView);
                }
            }
            binding.llCommodityDetail.addView(itemMessageDetailBinding.getRoot());
        }
        binding.rvBrand.setVisibility(TextUtils.isEmpty(vm.commodityDetail.get().getBrand()) ? View.GONE : View.VISIBLE);
        binding.rvBn.setVisibility(TextUtils.isEmpty(vm.commodityDetail.get().getBn()) ? View.GONE : View.VISIBLE);
        if (vm.commodityDetail.get().getSepc_val() == null || vm.commodityDetail.get().getSepc_val().size() == 0) {
            binding.rlSepc.setVisibility(View.GONE);
        } else {
            binding.rlSepc.setVisibility(View.VISIBLE);
        }

        //设置选择规格内容
        chooseSpecificationWindow.setData(vm.commodityDetail.get());
    }

    @Override
    public void setCommodityComment(List<CommodityDetail.OrderComment> orderComments) {
        binding.llCommentContent.removeAllViews();
        if (orderComments != null && orderComments.size() > 0) {
            binding.llComment.setVisibility(View.VISIBLE);
            binding.vblank.setVisibility(View.VISIBLE);
            for (CommodityDetail.OrderComment orderComment : orderComments) {
                ItemCommodityDetailCommentBinding itemCommodityDetailCommentBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_commodity_detail_comment, null, false);
                itemCommodityDetailCommentBinding.setData(orderComment);
                binding.llCommentContent.addView(itemCommodityDetailCommentBinding.getRoot());
            }
        } else {
            binding.llComment.setVisibility(View.GONE);
            binding.vblank.setVisibility(View.GONE);
        }
    }

    /**
     * 获取选择的规格
     */
    @Override
    public String getProductSkuId() {
        return chooseSpecificationWindow.getProductSkuId();
    }

    @Override
    public String getGoodsSum() {
        return chooseSpecificationWindow.goodsSum();
    }

    @Override
    public void toShare(CommodityDetail commodityDetail) {
        UMWeb web = new UMWeb(commodityDetail.getShare_url());
        web.setTitle(commodityDetail.getCommodity_name());//标题
        if (commodityDetail.getImgs().size() > 0) {
            UMImage image = new UMImage(getActivity(), commodityDetail.getImgs().get(0));
            web.setThumb(image);  //缩略图
        }
        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web).setCallback(this)
                .open();
    }

    @Override
    public void showShopCarRedPoint() {
        binding.vRedPoint.setVisibility(View.VISIBLE);
    }

    @Override
    public String getStock() {
        return chooseSpecificationWindow.getSelectedStock();
    }

    @Override
    public void onAddCartClick() {
        vm.addCartStore();
    }

    @Override
    public void onBuyClick() {
        vm.buyNow();
    }

    public void onActivityAddCartClick() {
        if (vm.commodityDetail.get().getSepc_val() == null || vm.commodityDetail.get().getSepc_val().size() == 0) {
            showChooseSpecificationWindow();
            return;
        }
        vm.addCartStore();
    }

    public void onActivityBuyNowClick() {
        if (vm.commodityDetail.get().getSepc_val() == null || vm.commodityDetail.get().getSepc_val().size() == 0) {
            showChooseSpecificationWindow();
            return;
        }
        vm.buyNow();
    }

    public String getPhoneNum() {
        return vm.commodityDetail.get().getProvider_info().getCo_phone();
    }

    public void toMerchants() {
        vm.onMerchantsClick(null, 0);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}

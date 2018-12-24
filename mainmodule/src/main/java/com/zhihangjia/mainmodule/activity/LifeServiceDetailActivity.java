package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jushi.gallery.activity.BeautyImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.LifeServiceDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityLifeServiceDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageDetailBinding;
import com.zhihangjia.mainmodule.viewmodel.LifeServiceDetailVM;
import com.zhihangjia.mainmodule.window.ChooseMapWindow;
import com.zhixingjia.bean.mainmodule.ContentsBean;

import java.io.Serializable;

/**
* @description 生活服务详情
* @author chenbin
* @date 2018/12/19 14:22
* @version v3.2.0
*/
public class LifeServiceDetailActivity extends BaseTitleActivity implements LifeServiceDetailCB {
    private static final String TAG = LifeServiceDetailActivity.class.getSimpleName();
    private LifeServiceDetailVM vm;
    private ActivityLifeServiceDetailBinding binding;
    private ChooseMapWindow chooseMapWindow;

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLifeServiceDetailBinding) baseViewBinding;
        chooseMapWindow = new ChooseMapWindow(this);
        setTitle(vm.title);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_life_service_detail;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new LifeServiceDetailVM(this);
        return vm;
    }

    @Override
    public void setContent() {
        binding.setVm(vm);
        if (vm.lifeServiceDetail.get() == null || vm.lifeServiceDetail.get().getContent() == null)
            return;
        vm.imageUrls.clear();
        for (ContentsBean contentsBean : vm.lifeServiceDetail.get().getContent()) {
            ItemMessageDetailBinding itemMessageDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_message_detail, null, false);
            itemMessageDetailBinding.content.setText(contentsBean.getNote());
            if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
                for (String img : contentsBean.getImgs()) {
                    GlideImageView imageView = new GlideImageView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.topMargin = DensityUtil.dpToPx(this, 12);
                    layoutParams.bottomMargin = DensityUtil.dpToPx(this, 12);
                    imageView.setLayoutParams(layoutParams);
                    GlideUtil.load(imageView, img);
                    itemMessageDetailBinding.llContent.addView(imageView);
                    vm.imageUrls.add(Uri.parse(img));
                    int index = vm.imageUrls.size() - 1;
                    imageView.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt(BeautyImageGalleryActivity.PAGE_INDEX, index);
                        bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY, (Serializable) vm.imageUrls);
                        doIntent(BeautyImageGalleryActivity.class, bundle);
                    });
                }
            }
            binding.llContent.addView(itemMessageDetailBinding.getRoot());
        }
    }

    @Override
    public void phoneCall(String phoneNumber) {
        CommonUtils.callPhone(this, phoneNumber);
    }

    @Override
    public void showChooseMapsWindows() {
        ChooseMapWindow.MapInfo mapInfo = new ChooseMapWindow.MapInfo();
        mapInfo.setLatitude(vm.lifeServiceDetail.get().getLatitude());
        mapInfo.setLongitude(vm.lifeServiceDetail.get().getLongitude());
        mapInfo.setName(vm.lifeServiceDetail.get().getInfo_addr());
        chooseMapWindow.setMapInfo(mapInfo);
        chooseMapWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0,0);
    }

    @Override
    public void setLifeServiceTitle(String cate) {
        setTitle(cate);
    }
}

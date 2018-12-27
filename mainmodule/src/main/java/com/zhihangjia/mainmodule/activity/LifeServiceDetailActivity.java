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
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.LifeServiceDetailAdapter;
import com.zhihangjia.mainmodule.bean.ContentBean;
import com.zhihangjia.mainmodule.callback.LifeServiceDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityLifeServiceDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemLifeServiceHeadviewBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageDetailBinding;
import com.zhihangjia.mainmodule.viewmodel.LifeServiceDetailVM;
import com.zhihangjia.mainmodule.window.ChooseMapWindow;
import com.zhixingjia.bean.mainmodule.ContentsBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 生活服务详情
 * @date 2018/12/19 14:22
 */
public class LifeServiceDetailActivity extends BaseTitleActivity implements LifeServiceDetailCB, LifeServiceDetailAdapter.ImageClickListener {
    private static final String TAG = LifeServiceDetailActivity.class.getSimpleName();
    private LifeServiceDetailVM vm;
    private ActivityLifeServiceDetailBinding binding;
    private ItemLifeServiceHeadviewBinding headviewBinding;
    private ChooseMapWindow chooseMapWindow;
    private LifeServiceDetailAdapter adapter;

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLifeServiceDetailBinding) baseViewBinding;
        chooseMapWindow = new ChooseMapWindow(this);
        headviewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_life_service_headview, null, false);
        adapter = new LifeServiceDetailAdapter(new ArrayList<>(), this);
        binding.crv.setAdapter(adapter);
        adapter.setEnableLoadMore(false);
        setTitle(vm.title);
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getDetailInfo();
            }

            @Override
            public void onLoadMore() {

            }
        });
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
//        binding.getRoot()
        binding.crv.setRefreshing(false);
        adapter.removeAllHeaderView();
        adapter.addHeaderView(headviewBinding.getRoot());
        headviewBinding.setData(vm.lifeServiceDetail.get());
        binding.setVm(vm);
        if (vm.lifeServiceDetail.get() == null || vm.lifeServiceDetail.get().getContent() == null)
            return;
        adapter.notifyDataChangedAfterLoadMore(true, vm.contentBeans);
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
        chooseMapWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void setLifeServiceTitle(String cate) {
        setTitle(cate);
    }

    @Override
    public void onImageClick(int position, ContentBean contentBean) {
        Bundle bundle = new Bundle();
        int index = 0;
        for (Uri imgs : vm.imageUrls) {
            if (imgs.toString().equals(contentBean.getImg())) {
                break;
            }
            index++;
        }
        bundle.putInt(BeautyImageGalleryActivity.PAGE_INDEX, index);
        bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY, (Serializable) vm.imageUrls);
        doIntent(BeautyImageGalleryActivity.class, bundle);
    }
}

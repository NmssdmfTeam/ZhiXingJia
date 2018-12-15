package com.zhixingjia.goodsmanagemodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.callback.AddOrEditProductCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityAddOrEditProductBinding;
import com.zhixingjia.goodsmanagemodule.viewmodel.AddOrEditProductVM;

import java.util.ArrayList;

/**
* @description 发布商品activity
* @author chenbin
* @date 2018/11/21 11:22
* @version v3.2.0
*/
public class AddOrEditProductActivity extends BaseTitleActivity implements AddOrEditProductCB {
    private final String TAG = AddOrEditProductActivity.class.getSimpleName();
    private AddOrEditProductVM vm;
    private ActivityAddOrEditProductBinding binding;
    private WheelPickerWindow brandwheelPickerWindow;       //品牌
    private WheelPickerWindow categorywheelPickerWindow;    //类别

    @Override
    public String setTitle() {
        return "发布商品";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityAddOrEditProductBinding) baseViewBinding;
        binding.isv.setImage_max_size(4);
        brandwheelPickerWindow = new WheelPickerWindow(this,new ArrayList<String>(),brandWheelPickerWindowCB);
        categorywheelPickerWindow = new WheelPickerWindow(this,new ArrayList<String>(),categoryWheelPickerWindowCB);
        binding.setVm(vm);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_or_edit_product;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AddOrEditProductVM(this);
        return vm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntegerConfig.REQUEST_CODE_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    binding.isv.addCameraImage();
                }
                break;
            case ImageGalleryActivity.IMAGE_SELECT_REQUEST:
                if (resultCode == RESULT_OK) {
                    binding.isv.addAlbumImage(data);
                }
                break;
            default:
                vm.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void showBrandWindow() {
        brandwheelPickerWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM,0,0);
    }

    @Override
    public void showCategoryWindow() {
        categorywheelPickerWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM,0,0);
    }

    @Override
    public void initData() {
        categorywheelPickerWindow.changeDataList(vm.categoryNames);
        brandwheelPickerWindow.changeDataList(vm.brandNames);
    }

    private WheelPickerWindowCB categoryWheelPickerWindowCB = new WheelPickerWindowCB() {
        @Override
        public void tvSureClick(String item, int position) {
            vm.categoryId = vm.commodityInitialize.getCateinfo().get(position).getCate_id();
            if (vm.commodityInitialize.getCateinfo().get(position).getSepc_info() == null
                    || vm.commodityInitialize.getCateinfo().get(position).getSepc_info().size() == 0) {
                binding.rlSepc.setVisibility(View.GONE);
                binding.includeRlSepc.setVisibility(View.GONE);
            } else {
                binding.rlSepc.setVisibility(View.VISIBLE);
                binding.includeRlSepc.setVisibility(View.VISIBLE);
                vm.sepcName.set(null);
                vm.skuName.set(null);
            }
            vm.selectedSepc.clear();
            vm.categoryIndex = position;
            vm.categoryName.set(item);
        }
    };

    private WheelPickerWindowCB brandWheelPickerWindowCB = new WheelPickerWindowCB() {
        @Override
        public void tvSureClick(String item, int position) {
            vm.brandName.set(item);
        }
    };
}

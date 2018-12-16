package com.zhixingjia.goodsmanagemodule.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.view.ImageSelectView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.callback.AddProductDescribeCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityProductDescribeBinding;
import com.zhixingjia.goodsmanagemodule.databinding.ItemDescribeContentBinding;
import com.zhixingjia.goodsmanagemodule.viewmodel.AddProductDescribeVM;

/**
* @description 添加图文描述
* @author chenbin
* @date 2018/11/21 13:46
* @version v3.2.0
*/
public class AddProductDescribeActivity extends BaseTitleActivity implements AddProductDescribeCB {
    private final String TAG = AddProductDescribeActivity.class.getSimpleName();
    private AddProductDescribeVM vm;
    private ImageSelectView currentImageSelectView;
    private ActivityProductDescribeBinding binding;

    @Override
    public String setTitle() {
        return "图文详情";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityProductDescribeBinding) baseViewBinding;
        baseTitleBinding.tTitle.inflateMenu(R.menu.sure);
        binding.setVm(vm);
        addContent();
        setListener();
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.sure) {

                }
                return false;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_product_describe;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AddProductDescribeVM(this);
        return vm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntegerConfig.REQUEST_CODE_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    currentImageSelectView.addCameraImage();
                }
                break;
            case ImageGalleryActivity.IMAGE_SELECT_REQUEST:
                if (resultCode == RESULT_OK) {
                    currentImageSelectView.addAlbumImage(data);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void addContent() {
        final ItemDescribeContentBinding itemPostContentBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_describe_content,null,false);
        itemPostContentBinding.isv.setOnUploadlistener(new ImageSelectView.OnImageUpLoadCompleteListener() {
            @Override
            public void onUpLoadComplete(String[] ids) {

            }

            @Override
            public void onUpLoadFailed(Throwable e) {

            }

            @Override
            public void onAddImageClick(ImageSelectView imageSelectView) {
                currentImageSelectView = imageSelectView;
            }
        });
        itemPostContentBinding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llContent.removeView(itemPostContentBinding.getRoot());
            }
        });
        binding.llContent.addView(itemPostContentBinding.getRoot());
    }
}

package com.zhixingjia.goodsmanagemodule.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.bean.UploadImage;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.view.ImageSelectView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.bean.ProductDescribe;
import com.zhixingjia.goodsmanagemodule.callback.AddProductDescribeCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityProductDescribeBinding;
import com.zhixingjia.goodsmanagemodule.databinding.ItemDescribeContentBinding;
import com.zhixingjia.goodsmanagemodule.viewmodel.AddProductDescribeVM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        if (vm.productDescribes != null && vm.productDescribes.size() > 0) {
            setDiscribeContent();
        } else {
            addContent();
        }
        setListener();
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.sure) {
                    //获取文本内容及ID
                    List<ProductDescribe> productDescribes = getContent();
                    if (productDescribes == null || productDescribes.size() == 0) {
                        showToast("请添加商品描述内容");
                        return false;
                    } else {
                        int i = 0;
                        for (ProductDescribe describe : productDescribes) {
                            if (TextUtils.isEmpty(describe.getNote())
                                    && (describe.getImgs() == null || describe.getImgs().size() == 0)) {

                                showToast("请填写第"+i+"行内容");
                                return false;
                            }
                            i++;
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentConfig.PRODUCT_DESCRIBES, (Serializable) productDescribes);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finishActivity();
                }
                return false;
            }
        });
    }

    /**
     * 获取内容，商品添加编辑做准备
     */
    private List<ProductDescribe> getContent() {
        List<ProductDescribe> productDescribes = new ArrayList<>();
        int childCount = binding.llContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ItemDescribeContentBinding itemDescribeContentBinding = DataBindingUtil.findBinding(binding.llContent.getChildAt(i));
            ProductDescribe productDescribe = new ProductDescribe();
            productDescribe.setNote(itemDescribeContentBinding.etContent.getText().toString());
            if (itemDescribeContentBinding.isv.getImgSize() > 0) {
                productDescribe.setImgs(itemDescribeContentBinding.isv.getFilePathList());
            }
            productDescribes.add(productDescribe);
        }
        return productDescribes;
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
        if (binding.llContent.getChildCount() >= 3) { //模块数不能超过9个
            ToastUtil.showMsg("图文数量不可超过3个");
            return;
        }
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
        if (binding.llContent.getChildCount() == 0) {
            itemPostContentBinding.btnRemove.setVisibility(View.GONE);
        }
        binding.llContent.addView(itemPostContentBinding.getRoot());
    }

    @Override
    public void setDiscribeContent() {
        for (ProductDescribe productDescribe : vm.productDescribes) {
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
            itemPostContentBinding.etContent.setText(productDescribe.getNote());
            if (productDescribe.getImgs() != null && productDescribe.getImgs().size() > 0) {
                for (UploadImage uploadImage : productDescribe.getImgs()) {
                    itemPostContentBinding.isv.addImage(uploadImage.getUrl(), uploadImage.getImage_id());
                }
            }
            itemPostContentBinding.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.llContent.removeView(itemPostContentBinding.getRoot());
                }
            });
            if (binding.llContent.getChildCount() == 0) {
                itemPostContentBinding.btnRemove.setVisibility(View.GONE);
            }
            binding.llContent.addView(itemPostContentBinding.getRoot());
        }
    }
}

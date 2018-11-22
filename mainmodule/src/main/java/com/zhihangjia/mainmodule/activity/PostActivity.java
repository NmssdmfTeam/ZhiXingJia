package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.ImageSelectView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.PostCB;
import com.zhihangjia.mainmodule.databinding.ActivityPostBinding;
import com.zhihangjia.mainmodule.databinding.ItemPostTagBinding;
import com.zhihangjia.mainmodule.viewmodel.PostVM;
import com.zhihangjia.mainmodule.databinding.ItemPostContentBinding;
import com.zhixingjia.bean.mainmodule.BbsCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 发帖页面
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class PostActivity extends BaseActivity implements PostCB{
    private ActivityPostBinding binding;
    private final String TAG = PostActivity.class.getSimpleName();
    private PostVM vm;
    private ImageSelectView currentImageSelectView;
    private List<ItemPostTagBinding> itemPostTagBindings = new ArrayList<>();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_post;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new PostVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityPostBinding) baseBinding;
        binding.setVm(vm);
        binding.tTitle.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.tTitle.inflateMenu(R.menu.post);
        vm.getBbsCat();
    }

    private void setListener() {
        binding.tTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        final ItemPostContentBinding itemPostContentBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_post_content,null,false);
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

    @Override
    public void showChooseWindow() {
        if (binding.llTags.getVisibility() == View.GONE) {
            binding.llTags.setVisibility(View.VISIBLE);
            binding.vBlackBackgroud.setVisibility(View.VISIBLE);
        } else {
            binding.llTags.setVisibility(View.GONE);
            binding.vBlackBackgroud.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCat(List<BbsCategory> cats) {
        itemPostTagBindings.clear();
        binding.tagLayout.removeAllViews();
        int i = 0;
        for (BbsCategory category : cats) {
            //设置类别
            ItemPostTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_post_tag,null,false);
            itemPostTagBinding.tvTag.setText(category.getCate_name());
            if (i == 0)
                itemPostTagBinding.tvTag.setSelected(true);
            itemPostTagBindings.add(itemPostTagBinding);
            binding.tagLayout.addView(itemPostTagBinding.getRoot());
            i++;
        }
        if (cats.size() > 0) {
            binding.tvTitle.setText(cats.get(0).getCate_name());
            vm.currentCat = cats.get(0).getCate_id();
            setListener();
        }
    }
}

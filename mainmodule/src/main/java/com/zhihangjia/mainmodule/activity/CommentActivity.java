package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.view.ImageSelectView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCommentInputBinding;
import com.zhihangjia.mainmodule.viewmodel.CommentVM;
import com.zhihangjia.mainmodule.databinding.ActivityCommentBinding;

/**
* @description 发表评论
* @author chenbin
* @date 2018/11/28 18:57
* @version v3.2.0
*/
public class CommentActivity extends BaseTitleActivity {
    private final String TAG = CommentActivity.class.getSimpleName();
    private CommentVM vm;
    private ActivityCommentBinding binding;
    private ImageSelectView currentImageSelectView;
    private int imageSectionCount;

    @Override
    public String setTitle() {
        return "发表评论";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityCommentBinding) baseViewBinding;
        baseTitleBinding.tTitle.inflateMenu(R.menu.post);
        //模拟商品数据
        for (int i = 0;i < 2; i++) {
            ItemCommentInputBinding commentInputBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_comment_input, null, false);
            commentInputBinding.isv.setOnUploadlistener(new ImageSelectView.OnImageUpLoadCompleteListener() {
                @Override
                public void onUpLoadComplete(String[] ids) {
                    imageSectionCount--;
                    if (imageSectionCount == 0) {//图片全都上传完毕

                    }
                }

                @Override
                public void onUpLoadFailed(Throwable e) {

                }

                @Override
                public void onAddImageClick(ImageSelectView imageSelectView) {
                    currentImageSelectView = imageSelectView;
                }
            });
            binding.llCommentContent.addView(commentInputBinding.getRoot());
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new CommentVM(this);
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
}

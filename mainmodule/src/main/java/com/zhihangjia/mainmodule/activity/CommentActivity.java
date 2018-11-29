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
import com.zhihangjia.mainmodule.bean.GoodsComment;
import com.zhihangjia.mainmodule.callback.CommentCB;
import com.zhihangjia.mainmodule.databinding.ItemCommentInputBinding;
import com.zhihangjia.mainmodule.viewmodel.CommentVM;
import com.zhihangjia.mainmodule.databinding.ActivityCommentBinding;
import com.zhixingjia.bean.mainmodule.OrderDetail;

import java.util.Arrays;

/**
* @description 发表评论
* @author chenbin
* @date 2018/11/28 18:57
* @version v3.2.0
*/
public class CommentActivity extends BaseTitleActivity implements CommentCB {
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
        vm.getOrderInfo();
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.post) {
                vm.post();
            }
            return true;
        });
        binding.rbLogisticService.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int score = (int) rating;
            vm.logisticsScore.set(score);
            binding.tvLogisticServiceScore.setText(OrderDetail.fomatScore(score));
        });
        binding.rbService.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int score = (int) rating;
            vm.serviceScore.set(score);
            binding.tvServiceScore.setText(OrderDetail.fomatScore(score));
        });
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

    @Override
    public void getOrderDetailSuccess() {
        setListener();
        //加载商品评分
        //模拟商品数据
        binding.llCommentContent.removeAllViews();
        vm.goodsComments.clear();
        for (OrderDetail.ItemBean itemBean : vm.orderDetail.get().getItem()) {
            GoodsComment goodsComment = new GoodsComment();
            goodsComment.setItem_id(itemBean.getItem_id());
            goodsComment.setItem_img(itemBean.getItem_img());
            ItemCommentInputBinding commentInputBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_comment_input, null, false);
            commentInputBinding.setData(goodsComment);
            vm.goodsComments.add(goodsComment);
            commentInputBinding.rb.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                int score = (int) rating;
                commentInputBinding.tvCommentTx.setText(OrderDetail.fomatScore(score));
                goodsComment.setCommodity_score(score);
            });
            commentInputBinding.isv.setOnUploadlistener(new ImageSelectView.OnImageUpLoadCompleteListener() {
                @Override
                public void onUpLoadComplete(String[] ids) {
                    imageSectionCount--;
                    if (imageSectionCount == 0) {//图片全都上传完毕
                        vm.postInfo();
                    }
                }

                @Override
                public void onUpLoadFailed(Throwable e) {
                    dismissLoaddingDialog();
                    showToast("上传图片失败");
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
    public int uploadImg() {
        imageSectionCount = 0;
        for (int i =0 ; i < binding.llCommentContent.getChildCount(); i++) {
            ItemCommentInputBinding itemCommentInputBinding = DataBindingUtil.getBinding(binding.llCommentContent.getChildAt(i));
            if (itemCommentInputBinding.isv.getImgSize() > 0) {
                itemCommentInputBinding.isv.uploadImage();
                imageSectionCount++;
            }
        }
        return imageSectionCount;
    }

    @Override
    public void setImgIds() {
        for (int i =0 ; i < binding.llCommentContent.getChildCount(); i++) {
            ItemCommentInputBinding itemCommentInputBinding = DataBindingUtil.getBinding(binding.llCommentContent.getChildAt(i));
            if (itemCommentInputBinding.isv.getImgSize() > 0) {
                vm.goodsComments.get(i).setImgs(Arrays.asList(itemCommentInputBinding.isv.getResult()));
            }
        }
    }
}

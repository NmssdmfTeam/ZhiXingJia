package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityReplayBinding;
import com.zhihangjia.mainmodule.viewmodel.ReplyVM;

/**
* @description 回复
* @author chenbin
* @date 2018/11/21 17:34
* @version v3.2.0
*/
public class ReplyActivity extends BaseTitleActivity {
    private final String TAG = ReplyActivity.class.getSimpleName();
    private ReplyVM vm;
    private ActivityReplayBinding binding;

    @Override
    public String setTitle() {
        return "写评论";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityReplayBinding) baseViewBinding;
        baseTitleBinding.tTitle.inflateMenu(R.menu.post);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_replay;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ReplyVM(this);
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
                break;
        }
    }
}

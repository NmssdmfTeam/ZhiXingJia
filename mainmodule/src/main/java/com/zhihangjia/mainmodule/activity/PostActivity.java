package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.bean.Upload;
import com.nmssdmf.commonlib.bean.UploadImage;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.net.IServiceLib;
import com.nmssdmf.commonlib.net.http.OkHttpClientProvider;
import com.nmssdmf.commonlib.net.retrofit.HttpObserver;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.view.ImageSelectView;
import com.nmssdmf.commonlib.view.TagView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.PostContent;
import com.zhihangjia.mainmodule.callback.PostCB;
import com.zhihangjia.mainmodule.databinding.ActivityPostBinding;
import com.zhihangjia.mainmodule.databinding.ItemPostContentBinding;
import com.zhihangjia.mainmodule.databinding.ItemPostTagBinding;
import com.zhihangjia.mainmodule.viewmodel.PostVM;
import com.zhixingjia.bean.mainmodule.BbsCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 发帖页面
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class PostActivity extends BaseActivity implements PostCB {
    private ActivityPostBinding binding;
    private final String TAG = PostActivity.class.getSimpleName();
    private PostVM vm;
    private ImageSelectView currentImageSelectView;
    private List<ItemPostTagBinding> itemPostTagBindings = new ArrayList<>();
    private int imageSectionCount;
//    private PostContent.VideoInfo videInfo;
//    private int videoIndex = -1;

    Map<Integer, PostContent.VideoInfo> map = new HashMap<>();
    private int videoCount = 0;

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
        if (!TextUtils.isEmpty(vm.catId)) {
            binding.tvTitle.setText(vm.catname);
            binding.ivIcon.setVisibility(View.GONE);
            setListener();
        } else {
            binding.tvTitle.setVisibility(View.VISIBLE);
            vm.getBbsCat();
        }
        //默认添加一个，且不能删除
        addContent();
        ItemPostContentBinding itemPostContentBinding = DataBindingUtil.findBinding(binding.llContent.getChildAt(0));
        itemPostContentBinding.btnRemove.setVisibility(View.INVISIBLE);
    }

    private void setListener() {
        binding.tTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.vBlackBackgroud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePanel();
            }
        });
        binding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.post) {
//                    uploadImg();
                    uploadVideo();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.vBlackBackgroud.getVisibility() == View.VISIBLE) {
            closePanel();
            return;
        }
        super.onBackPressed();
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
                    //所有图片选择不可显示视频
//                    List<ImageData> temps = (List<ImageData>)data.getExtras().getSerializable("datas");
//                    for (ImageData imageData : temps) {
//                        if (!CommonUtils.isEmpty(imageData.getVideoPath())) {
//                            refreshVideo(false);
//                            break;
//                        }
//                    }
                }
                break;
            case 100: {
                if (resultCode == 101) {//照片
                    currentImageSelectView.addCameraImage();
                } else if (resultCode == 102) {//视频快照
//                    refreshVideo(false);
                    currentImageSelectView.addCameraVideo(data);
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void addContent() {
        if (binding.llContent.getChildCount() >= 9) { //模块数不能超过9个
            ToastUtil.showMsg("图文数量不可超过9个");
            return;
        }
        final ItemPostContentBinding itemPostContentBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_post_content, null, false);

//        int childCount = binding.llContent.getChildCount();
//        boolean showVideo = true;
//        for (int i = 0; i < childCount; i++) {
//            ItemPostContentBinding itemPostContentBinding1 = DataBindingUtil.findBinding(binding.llContent.getChildAt(i));
//            if (itemPostContentBinding1.isv.hasVideo()) {
//                showVideo = false;
//                break;
//            }
//        }
        itemPostContentBinding.isv.setShowVideo(true);

        itemPostContentBinding.isv.setOnUploadlistener(new ImageSelectView.OnImageUpLoadCompleteListener() {
            @Override
            public void onUpLoadComplete(String[] ids) {
                imageSectionCount--;
                if (imageSectionCount == 0) {//图片全都上传完毕
                    getContent();
                }
            }

            @Override
            public void onUpLoadFailed(Throwable e) {
                dismissLoaddingDialog();
            }

            @Override
            public void onAddImageClick(ImageSelectView imageSelectView) {
                currentImageSelectView = imageSelectView;
            }

            @Override
            public void deleteImage(UploadImage uploadImage) {
//                if (!CommonUtils.isEmpty(uploadImage.getVideoPath())) {
//                    refreshVideo(true);
//                }
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

    /**
     *是否选择视频
     * @param video
     */
    private void refreshVideo(boolean video){
        int childCount = binding.llContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ItemPostContentBinding itemPostContentBinding1 = DataBindingUtil.findBinding(binding.llContent.getChildAt(i));

            itemPostContentBinding1.isv.setShowVideo(video);
        }
    }

    public void uploadVideo(){
        showLoaddingDialog();
        int childCount = binding.llContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ItemPostContentBinding itemPostContentBinding = DataBindingUtil.findBinding(binding.llContent.getChildAt(i));
            if (itemPostContentBinding.isv.getImgSize() > 0) {
                for (int j = 0;j <  itemPostContentBinding.isv.getFilePathList().size(); j++) {
                    UploadImage uploadImage = itemPostContentBinding.isv.getFilePathList().get(j);
                    if (!CommonUtils.isEmpty(uploadImage.getVideoPath())) {
                        videoCount += 1;
                        doUploadVideo(new File(uploadImage.getVideoPath()), i, j);
                    }
                }
            }
        }
        if (videoCount == 0) {//没有视频，直接上传图片
            uploadImg();
        }
    }

    public void doUploadVideo(final File file, final int productDescribeIndex, final int imgIndex) {

        RequestBody request_body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), request_body);
        IServiceLib iService = new Retrofit.Builder()
                .baseUrl(BaseConfig.IMAGEUPLOADIP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClient()).build().create(IServiceLib.class);
        iService.uploadFile(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new HttpObserver<Upload>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtil.getInstance().showToast("上传视频错误");
                        dismissLoaddingDialog();
                    }

                    @Override
                    public void onNext(Upload uploadImage) {
                        videoCount -= 1;
                        if (StringConfig.OK.equals(uploadImage.getStatus_code())) {
                            PostContent.VideoInfo videoInfo = new PostContent.VideoInfo();
                            videoInfo.setVideo_id(uploadImage.getData().getImage_id());
                            videoInfo.setPosition(String.valueOf(imgIndex));
//                            videInfo = videoInfo;
                            map.put(productDescribeIndex, videoInfo);
//                            videoIndex = productDescribeIndex;
                        } else {
                            dismissLoaddingDialog();
                            ToastUtil.getInstance().showToast("上传视频失败");
                        }
                        if (videoCount == 0) {
                            uploadImg();
                        }

                    }
                });
    }



    /**
     * 图片上传
     */
    public void uploadImg() {
        showLoaddingDialog();
        int childCount = binding.llContent.getChildCount();
        imageSectionCount = 0;
        for (int i = 0; i < childCount; i++) {
            ItemPostContentBinding itemPostContentBinding = DataBindingUtil.findBinding(binding.llContent.getChildAt(i));
            if (itemPostContentBinding.isv.getImgSize() > 0) {
                imageSectionCount++;
                itemPostContentBinding.isv.uploadImage();
            }
        }
        if (imageSectionCount == 0) {
            //图片个数为0，直接获取文本内容,否则等待图片上传完毕后，再获取文本+图片内容
            getContent();
        }
    }

    public void getContent() {
        List<PostContent> postContents = new ArrayList<>();
        int childCount = binding.llContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ItemPostContentBinding itemPostContentBinding = DataBindingUtil.findBinding(binding.llContent.getChildAt(i));
            if (itemPostContentBinding.isv.getImgSize() > 0 || !TextUtils.isEmpty(itemPostContentBinding.etContent.getText().toString())) {//内容或者图片不为空
                PostContent postContent = new PostContent();
                postContent.setNote(itemPostContentBinding.etContent.getText().toString());
                postContent.setImgs(itemPostContentBinding.isv.getResult());
                if (map.size() > 0) {
                    Set<Integer> set = map.keySet(); //取出所有的key值
                    for (Integer j : set) {
                        if (i == j) {
                            PostContent.VideoInfo videoInfo = map.get(j);
                            videoInfo.setImage_id(itemPostContentBinding.isv.getImgIds().get(Integer.valueOf(videoInfo.getPosition())));
                            postContent.setVideoinfo(videoInfo);
                            break;
                        }
                    }
                }
                postContents.add(postContent);
            }
        }
        vm.postContent(postContents, binding.etTitle.getText().toString());
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
            final ItemPostTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_post_tag, null, false);
            itemPostTagBinding.tvTag.setText(category.getCate_name());
            itemPostTagBinding.tvTag.setMode(TagView.SINGLEMODE);
            itemPostTagBinding.tvTag.setTagId(category.getCate_id());
            itemPostTagBinding.tvTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ItemPostTagBinding itemPostTag : itemPostTagBindings) {
                        itemPostTag.tvTag.setSelected(false);
                    }
                    itemPostTagBinding.tvTag.setSelected(true);
                    closePanel();
                    binding.tvTitle.setText(itemPostTagBinding.tvTag.getText());
                    vm.currentCat = itemPostTagBinding.tvTag.getTagId();
                }
            });
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

    private void closePanel() {
        binding.llTags.setVisibility(View.GONE);
        binding.vBlackBackgroud.setVisibility(View.GONE);
    }
}

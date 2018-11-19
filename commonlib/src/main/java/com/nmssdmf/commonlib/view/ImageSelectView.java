package com.nmssdmf.commonlib.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.adapter.ImageSelectAdapter;
import com.nmssdmf.commonlib.bean.UploadImage;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.FileUtil;
import com.nmssdmf.commonlib.util.ImageUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.PermissionCompat;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.widget.GridSpacingItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Create by mahuafeng on  16/5/11
 * <p>
 * 图片选择器,带图片上传功能
 * <p>
 * <p>
 * <p>
 */

public class ImageSelectView extends LinearLayout implements ImageSelectAdapter.ViewClickListener {
    private final String TAG = ImageSelectView.class.getSimpleName();

    private Context context;

    private RecyclerView rv_image_rl;

    private ImageSelectAdapter adapter;
//    private List<String> imgs = new ArrayList<>(); //用于adapter
    private List<UploadImage> uploadImages = new ArrayList<UploadImage>(); //用于adapter显示
    private List<String> imageIds = new ArrayList<>();//用于上传给服务器

    private String temp_path;//拍照时候的照片地址

    private OnImageUpLoadCompleteListener upload_listener;
    private boolean isNeed = false;//图片是否非必填

    private int wrap_width = 0; // 图片＋删除图标整体的宽度
    private int wrap_height = 0;// 图片＋删除图标整体的宽度
    private int sdv_size = 0; // 组件每个图片的宽度\高度

    //以下属性的数值请对照布局文件
    private int IMAGE_MARGIN = 12;//每张图之间的间距
    private int VIEW_PADDING_LEFT = 16;// ImageSelectView右边
    private int VIEW_PADDING_RIGHT = 16;// ImageSelectView右边
    private int DELETE_IMAGE_SIZE = 18; // 删除图标的高宽

    public ImageSelectView(Context context) {
        super(context);
        initView(context);
    }

    public ImageSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        initView(context);
    }

    private void initView(Context context) {

        if (isInEditMode()) {
            return;
        }
        this.context = context;


        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_image_select, null);
        VIEW_PADDING_RIGHT = DensityUtil.dpToPx(context, 16);
        VIEW_PADDING_LEFT = DensityUtil.dpToPx(context, 16);
        IMAGE_MARGIN = DensityUtil.dpToPx(context, 12);
        /**
         * 计算每张图外围的宽度(图片＋删除图标整体的宽度)，因为高==宽，所以也是每张图外围的高度<br/>
         * 外围的宽度=(屏幕宽度-ImageSelectView的左右padding和margin-item的间距*3)/4
         *
         */
        wrap_width = (DensityUtil.getScreenWidth(context) - VIEW_PADDING_RIGHT - VIEW_PADDING_LEFT - IMAGE_MARGIN * 3) / 4;//图片宽度


        rv_image_rl = (RecyclerView) view.findViewById(R.id.rv_image_rl);
        rv_image_rl.addItemDecoration(new GridSpacingItemDecoration(4,IMAGE_MARGIN,false));//item之间的间距

        UploadImage uploadImage = new UploadImage();
        uploadImage.setType(1);
        uploadImages.add(uploadImage);
        adapter = new ImageSelectAdapter(uploadImages, wrap_width, this);
        rv_image_rl.setAdapter(adapter);
        rv_image_rl.setHasFixedSize(true);
        rv_image_rl.setLayoutManager(new GridLayoutManager(context, 4));

        //图片高度 = (屏幕宽度 - 左右间距 - 间隔)/4 - 一半的删除图标
        sdv_size = wrap_width;

        addView(view);
    }

    private void init(AttributeSet attrs) {
//        if (attrs != null) {
//            TypedArray arr = getContext().obtainStyledAttributes(attrs, com.android.internal.R.styleable.View);
//            VIEW_PADDING_RIGHT = arr.getDimension(com.android.internal.R.styleable.View_paddingRight, 0);
//            VIEW_PADDING_LEFT = arr.getDimension(com.android.internal.R.styleable.View_paddingLeft, 0);
//            arr.recycle();
//        }
        VIEW_PADDING_RIGHT = getPaddingRight();
        VIEW_PADDING_LEFT = getPaddingLeft();
        JLog.i(TAG, "VIEW_PADDING_RIGHT:" + VIEW_PADDING_RIGHT + ",VIEW_PADDING_LEFT:" + VIEW_PADDING_LEFT);
    }

    private void toShowDialog() {
        if (adapter.getImageSize() < BaseConfig.MAX_IMG) {
            temp_path = FileUtil.getBaseImageDir() + System.currentTimeMillis() + ".jpg";
            showAddImageDialog((Activity) context, (BaseConfig.MAX_IMG - adapter.getImageSize()), temp_path);
        } else {
            ToastUtil.getInstance().showToast( "图片数目已达上限");
        }
    }

    /**
     * 通过相册和拍照选择图片
     *
     * @param activity
     * @param count    相册选择的张数
     * @param filePath 拍照保存的文件名
     * @description AlertDialog如果不引用support包，则是居中显示底部按钮
     */
    public static void showAddImageDialog(final Activity activity, final int count, final String filePath) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        final android.support.v7.app.AlertDialog dialog;
        final LinearLayout layout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.alert_dialog_select_image, null);

        builder.setView(layout);
        TextView tv_add_image_phone = (TextView) layout.findViewById(R.id.tv_add_image_phone);
        TextView tv_add_image_galley = (TextView) layout.findViewById(R.id.tv_add_image_galley);

        dialog = builder.create();
        final PackageManager pm = activity.getPackageManager();

        tv_add_image_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (!PermissionCompat.getInstance().checkGalleryPermission(activity)) {
                    return;
                }
                getImageFromCamera(activity, filePath);
            }
        });

        tv_add_image_galley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (!PermissionCompat.getInstance().checkGalleryPermission(activity)) {
                    return;
                }
                getImageFromAlbum(activity, count);
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(DensityUtil.dpToPx(activity,300), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static void getImageFromAlbum(Activity activity, int count) {
        Intent intent = new Intent(activity, ImageGalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, ImageGalleryActivity.IMAGE_SELECT_REQUEST);
    }

    public static void getImageFromCamera(Activity activity, String filePath) {
        String saveDir = FileUtil.getBaseImageDir();
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT < 24) {
                    Uri uri = Uri.fromFile(new File(filePath));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, IntegerConfig.REQUEST_CODE_CAMERA_IMAGE);
                    activity.startActivityForResult(intent, IntegerConfig.REQUEST_CODE_CAMERA_IMAGE);
                } else {
                    //适配安卓7.0
                    ContentValues contentValues=new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA,
                            new File(filePath).getAbsolutePath());
                    Uri uri= activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                    activity.grantUriPermission(activity.getPackageName(),uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    activity.startActivityForResult(intent, IntegerConfig.REQUEST_CODE_CAMERA_IMAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.getInstance().showToast("手机无可用SD卡");
        }
    }

    public void notifyDataSetChanged() {
        if (adapter.getImageSize() == BaseConfig.MAX_IMG) {
            removeAddImageView();
        }
        adapter.notifyDataSetChanged();
    }

    private void removeAddImageView() {
        for (int i = 0; i < adapter.getData().size();i++) {
            if (adapter.getData().get(i).getItemType() == 1) {
                adapter.getData().remove(i);
                break;
            }
        }
    }

    /**
     * 拍照返回添加
     */
    public void addCameraImage() {
        UploadImage uploadImage = new UploadImage();
        uploadImage.setUrl(temp_path);
        uploadImage.setImage_id("");
        if (adapter.getData().size() == 0)
            adapter.getData().add(uploadImage);
        else
            adapter.getData().add(adapter.getData().size() - 1,uploadImage);
        if (adapter.getImageSize() == BaseConfig.MAX_IMG) {
            removeAddImageView();
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 从相册返回添加
     *
     * @param data
     */
    public void addAlbumImage(Intent data) {
        if (data == null) {
            return;
        }
        List<String> temps = data.getExtras().getStringArrayList("datas");
        for (String path : temps) {
            UploadImage uploadImage = new UploadImage();
            uploadImage.setUrl(path);
            uploadImage.setImage_id("");
            if (adapter.getData().size() == 0)
                adapter.getData().add(uploadImage);
            else
                adapter.getData().add(adapter.getData().size() - 1,uploadImage);
        }
        if (adapter.getImageSize() == BaseConfig.MAX_IMG) {
            removeAddImageView();
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加图片
     *
     * @param path 图片的地址可以是本地图片和服务器图片地址
     * @param id   如果是服务器图片，需要给出图片的id
     */
    public void addImage(String path, String id) {
        if (uploadImages != null && path != null) {
            if (adapter.getImageSize() < BaseConfig.MAX_IMG) {
                UploadImage uploadImage = new UploadImage();
                uploadImage.setUrl(path);
                uploadImage.setImage_id(id);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isFull(int size) {
        imageIds.clear();
        for (int i = 0; i < size; i++) {
            String image_id = adapter.getData().get(i).getImage_id();
            if (TextUtils.isEmpty(image_id)) {
                return false;
            }
            imageIds.add(image_id);
        }
        return true;
    }

    /**
     * 上传图片到服务端
     */
    public void uploadImage() {
        int imageSize = adapter.getImageSize();
        if (imageSize == 0 && !isNeed) {
            ToastUtil.getInstance().showToast("请至少添加一张图片");
            upload_listener.onUpLoadFailed(new Exception("There is no file need to upload"));
            return;
        }

        // 上传之前先判断是不是所有的都是已上传的土拍呢，如果是则直接走回调
        if (isFull(imageSize)) {
            if (upload_listener != null) {
                upload_listener.onUpLoadComplete(toStringArray(imageIds));
                return;
            }
        }
        for (int i = 0; i < imageSize; i++) {
            if (adapter.getData().get(i).equals("")) {
                final String file_path = adapter.getData().get(i).getUrl();
                File file = ImageUtil.getCompressFile(file_path);
                // create your getFile object here
                if (file == null) {
                    upload_listener.onUpLoadFailed(new Exception("New file failed with the file path:" + file_path));
                    ToastUtil.getInstance().showToast( "上传图片失败");
                    return;
                }
                doUploadImage(file, i);
            }
        }
    }


        /**
         * upload image to the server
         *
         * @param file
         * @param index
         */
    private void doUploadImage(final File file, final int index) {

//        RequestBody request_body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), request_body);
//
//        RxRequest.createLib(4).uploadImage(body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new JushiObserver<BaseData<UploadImage>>() {
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        upload_listener.onUpLoadFailed(e);
//                        CommonUtils.showToast( context.getString(R.string.upload_image_failed));
//                    }
//
//                    @Override
//                    public void onNext(BaseData<UploadImage> obj) {
//                        if (Config.OK.equals(obj.getStatus_code())) {
//                            image_ids.add(index, obj.getData().getImage_id());
//                            image_ids.remove(index + 1);
//                            if (CommonUtils.isFull(image_ids, image_ids.size())) {
//                                if (upload_listener != null) {
//                                    upload_listener.onUpLoadComplete(toStringArray(image_ids));
//                                }
//                            }
//                        } else {
//                            upload_listener.onUpLoadFailed(new Exception("Http request failed with status code 0 "));
//                            CommonUtils.showToast( context.getString(R.string.upload_image_failed));
//                        }
//                    }
//                });
    }

    private String[] toStringArray(List<String> image_ids) {
        String[] ss = new String[image_ids.size()];
        for (int i = 0; i < image_ids.size(); i++) {
            ss[i] = image_ids.get(i);
        }
        return ss;
    }


    public void setOnUploadlistener(OnImageUpLoadCompleteListener upload_listener) {
        this.upload_listener = upload_listener;
    }

//    public List<UploadImage> getData() {
//        return adapter.getData();
//    }
//
//    public void setData(List<UploadImage> datas) {
//        if (datas != null && datas.size() > 0) {
//            for (UploadImage path : datas) {
//                if (adapter.getImageSize() < BaseConfig.MAX_IMG) {
//                    adapter.getData().add(path);
//                }
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }

    public String[] getResult() {
        return toStringArray(imageIds);
    }

    @Override
    public void onAddViewClick() {
        toShowDialog();
        if (upload_listener != null) {
            upload_listener.onAddImageClick(this);
        }
    }

    public interface OnImageUpLoadCompleteListener {
        void onUpLoadComplete(String[] ids);

        void onUpLoadFailed(Throwable e);

        void onAddImageClick(ImageSelectView imageSelectView);
    }
}

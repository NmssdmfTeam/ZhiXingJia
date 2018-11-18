package com.nmssdmf.commonlib.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.FileUtil;
import com.nmssdmf.commonlib.util.ImageUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.PermissionCompat;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.widget.FullyLinearLayoutManager;

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

public class ImageSelectView extends LinearLayout {
    private final String TAG = ImageSelectView.class.getSimpleName();

    private Context context;

    private RecyclerView rv_image_rl;
    private TextView tv_add_image;

    private ImageAdapter adapter_img;
    private List<String> imgs = new ArrayList<>(); //用于adapter

    private String temp_path;//拍照时候的照片地址

    private List<String> image_ids = new ArrayList<>();//用于上传给服务器
    private OnImageUpLoadCompleteListener upload_listener;
    private boolean isNeed = false;//图片是否非必填

    private int wrap_width = 0; // 图片＋删除图标整体的宽度
    private int wrap_height = 0;// 图片＋删除图标整体的宽度
    private int sdv_size = 0; // 组件每个图片的宽度\高度

    //以下属性的数值请对照布局文件
    private int IMAGE_MARGIN = 16;//每张图之间的间距
    private int VIEW_PADDING_LEFT = 8;// ImageSelectView右边
    private int VIEW_PADDING_RIGHT = 8;// ImageSelectView右边
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

    public void setNeed(boolean need) {
        isNeed = need;
    }

    public void setImage_ids(List<String> image_ids) {
        this.image_ids = image_ids;
    }

    public void setImgs(List<String> imgs) {//询价页面回复sku数据，恢复图片数据
        this.imgs = imgs;
        adapter_img.setImgs(imgs);
        adapter_img.notifyDataSetChanged();
    }

    private void initView(Context context) {

        if (isInEditMode()) {
            return;
        }
        this.context = context;

        adapter_img = new ImageAdapter(imgs, context);

        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_image_select, null);

        /**
         * 计算每张图外围的宽度(图片＋删除图标整体的宽度)，因为高==宽，所以也是每张图外围的高度<br/>
         * 外围的宽度=(屏幕宽度-ImageSelectView的左右padding和margin-item的间距*3)/4
         *
         */
        wrap_width = (DensityUtil.getScreenWidth(context) - VIEW_PADDING_RIGHT - VIEW_PADDING_LEFT - DensityUtil.dpToPx(context, IMAGE_MARGIN - DELETE_IMAGE_SIZE / 2) * 3) / 4;
        wrap_height = wrap_width + 1/*- DensityUtil.dpToPx(context, SUIT_SIZE)*/;
        /**
         * 设置整个组件的宽高</p>
         *
         * 宽: ViewGroup.LayoutParams.MATCH_PARENT</p>
         * 高: wrap_width-DensityUtil.dpToPx(context, 8)
         * 这里DensityUtil.dpToPx(context, 8)为一个便宜量，为了使组件顶部不至于空白太多，因为一般组件上方是TextView（参考图片），而此TextView底部会有一个空白
         */
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, wrap_height);
        view.setLayoutParams(params);
        view.setOrientation(LinearLayout.HORIZONTAL);

        rv_image_rl = (RecyclerView) view.findViewById(R.id.rv_image_rl);
        tv_add_image = (TextView) view.findViewById(R.id.tv_add_image);
        rv_image_rl.addItemDecoration(new SpacesItemDecoration(DensityUtil.dpToPx(context, IMAGE_MARGIN - DELETE_IMAGE_SIZE / 2)));//item之间的间距

        rv_image_rl.setAdapter(adapter_img);
        rv_image_rl.setHasFixedSize(true);
        rv_image_rl.setLayoutManager(new FullyLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        //图片高度 = (屏幕宽度 - 左右间距 - 间隔)/4 - 一半的删除图标
        sdv_size = wrap_width - DensityUtil.dpToPx(context, DELETE_IMAGE_SIZE / 2);
        LayoutParams param = new LayoutParams(sdv_size, sdv_size);
        param.topMargin = DensityUtil.dpToPx(context, DELETE_IMAGE_SIZE / 2);
        tv_add_image.setLayoutParams(param);

        tv_add_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toShowDialog();
            }
        });

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
        if (imgs.size() < BaseConfig.MAX_IMG) {
            temp_path = FileUtil.getBaseImageDir() + System.currentTimeMillis() + ".jpg";
            showAddImageDialog((Activity) context, (BaseConfig.MAX_IMG - imgs.size()), temp_path);
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
                Uri uri = Uri.fromFile(new File(filePath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, IntentConfig.REQUEST_CODE_CAMERA_IMAGE);
                activity.startActivityForResult(intent, IntentConfig.REQUEST_CODE_CAMERA_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.getInstance().showToast("手机无可用SD卡");
        }
    }

    public void notifyDataSetChanged() {
        if (imgs.size() == 4) {
            tv_add_image.setVisibility(GONE);
        }
        adapter_img.notifyDataSetChanged();
    }

    /**
     * 拍照返回添加
     */
    public void addCameraImage() {
        imgs.add(temp_path);
        image_ids.add("");
        if (imgs.size() == 4) {
            tv_add_image.setVisibility(GONE);
        }
        adapter_img.notifyDataSetChanged();
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
            imgs.add(path);
            image_ids.add("");
        }
        if (imgs.size() == 4) {
            tv_add_image.setVisibility(GONE);
        }
        adapter_img.notifyDataSetChanged();
    }

    /**
     * 添加图片
     *
     * @param path 图片的地址可以是本地图片和服务器图片地址
     * @param id   如果是服务器图片，需要给出图片的id
     */
    public void addImage(String path, String id) {
        if (imgs != null && path != null) {
            if (imgs.size() < BaseConfig.MAX_IMG) {
                imgs.add(path);
                image_ids.add(id);
            }
        }
        adapter_img.notifyDataSetChanged();
    }

    /**
     * 上传图片到服务端
     */
    public void uploadImage() {
        if (imgs.size() == 0 && !isNeed) {
            ToastUtil.getInstance().showToast("请至少添加一张图片");
            upload_listener.onUpLoadFailed(new Exception("There is no file need to upload"));
            return;
        }

        // 上传之前先判断是不是所有的都是已上传的土拍呢，如果是则直接走回调
        if (CommonUtils.isFull(image_ids, image_ids.size())) {
            JLog.i(TAG, "image_ids size:" + image_ids.size() + "||" + new Gson().toJson(image_ids) + ",upload_listener:" + upload_listener);
            if (upload_listener != null) {
                upload_listener.onUpLoadComplete(toStringArray(image_ids));
                return;
            }
        }
        for (int i = 0; i < imgs.size(); i++) {
            if (image_ids.get(i).equals("")) {
                final String file_path = imgs.get(i);
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

    public List<String> getData() {
        return imgs;
    }

    public void setData(List<String> datas) {
        if (datas != null && datas.size() > 0) {
            for (String path : datas) {
                if (imgs.size() < BaseConfig.MAX_IMG) {
                    imgs.add(path);
                }
            }
        }
        adapter_img.notifyDataSetChanged();
    }

    public String[] getResult() {
        return toStringArray(image_ids);
    }

    public interface OnImageUpLoadCompleteListener {
        void onUpLoadComplete(String[] ids);

        void onUpLoadFailed(Throwable e);
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageVH> {
        private List<String> imgs = new ArrayList<>();
        private Context context;
        private boolean candelete = true;// 是否可以删除
        private int flag = 0;
        private String TAG = ImageAdapter.class.getSimpleName();

        public ImageAdapter(List<String> imgs, Context context) {
            this.imgs = imgs;
            this.context = context;
        }

        public ImageAdapter(List<String> imgs, Context context, boolean candelete) {
            this.imgs = imgs;
            this.context = context;
            this.candelete = candelete;
        }

        public ImageAdapter(List<String> imgs, Context context, int flag) {
            this.imgs = imgs;
            this.context = context;
            this.flag = flag;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        @Override
        public ImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_img, parent, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(wrap_width, wrap_height));
            ImageVH vh = new ImageVH(view);

            vh.sdv = (GlideImageView) view.findViewById(R.id.sdv);
            vh.iv = (ImageView) view.findViewById(R.id.iv);

            JLog.d(TAG, "sdv_size = " + sdv_size);
            RelativeLayout.LayoutParams sdv_param = new RelativeLayout.LayoutParams(sdv_size, sdv_size);
            sdv_param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            sdv_param.setMargins(0, DELETE_IMAGE_SIZE / 2, DELETE_IMAGE_SIZE, 0);
            vh.sdv.setLayoutParams(sdv_param);

            return vh;
        }

        @Override
        public void onBindViewHolder(final ImageVH holder, final int position) {
            String uri;
            if (imgs != null && imgs.size() > 0) {
                if (imgs.get(position) != null && imgs.get(position).startsWith("http")) {
                    uri = imgs.get(position);
                } else {
                    uri = "file://" + imgs.get(position);
                }
                JLog.i(TAG, "uri:" + uri);
                Glide.with(context)
                        .load(uri)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .error(R.drawable.no_pic))
                        .into(holder.sdv);

                if (candelete) {
                    holder.iv.setVisibility(View.VISIBLE);
                    holder.iv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            imgs.remove(position);
                            image_ids.remove(position);

                            if (imgs.size() < 4) {
                                tv_add_image.setVisibility(VISIBLE);
                            }

                            notifyDataSetChanged();
                        }
                    });
                } else {
                    holder.iv.setVisibility(View.GONE);
                }
                if (flag != 0) {
                    holder.iv.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return imgs.size();
        }
    }

    public class ImageVH extends RecyclerView.ViewHolder {
        public GlideImageView sdv;
        public ImageView iv;
        public View root;

        public ImageVH(View itemView) {
            super(itemView);
            this.root = itemView;
        }
    }

    /**
     * 设置recyclerview间距的
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
//            outRect.left = space;
            outRect.right = space;
//            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
//            if (parent.getChildPosition(view) == 0)
//                outRect.top = space;
        }
    }
}

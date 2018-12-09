package com.zhixingjia.personmodule.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.FileUtil;
import com.nmssdmf.commonlib.util.ImagePathUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.PersonInfoCB;
import com.zhixingjia.personmodule.databinding.ActivityPersonInfoBinding;
import com.zhixingjia.personmodule.viewmodule.PersonInfoVM;
import com.zhixingjia.personmodule.window.ModifyAvatarWindow;

import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.System.currentTimeMillis;

public class PersonInfoActivity extends BaseTitleActivity implements PersonInfoCB{
    private final String TAG = PersonInfoActivity.class.getSimpleName();

    private ActivityPersonInfoBinding binding;
    private WheelPickerWindow selectSexWindow;
    private ModifyAvatarWindow modifyAvatarWindow;

    private PersonInfoVM vm;
    public static final int CROP_RESOULT = 124;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new PersonInfoVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "个人信息";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityPersonInfoBinding) baseViewBinding;
        binding.setVm(vm);
        vm. initData();

        binding.tvSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectSexWindow == null) {
                    selectSexWindow = new WheelPickerWindow(PersonInfoActivity.this, vm.getSexList(), vm);
                }

                selectSexWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
                selectSexWindow.setCurrentItem(vm.userInfo.get().getSex().equals("0") ? 0 : 1);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_person_info;
    }

    private void startForCrop(Uri inputUri) {
        JLog.i(TAG, "=====crop====");
        vm.cropFileName = currentTimeMillis() + ".png";
        vm.cropFilePath = FileUtil.getBaseImageDir() + vm.cropFileName;
        File mCropFile = new File(vm.cropFilePath);
        vm.outPutUri = Uri.fromFile(mCropFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= 24) {
            vm.cropFilePath = mCropFile.getAbsolutePath();
            JLog.i(TAG, "=====outPutUri====" + vm.outPutUri + "  cropFilePath:" + vm.cropFilePath);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, vm.outPutUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String url = ImagePathUtil.getPath(this, inputUri);
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, vm.outPutUri);
        }

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//去除黑边
        intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁框会重叠
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", false);
        startActivityForResult(intent, CROP_RESOULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PersonInfoVM.CHANGE_NAME_REQUEST_CODE:{
                int type = data.getExtras().getInt(IntentConfig.TYPE);
                if (type == 1) {
                    vm.userInfo.get().setNickname(data.getExtras().getString(IntentConfig.NAME));
                } else {
                    vm.userInfo.get().setRealname(data.getExtras().getString(IntentConfig.NAME));
                }
                break;
            }
            case ModifyAvatarWindow.GALLEY_RESULT:
                if (data != null) {
                    //data有数据的情况下  data:Intent { dat=content://media/external/images/media/33374 flg=0x1 }
                    //data无数据情况下 data:null
                    //有数据的情况下  path:/storage/emulated/0/DCIM/Camera/IMG_20160622_165103.jpg
                    //file:///storage/emulated/0/trading_image/1521689088089.png
                    //7.0  content://com.android.providers.media.documents/document/image%3A1015
                    JLog.i(TAG, "data.getData():" + data.getData());
                    if (Build.VERSION.SDK_INT >= 24) {
                        File imageUri = new File(ImagePathUtil.getPath(this, data.getData()));
                        Uri dataUri = FileProvider.getUriForFile(this, "com.zhihangjia.project.FileProvider", imageUri);
                        startForCrop(dataUri);
                    } else {
                        ContentResolver resolver = getContentResolver();
                        Uri originalUri = data.getData(); // 获得图片的uri
                        if (originalUri != null) {
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            // 根据索引值获取图片路径
                            String path = cursor.getString(column_index);
                            JLog.i(TAG, "[onActivityResult] path:" + path);
                            startForCrop(Uri.parse("file://" + path));
                            /*if (!cursor.isClosed()) { //应用不支持api14以下，14以上自动关闭
                                cursor.close();
                            }*/
                        }
                    }
                }
                break;
            case IntegerConfig.REQUEST_CODE_CAMERA_IMAGE:
                JLog.i(TAG, "image_path:" + modifyAvatarWindow.getImage_path() + ",SDK:" + Build.VERSION.SDK_INT);
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri inputUri = FileProvider.getUriForFile(this, "com.zhihangjia.project.FileProvider", new File(modifyAvatarWindow.getImage_path()));
                    startForCrop(inputUri);
                } else {
                    if (null == modifyAvatarWindow.getImage_path() || "".equals(modifyAvatarWindow.getImage_path())) {
                        return;
                    }
                    File indexFile = new File(modifyAvatarWindow.getImage_path());
                    if (indexFile.exists()) {
//                        sdv_img.setImageURI(Uri.parse("file://" + vm.image_path));
                        startForCrop(Uri.parse("file://" + modifyAvatarWindow.getImage_path()));
                    }
                }
                break;

            case CROP_RESOULT://裁剪
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        //content://com.jushi.trading.FileProvider/rc_external_path/trading_image/1497957667706.png
                        try {
//                        sdv_img.setImageURI(Uri.parse("file://" + cropFilePath));
                            File cropFile = new File(vm.cropFilePath);
                            if (cropFile.exists()) {
                                JLog.i(TAG, "==显示裁剪问题==");
                                vm.orginUrl = "file://" + vm.cropFilePath;
                                vm.doUploadImage(cropFile);
                            } else {
                                JLog.i(TAG, "裁剪图片不存在");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            MediaStore.Images.Media.insertImage(getContentResolver(),
                                    vm.cropFilePath, vm.cropFileName, "trading_" + currentTimeMillis());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        File file = new File(vm.cropFilePath);
                        if (file.exists()) {
//                        sdv_img.setImageURI(Uri.parse("file://" + cropFilePath));
                            vm.orginUrl = "file://" + vm.cropFilePath;
                            vm.doUploadImage(file);
                        } else {
                            JLog.i(TAG, "裁剪图片不存在");
                            //CommonUtils.showToast(activity, "裁剪图片异常,请检查图片查看器是否拥有文件权限");
                        }
                    }
                }
        }
    }

    @Override
    public void showModifyAvatarWindow() {
        if (modifyAvatarWindow == null)
            modifyAvatarWindow = new ModifyAvatarWindow(this);
        modifyAvatarWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0,0);
    }

    @Override
    public void dismissModifyAvatarWindow() {
        modifyAvatarWindow.dismiss();
    }

}

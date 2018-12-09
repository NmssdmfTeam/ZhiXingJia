package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.Upload;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.net.IServiceLib;
import com.nmssdmf.commonlib.net.http.OkHttpClientProvider;
import com.nmssdmf.commonlib.net.retrofit.HttpObserver;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.personmodule.activity.ChangeNameActivity;
import com.zhixingjia.personmodule.callback.PersonInfoCB;
import com.zhixingjia.service.PersonService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class PersonInfoVM extends BaseVM implements WheelPickerWindowCB {
    public static final int CHANGE_NAME_REQUEST_CODE = 100;
    private PersonInfoCB cb;
    public String cropFilePath;
    public String cropFileName;
    public Uri outPutUri;
    public String orginUrl;
    private String imageId;

    private List<String> sexList = new ArrayList<>();
    public final ObservableField<UserInfo> userInfo = new ObservableField<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PersonInfoVM(PersonInfoCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void initData() {
        String userInfoString = PreferenceUtil.getString(PrefrenceConfig.USER_INFO, "");
        if (StringUtil.isEmpty(userInfoString)) {

        } else {
            userInfo.set(new Gson().fromJson(userInfoString, UserInfo.class));
        }


        sexList.add("男");
        sexList.add("女");
    }

    public void tvNickNameClick(View view, int type) {
        Bundle bundle = new Bundle();
        if (type == 1) {
            bundle.putString(IntentConfig.NAME, userInfo.get().getNickname());
        } else {
            bundle.putString(IntentConfig.NAME, userInfo.get().getRealname());
        }
        bundle.putInt(IntentConfig.TYPE, type);
        cb.doIntentForResult(ChangeNameActivity.class, bundle, CHANGE_NAME_REQUEST_CODE);
    }

    public void onModifyAvatarClick(View view) {
        cb.showModifyAvatarWindow();
    }

    @Override
    public void tvSureClick(String item, final int position) {
        if (userInfo.get().getSex().equals(String.valueOf(position)))//没有变化
            return;
        Map<String, String> map = new HashMap<>();
        map.put("sex", String.valueOf(position));
        cb.showLoaddingDialog();
        infoSave(map,1);
    }

    public void modifyAvatar() {
        Map<String, String> map = new HashMap<>();
        map.put("avatar", imageId);
        cb.showLoaddingDialog();
        infoSave(map,2);
    }

    private void infoSave(final Map<String, String> params, final int type) {
        HttpUtils.doHttp(subscription, RxRequest.create(PersonService.class, HttpVersionConfig.API_MY_INFO_SAVE).infoSave(params), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                if (type == 2) {
                    userInfo.get().setAvatar(orginUrl);
                } else if (type == 1) {
                    userInfo.get().setSex(params.get("sex"));
                }
                PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfo.get()));
                cb.dismissModifyAvatarWindow();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    /**
     * upload image to the server
     *
     * @param file
     */
    public void doUploadImage(final File file) {
        RequestBody request_body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), request_body);
        IServiceLib iService = new Retrofit.Builder()
                .baseUrl(BaseConfig.IMAGEUPLOADIP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClient()).build().create(IServiceLib.class);
        iService.uploadImage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new HttpObserver<Upload>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtil.getInstance().showToast("上传图片失败");
                    }

                    @Override
                    public void onNext(Upload uploadImage) {
                        if (StringConfig.OK.equals(uploadImage.getStatus_code())) {
                            imageId = uploadImage.getData().getImage_id();
                            modifyAvatar();
                        } else {
                            ToastUtil.getInstance().showToast("上传图片失败");
                        }
                    }
                });
    }


    public List<String> getSexList() {
        return sexList;
    }

    public void setSexList(List<String> sexList) {
        this.sexList = sexList;
    }
}

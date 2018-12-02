package com.zhixingjia.personmodule.viewmodule;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.bean.Upload;
import com.nmssdmf.commonlib.bean.UploadImage;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.net.IServiceLib;
import com.nmssdmf.commonlib.net.http.OkHttpClientProvider;
import com.nmssdmf.commonlib.net.retrofit.HttpObserver;
import com.nmssdmf.commonlib.util.FileUtil;
import com.nmssdmf.commonlib.util.ImageUtil;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.TradeArea;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.personmodule.bean.ApplySupplierBean;
import com.zhixingjia.personmodule.callback.ApplySupplierCB;
import com.zhixingjia.service.MainService;

import java.io.File;
import java.io.Serializable;
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
 * 成为卖家viewmodel
 * Create by chenbin on 2018/12/1
 * <p>
 * <p>
 */
public class ApplySupplierVM extends BaseVM {
    private ApplySupplierCB callback;
    public List<TradeArea> businessCircleList = new ArrayList<>();
    public final ObservableField<ApplySupplierBean> applySupplier = new ObservableField<>();
    private List<HouseBean.CateBean> cateBeans = new ArrayList<>();
    public final static int SELECTCATEGORY_RESULT = 13543;
    public String frontIDCardPathTemp = FileUtil.getBaseImageDir() + System.currentTimeMillis() + "FrontIDCard.jpg";
    public String backIDCardPathTemp = FileUtil.getBaseImageDir() + System.currentTimeMillis() + "BackIDCard.jpg";
    public String businessLicenseCardPathTemp = FileUtil.getBaseImageDir() + System.currentTimeMillis() + "License.jpg";
    public UploadImage frontIDCardPathUploadBean;
    public UploadImage backIDCardPathUploadBean;
    public UploadImage businessLicenseCardPathUploadBean;
    private int imageIndex = -1;     //表示点击那个证件操作
    private int uploadImageCount = 0;     //上传图片数量


    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ApplySupplierVM(ApplySupplierCB callBack) {
        super(callBack);
        this.callback = callBack;
        getTradeArea();
        initData();
    }

    private void initData() {
        applySupplier.set(new ApplySupplierBean());
//        businessCircleList.add("")
    }

    public void onShowWheelPickPopWindowClick(View view) {
        callback.showBusinessCirclePopWindow();
    }

    public void onCategotyViewClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.MERCHANT_CATEGORY, (Serializable) cateBeans);
        callback.doInentClassNameForResult(ActivityNameConfig.SELECTCATEGORY_ACTIVITY, bundle, SELECTCATEGORY_RESULT);
    }

    /**
     * 获取所在商圈
     */
    public void getTradeArea() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_TRADE_AREA).getTradeArea(),
                new ServiceCallback<BaseListData<TradeArea>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<TradeArea> tradeAreaBaseListData) {
                        businessCircleList = tradeAreaBaseListData.getData();
                        List<String> tradeAreaName = new ArrayList<>();
                        for (TradeArea tradeArea : tradeAreaBaseListData.getData()) {
                            tradeAreaName.add(tradeArea.getTrade_name());
                        }
                        callback.setPickViewData(tradeAreaName);
                    }

                    @Override
                    public void onDefeated(BaseListData<TradeArea> tradeAreaBaseListData) {

                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECTCATEGORY_RESULT) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    cateBeans = (List<HouseBean.CateBean>) bundle.getSerializable(IntentConfig.MERCHANT_CATEGORY);
                    String category = "";
                    String category_ids = "";
                    if (cateBeans != null && cateBeans.size() > 0) {
                        int i = 0;
                        for (HouseBean.CateBean cateBean : cateBeans) {
                            if (i != cateBeans.size() - 1) {
                                category += (cateBean.getCate_name() + "、");
                                category_ids += (cateBean.getCate_id() + ",");
                            } else {
                                category += cateBean.getCate_name();
                                category_ids += cateBean.getCate_id();
                            }
                            i++;
                        }
                    }
                    applySupplier.get().setMain_camp_name(category);
                    applySupplier.get().setMain_camp(category_ids);
                }
            } else if (requestCode == IntegerConfig.REQUEST_CODE_CAMERA_IMAGE) {
                String temp_path = "";
                if (imageIndex == 1) {
                    temp_path = frontIDCardPathTemp;
                } else if (imageIndex == 2) {
                    temp_path = backIDCardPathTemp;
                } else if (imageIndex == 3) {
                    temp_path = businessLicenseCardPathTemp;
                }
                UploadImage uploadImage = new UploadImage();
                uploadImage.setUrl(temp_path);
                uploadImage.setImage_id("");
                if (imageIndex == 1) {
                    frontIDCardPathUploadBean = uploadImage;
                    callback.loadFrontIDCardImg(temp_path);
                } else if (imageIndex == 2) {
                    backIDCardPathUploadBean = uploadImage;
                    callback.loadBackIDCardImg(temp_path);
                } else if (imageIndex == 3) {
                    businessLicenseCardPathUploadBean = uploadImage;
                    callback.loadLicenseImg(temp_path);
                }
            } else if (requestCode == ImageGalleryActivity.IMAGE_SELECT_REQUEST) {
                if (data == null) {
                    return;
                }
                List<String> temps = data.getExtras().getStringArrayList("datas");
                if (temps != null && temps.size() > 0) {
                    UploadImage uploadImage = new UploadImage();
                    uploadImage.setUrl(temps.get(0));
                    uploadImage.setImage_id("");
                    if (imageIndex == 1) {
                        frontIDCardPathUploadBean = uploadImage;
                        callback.loadFrontIDCardImg(uploadImage.getUrl());
                    } else if (imageIndex == 2) {
                        backIDCardPathUploadBean = uploadImage;
                        callback.loadBackIDCardImg(uploadImage.getUrl());
                    } else if (imageIndex == 3) {
                        businessLicenseCardPathUploadBean = uploadImage;
                        callback.loadLicenseImg(uploadImage.getUrl());
                    }
                }
            }
        }
    }

    /**
     * 上传正面身份证照片
     *
     * @param view
     */
    public void onFrontIDCardClick(View view) {
        imageIndex = 1;
        callback.showSelectImageDialog(frontIDCardPathTemp);
    }

    /**
     * 上传背面身份证照片
     *
     * @param view
     */
    public void onBackIDCardClick(View view) {
        imageIndex = 2;
        callback.showSelectImageDialog(backIDCardPathTemp);
    }

    /**
     * 上传营业执照
     *
     * @param view
     */
    public void onBackBusinessLicenceClick(View view) {
        imageIndex = 3;
        callback.showSelectImageDialog(businessLicenseCardPathTemp);
    }

    /**
     * 点击提交
     * @param view
     */
    public void onCommitClick(View view) {
        if (!isDataValidate()) {
            return;
        }
        callback.showLoaddingDialog();
        //上传图片
        uploadImageCount = 0;
        if (TextUtils.isEmpty(frontIDCardPathUploadBean.getImage_id())) {
            uploadImg(frontIDCardPathUploadBean.getUrl(), 0);
            uploadImageCount++;
        }
        if (TextUtils.isEmpty(backIDCardPathUploadBean.getImage_id())) {
            uploadImg(backIDCardPathUploadBean.getUrl(), 1);
            uploadImageCount++;
        }
        if (TextUtils.isEmpty(businessLicenseCardPathUploadBean.getImage_id())) {
            uploadImg(businessLicenseCardPathUploadBean.getUrl(), 2);
            uploadImageCount++;
        }
        if (uploadImageCount == 0) {//不需要上传图片
            becomeSeller();
        }
    }

    /**
     * 成为卖家
     */
    private void becomeSeller() {
        Map<String,String> map = new HashMap<>();
        map.put("company_name", applySupplier.get().getCompany_name());
        map.put("trade_area", applySupplier.get().getTrade_area());
        map.put("co_addr", applySupplier.get().getCo_addr());
        map.put("deputy", applySupplier.get().getDeputy());
        map.put("co_phone", applySupplier.get().getCo_phone());
        map.put("main_camp", applySupplier.get().getMain_camp());
        map.put("card_front", frontIDCardPathUploadBean.getImage_id());
        map.put("card_back", backIDCardPathUploadBean.getImage_id());
        map.put("license_img", businessLicenseCardPathUploadBean.getImage_id());
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY_BECOME_SELLER).becomeSeller(map),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                //改变当前用户状态为审核中
                String userInfoStr = PreferenceUtil.getString(PrefrenceConfig.USER_INFO, "");
                if (!TextUtils.isEmpty(userInfoStr)) {
                    UserInfo userInfo = new Gson().fromJson(userInfoStr, UserInfo.class);
                    userInfo.setVerify_status("审核中");
                    PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfo));
                }
                callback.finishActivity();
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    private void uploadImg(String filepath, final int index) {
        File file = ImageUtil.getCompressFile(filepath);
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
                        callback.dismissLoaddingDialog();
                    }

                    @Override
                    public void onNext(Upload uploadImage) {
                        if (StringConfig.OK.equals(uploadImage.getStatus_code())) {
                            if (index == 0) {
                                frontIDCardPathUploadBean.setImage_id(uploadImage.getData().getImage_id());
                            } else if (index == 1) {
                                backIDCardPathUploadBean.setImage_id(uploadImage.getData().getImage_id());
                            } else if (index == 2) {
                                businessLicenseCardPathUploadBean.setImage_id(uploadImage.getData().getImage_id());
                            }
                            uploadImageCount--;
                            if (uploadImageCount == 0) {//上传完成
                                //提交数据
                                becomeSeller();
                            }
                        } else {
                            ToastUtil.getInstance().showToast("上传图片失败");
                        }
                    }
                });
    }

    /**
     * 检验数据是否有效
     */
    private boolean isDataValidate() {
        if (TextUtils.isEmpty(applySupplier.get().getCompany_name())) {
            callback.showToast("请填写店铺名称");
            return false;
        }
        if (TextUtils.isEmpty(applySupplier.get().getTrade_area())) {
            callback.showToast("请填写所在商圈");
            return false;
        }
        if (TextUtils.isEmpty(applySupplier.get().getCo_addr())) {
            callback.showToast("请填写店铺地址");
            return false;
        }
        if (TextUtils.isEmpty(applySupplier.get().getDeputy())) {
            callback.showToast("请填写法人代表");
            return false;
        }
        if (TextUtils.isEmpty(applySupplier.get().getCo_phone())) {
            callback.showToast("请填写店铺电话");
            return false;
        }
        if (TextUtils.isEmpty(applySupplier.get().getMain_camp())) {
            callback.showToast("请填写主营产品");
            return false;
        }
        if (frontIDCardPathUploadBean == null) {
            callback.showToast("请填写身份证正面");
            return false;
        }
        if (backIDCardPathUploadBean == null) {
            callback.showToast("请填写身份证反面");
            return false;
        }
        if (businessLicenseCardPathUploadBean == null) {
            callback.showToast("请填写营业执照");
            return false;
        }
        return true;
    }
}

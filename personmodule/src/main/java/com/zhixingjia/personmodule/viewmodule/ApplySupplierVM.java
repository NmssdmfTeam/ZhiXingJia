package com.zhixingjia.personmodule.viewmodule;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jushi.gallery.activity.ImageGalleryActivity;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.bean.UploadImage;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntegerConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.FileUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.TradeArea;
import com.zhixingjia.personmodule.bean.ApplySupplierBean;
import com.zhixingjia.personmodule.callback.ApplySupplierCB;
import com.zhixingjia.service.MainService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void becomeSeller() {

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
                    if (cateBeans != null && cateBeans.size() > 0) {
                        int i = 0;
                        for (HouseBean.CateBean cateBean : cateBeans) {
                            if (i != cateBeans.size() - 1)
                                category += (cateBean.getCate_name() + "、");
                            else
                                category += cateBean.getCate_name();
                            i++;
                        }
                    }
                    applySupplier.get().setMain_camp_name(category);
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
}

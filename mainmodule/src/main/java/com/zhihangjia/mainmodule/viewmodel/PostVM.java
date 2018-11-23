package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.bean.PostContent;
import com.zhihangjia.mainmodule.callback.PostCB;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description  发帖viewmodel
* @author chenbin
* @date 2018/11/19 15:07
* @version v3.2.0
*/
public class PostVM extends BaseVM {
    private PostCB callBack;
    public String currentCat;
    public String catId;
    public String catname;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PostVM(PostCB callBack) {
        super(callBack);
        this.callBack = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = callBack.getIntentData();
        if (bundle != null) {
            catId = bundle.getString(IntentConfig.CAT_ID);
            catname = bundle.getString(IntentConfig.NAME);
            currentCat = catId;
        }
    }

    /**
     * 添加图文
     * @param view
     */
    public void onAddContentClick(View view) {
        callBack.addContent();
    }

    /**
     * 点击标题
     * @param view
     */
    public void onTitleClick(View view) {
        if (TextUtils.isEmpty(catId))
            callBack.showChooseWindow();
    }

    public void getBbsCat() {
        String cat_json = PreferenceUtil.getString(PrefrenceConfig.BBS_CAT,"");
        if (!TextUtils.isEmpty(cat_json)) {
            List<BbsCategory> bbsCategories = new Gson().fromJson(cat_json, new TypeToken<List<BbsCategory>>() {}.getType());
            callBack.setCat(bbsCategories);
        } else {
            getMessageCat();
        }
    }

    /**
     * 获取类别
     */
    public void getMessageCat() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_CATE).getBbsCate(),
                new ServiceCallback<BaseListData<BbsCategory>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<BbsCategory> bbsCategoryBaseListData) {
                        if (bbsCategoryBaseListData.getData() != null) {
                            callBack.setCat(bbsCategoryBaseListData.getData());
                            //对信息中心的消息类型进行缓存
                            PreferenceUtil.setStringValue(PrefrenceConfig.BBS_CAT, new Gson().toJson(bbsCategoryBaseListData.getData()));
                        }
                    }

                    @Override
                    public void onDefeated(BaseListData<BbsCategory> bbsCategoryBaseListData) {

                    }
                });
    }

    public void postContent(List<PostContent> postContents, String title) {
        callBack.showLoaddingDialog();
        if (postContents == null || postContents.size() == 0){
            ToastUtil.showMsg("请填写内容");
            callBack.dismissLoaddingDialog();
            return;
        }
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showMsg("请填写标题");
            callBack.dismissLoaddingDialog();
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("cate_id", currentCat);
        params.put("title", title);
        params.put("contents", new Gson().toJson(postContents));
        //先上传图片
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INSERT).postBbs(params),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {
                callBack.dismissLoaddingDialog();
            }

            @Override
            public void onSuccess(Base base) {
                if (StringConfig.OK.equals(base.getStatus_code())) {
                    callBack.dismissLoaddingDialog();
                    callBack.finishActivity();
                    //刷新消息
                    RxBus.getInstance().send(RxEvent.BbsEvent.BBS_INSERT, null);
                }
            }

            @Override
            public void onDefeated(Base base) {
                callBack.dismissLoaddingDialog();
            }
        });
    }
}

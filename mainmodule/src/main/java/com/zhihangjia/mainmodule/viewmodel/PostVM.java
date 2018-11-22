package com.zhihangjia.mainmodule.viewmodel;

import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.PostCB;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.MainService;

import java.util.List;

/**
* @description  发帖viewmodel
* @author chenbin
* @date 2018/11/19 15:07
* @version v3.2.0
*/
public class PostVM extends BaseVM {
    private PostCB callBack;
    public String currentCat;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PostVM(PostCB callBack) {
        super(callBack);
        this.callBack = callBack;
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
}

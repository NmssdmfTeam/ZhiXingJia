package com.zhihangjia.mainmodule.viewmodel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.SelectCategoriesCB;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.service.MainService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectCategoryVM extends BaseVM {
    private SelectCategoriesCB callback;
    public List<HouseBean.CateBean> selectCateBean = new ArrayList<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SelectCategoryVM(SelectCategoriesCB callBack) {
        super(callBack);
        this.callback = callBack;
        getHouseCate();
        initData();
    }

    private void initData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            selectCateBean = (List<HouseBean.CateBean>) bundle.getSerializable(IntentConfig.MERCHANT_CATEGORY);
        }
    }

    /**
     * 获取建材家居分类列表
     */
    public void getHouseCate() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE_CATE).getHouseCate(),
                new ServiceCallback<BaseListData<HouseBean.CateBean>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<HouseBean.CateBean> cateBeanBaseListData) {
                        if (selectCateBean != null && selectCateBean.size() > 0) {
                            for (int i =0; i < cateBeanBaseListData.getData().size(); i++) {
                                HouseBean.CateBean cateBean = cateBeanBaseListData.getData().get(i);
                                for (HouseBean.CateBean selectedItem: selectCateBean) {
                                    if (cateBean.getCate_id().equals(selectedItem.getCate_id())) {
                                        cateBeanBaseListData.getData().get(i).setSelect(true);
                                    }
                                }
                            }
                        }
                        callback.setData(cateBeanBaseListData.getData());
                    }

                    @Override
                    public void onDefeated(BaseListData<HouseBean.CateBean> cateBeanBaseListData) {

                    }

                });
    }

    /**
     * 确定
     * @param view
     */
    public void onSureClick(View view) {
        List<HouseBean.CateBean> list = callback.getSelectedData();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.MERCHANT_CATEGORY, (Serializable) list);
        callback.setResultCode(Activity.RESULT_OK, bundle);
        callback.finishActivity();
    }

}

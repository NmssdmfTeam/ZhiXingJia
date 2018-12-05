package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.SearchActivity;
import com.zhihangjia.mainmodule.callback.AllCategoriesCB;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.service.MainService;

public class AllCategoriesVM extends BaseVM {
    private AllCategoriesCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AllCategoriesVM(AllCategoriesCB callBack) {
        super(callBack);
        this.callback = callBack;
        getHouseCate();
    }

    public void doSearch() {

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
                        callback.setData(cateBeanBaseListData.getData());
                    }

                    @Override
                    public void onDefeated(BaseListData<HouseBean.CateBean> cateBeanBaseListData) {

                    }

                });
    }

    public void onSearchClick(View view) {
        callback.doIntent(SearchActivity.class, null);
    }

}

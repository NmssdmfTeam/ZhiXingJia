package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MerchantEvaluateFragmentCB;
import com.zhixingjia.bean.mainmodule.Comment;
import com.zhixingjia.bean.mainmodule.ShopInfo;
import com.zhixingjia.service.MainService;

public class MerchantEvaluateFragmentVM extends BaseRecyclerViewFragmentVM {
    public final ObservableField<ShopInfo.MemberBean> memberInfo = new ObservableField<>();
    public String memberId;
    private String pages = "0";
    private MerchantEvaluateFragmentCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantEvaluateFragmentVM(MerchantEvaluateFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            pages = "0";
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_SHOPINFO_EVALUATE).getShopInfoEvaluate(memberId, pages),
                new ServiceCallback<BaseListData<Comment>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<Comment> commentBaseListData) {
                        callback.refreshAdapter(isRefresh, commentBaseListData.getData());
                        if (commentBaseListData.getData().size() > 0) {
                            pages = commentBaseListData.getData().get(commentBaseListData.getData().size() - 1).getComment_id();
                        }
                    }

                    @Override
                    public void onDefeated(BaseListData<Comment> commentBaseListData) {

                    }
                });
    }
}

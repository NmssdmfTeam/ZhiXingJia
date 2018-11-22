package com.zhihangjia.mainmodule.viewmodel;


import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MessageDetailCB;
import com.zhixingjia.bean.mainmodule.MessageComment;
import com.zhixingjia.bean.mainmodule.MessageDetail;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenbin
 * @version v3.2.0
 * @description 信息详情viewmodel
 * @date 2018/11/20 11:12
 */
public class MessageDetailVM extends BaseVM {
    private String messageId = "1";
    private MessageDetailCB cb;

    private MessageDetail detail;

    private List<MessageComment> list = new ArrayList<>();

    private int onlyLookBuilder = 0;//是否只看楼主，默认是0，0=否 1=是

    private int page = 1;//评论翻页
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageDetailVM(MessageDetailCB callBack) {
        super(callBack);
        this.cb = callBack;
    }

    public void getMessageDetail() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_VIEW).getMessageDetail(messageId), new ServiceCallback<BaseData<MessageDetail>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<MessageDetail> data) {
                detail = data.getData();
                cb.initView();
            }

            @Override
            public void onDefeated(BaseData<MessageDetail> data) {

            }
        });
    }

    public void getCommentList(final boolean isRefresh){
        Map<String, String> map = new HashMap<>();
        map.put("bbs_id", messageId);//必填，帖子ID
        map.put("pages", String.valueOf(page));//必填，分页的传，默认为1，加载一次加1，以此类推
        map.put("hot_sort", "asc");//默认传asc desc=倒序 asc=正序 hot=最热
        map.put("landlord", String.valueOf(onlyLookBuilder));//选填，是否只看楼主，默认是0，0=否 1=是
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_COMMENT).getCommentList(map), new ServiceCallback<BaseListData<MessageComment>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<MessageComment> data) {
                if (data.getData() != null && data.getData().size() > 0) {
                    cb.refreshComent(isRefresh, data.getData());
                }
            }

            @Override
            public void onDefeated(BaseListData<MessageComment> data) {

            }
        });
    }

    public void setDetail(MessageDetail detail) {
        this.detail = detail;
    }

    public MessageDetail getDetail() {
        return detail;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MessageComment> getList() {
        return list;
    }

    public void setList(List<MessageComment> list) {
        this.list = list;
    }

    public int getOnlyLookBuilder() {
        return onlyLookBuilder;
    }

    public void setOnlyLookBuilder(int onlyLookBuilder) {
        this.onlyLookBuilder = onlyLookBuilder;
        getCommentList(true);
    }
}

package com.zhihangjia.mainmodule.viewmodel;


import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.MessageCenterModuleActivity;
import com.zhihangjia.mainmodule.activity.ReplyActivity;
import com.zhihangjia.mainmodule.callback.MessageDetailCB;
import com.zhihangjia.mainmodule.callback.XYHeadLineDetailCB;
import com.zhixingjia.bean.mainmodule.HeadLines;
import com.zhixingjia.bean.mainmodule.MessageComment;
import com.zhixingjia.bean.mainmodule.MessageDetail;
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
public class XYHeadLineDetailVM extends BaseVM {
    public String messageId = "1";
    private XYHeadLineDetailCB cb;

    public final ObservableField<HeadLines> detail = new ObservableField<>();

    private List<MessageComment> list = new ArrayList<>();
    private List<String> flipList = new ArrayList<>();
    public List<Uri> imageUrls = new ArrayList<>();
    public String firstContent;                         //用于分享的描述

    public final ObservableBoolean onlyLookBuilder = new ObservableBoolean(false);//是否只看楼主，默认是0，0=否 1=是
    public final ObservableBoolean isHot = new ObservableBoolean(false);//最赞
    public final ObservableBoolean isSortDesc = new ObservableBoolean(false);//最赞

    public final ObservableInt zanNum = new ObservableInt(0);
    public final ObservableInt commentNum = new ObservableInt(0);

    public final ObservableInt page = new ObservableInt(1);//评论翻页

    private boolean isGive;                 //是否点过赞
    private int zanNumOriginal;             //点赞实际个数
    private String user_name;
    private int position;                   //位置
    private int currentPageIndex = -1;           //pageView的currentPage Index;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public XYHeadLineDetailVM(final XYHeadLineDetailCB callBack) {
        super(callBack);
        this.cb = callBack;
        initData();

        onlyLookBuilder.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                getCommentList(true);
            }
        });

        isHot.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                getCommentList(true);
            }
        });
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            messageId = bundle.getString(IntentConfig.ID);
            position = bundle.getInt(IntentConfig.POSITION);
            currentPageIndex = bundle.getInt(IntentConfig.PAGEVIEW_CURRENT_INDEX, -1);
        }
        user_name = PreferenceUtil.getString(PrefrenceConfig.USER_NAME, "知行家276410");
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.BbsEvent.COMMENT_INSERT, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.BbsEvent.COMMENT_INSERT, this);
    }

    public void getYXHeadLineDetail() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_NEWS_HEADLINES_COMMENT).getYXHeadLineDetail(messageId), new ServiceCallback<BaseData<HeadLines>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<HeadLines> data) {
                detail.set(data.getData());
//                int maxPage = Integer.valueOf(detail.get().getComment_pages());
                flipList.clear();
//                for (int i = 1; i<= maxPage; i++) {
//                    flipList.add("第"+i+"页");
//                }
                isGive = "1".equals(data.getData().getGive_state());
                if (!TextUtils.isEmpty(data.getData().getComment_sum())) {
                    try {
                        int commentSum = Integer.valueOf(data.getData().getComment_sum());
                        if (commentSum > 99)
                            commentSum = 99;
                        commentNum.set(commentSum);
                    }catch (Exception e) {
                        commentNum.set(0);
                    }
                }
                if (!TextUtils.isEmpty(data.getData().getGive_sum())) {
                    try {
                        int giveSum = Integer.valueOf(data.getData().getGive_sum());
                        zanNumOriginal = giveSum;
                        if (giveSum > 99)
                            giveSum = 99;
                        zanNum.set(Integer.valueOf(giveSum));
                    } catch (Exception e) {
                        zanNum.set(0);
                    }
                }
                cb.initView();
                cb.refreshGiveInfo(zanNumOriginal, data.getData().getGive_info());
            }

            @Override
            public void onDefeated(BaseData<HeadLines> data) {

            }
        });
    }

    public void getCommentList(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("id", messageId);//必填，帖子ID
        if (!isRefresh)
            page.set(page.get()+1);
        map.put("pages", String.valueOf(page.get()));//必填，分页的传，默认为1，加载一次加1，以此类推
        map.put("hot_sort", isHot.get() ? "hot" : (isSortDesc.get() ? "desc" : "asc"));//默认传asc desc=倒序 asc=正序 hot=最热
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_NEWS_HEADLINES_COMMENT).getHeadLineCommentList(map), new ServiceCallback<BaseListData<MessageComment>>() {
            @Override
            public void onError(Throwable error) {
                cb.endFresh();
            }

            @Override
            public void onSuccess(BaseListData<MessageComment> data) {
                cb.endFresh();
                if (data.getData() != null && data.getData().size() > 0) {
                    cb.refreshComent(isRefresh, data.getData());
                }
            }

            @Override
            public void onDefeated(BaseListData<MessageComment> data) {
                cb.endFresh();
            }
        });
    }

    public void tvSortClick(View view) {
        isHot.set(false);
        isSortDesc.set(!isSortDesc.get());

        getCommentList(true);
    }

    public void onCommentClick(View view) {
        //是否登录
        String token = PreferenceUtil.getString(PrefrenceConfig.TOKEN, "");
        if (TextUtils.isEmpty(token)) {//未登录
            baseCallBck.doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.BBS_ID, messageId);
        baseCallBck.doIntent(ReplyActivity.class, bundle);
    }

    /**
     * 回到评论首部
     * @param view
     */
    public void onCommentBackClick(View view) {
        cb.scrollToTop();
    }

    public void onShareClick(View view) {
        cb.showShareWindow();
    }

    /**
     * 点赞
     */
    public void onZan(final String types, String relation_id, final int position) {
        Map<String,Object> map = new HashMap<>();
        map.put("types", types);
        map.put("relation_id", relation_id);
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_HEADLINES_GIVE_INSERT).yxHeadLinegiveInsert(map),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                if ("0".equals(types)) {
                    //刷新文章攒数量
                    String giveInfo = "";
                    if (isGive) {         //取消点赞
                        zanNumOriginal--;
                        if (zanNumOriginal == 0) {
                            giveInfo = detail.get().getGive_info().replaceFirst(user_name, "");
                        } else {
                            giveInfo = detail.get().getGive_info().replaceFirst(user_name+"、", "");
                        }
                        isGive = false;
                        detail.get().setGive_state("0");
                    } else {                //点赞
                        zanNumOriginal++;
                        if (zanNumOriginal == 1)
                            giveInfo = user_name;
                        else
                            giveInfo = user_name+"、"+detail.get().getGive_info();
                        isGive = true;
                        detail.get().setGive_state("1");
                    }
                    detail.get().setGive_info(giveInfo);
                    setZanNum();//刷新点赞数
                    cb.refreshGiveInfo(zanNumOriginal, giveInfo);
                } else {
                    //刷新评论
                    cb.onCommentZanRequestFinish(position);
                }
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    public void onZanClick(View view) {
        onZan("0",messageId,0);
    }

    private void setZanNum() {
        if (zanNumOriginal > 99) {
            zanNum.set(99);
        }
        zanNum.set(zanNumOriginal);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.BbsEvent.HEADLINE_COMMENT_INSERT://变更
                getYXHeadLineDetail();
                getCommentList(true);
                break;
        }
    }

    public int getPage() {
        return page.get();
    }

    public void setPage(int page) {
        this.page.set(page);
    }

    public List<MessageComment> getList() {
        return list;
    }

    public void setList(List<MessageComment> list) {
        this.list = list;
    }

    public List<String> getFlipList() {
        return flipList;
    }

    public void setFlipList(List<String> flipList) {
        this.flipList = flipList;
    }
}

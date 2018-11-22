package com.zhihangjia.mainmodule.viewmodel;


import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MessageDetailCB;
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
public class MessageDetailVM extends BaseVM {
    private String messageId = "1";
    private MessageDetailCB cb;

    public final ObservableField<MessageDetail> detail = new ObservableField<>();

    private List<MessageComment> list = new ArrayList<>();
    private List<String> flipList = new ArrayList<>();

    public final ObservableBoolean onlyLookBuilder = new ObservableBoolean(false);//是否只看楼主，默认是0，0=否 1=是
    public final ObservableBoolean isHot = new ObservableBoolean(false);//最赞
    public final ObservableBoolean isSortDesc = new ObservableBoolean(false);//最赞

    private int page = 1;//评论翻页

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageDetailVM(MessageDetailCB callBack) {
        super(callBack);
        this.cb = callBack;

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

    public void getMessageDetail() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_VIEW).getMessageDetail(messageId), new ServiceCallback<BaseData<MessageDetail>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<MessageDetail> data) {
                detail.set(data.getData());
                int maxPage = Integer.valueOf(detail.get().getComment_pages());
                flipList.clear();
                for (int i = 1; i<= maxPage; i++) {
                    flipList.add("第"+i+"页");
                }
                cb.initView();
            }

            @Override
            public void onDefeated(BaseData<MessageDetail> data) {

            }
        });
    }

    public void getCommentList(final boolean isRefresh) {
        if (isRefresh) {
            page = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("bbs_id", messageId);//必填，帖子ID
        map.put("pages", String.valueOf(page));//必填，分页的传，默认为1，加载一次加1，以此类推
        map.put("hot_sort", isHot.get() ? "hot" : (isSortDesc.get() ? "desc" : "asc"));//默认传asc desc=倒序 asc=正序 hot=最热
        map.put("landlord", onlyLookBuilder.get() ? "1" : "0");//选填，是否只看楼主，默认是0，0=否 1=是
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_COMMENT).getCommentList(map), new ServiceCallback<BaseListData<MessageComment>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<MessageComment> data) {
                if (data.getData() != null && data.getData().size() > 0) {
                    if (!isRefresh)
                        page += 1;
                    cb.refreshComent(isRefresh, data.getData());
                }
            }

            @Override
            public void onDefeated(BaseListData<MessageComment> data) {

            }
        });
    }

    public void tvSortClick(View view) {
        isHot.set(false);
        isSortDesc.set(!isSortDesc.get());

        getCommentList(true);
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

    public List<String> getFlipList() {
        return flipList;
    }

    public void setFlipList(List<String> flipList) {
        this.flipList = flipList;
    }
}

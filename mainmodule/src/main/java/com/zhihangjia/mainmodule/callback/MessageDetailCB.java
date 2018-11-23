package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.MessageComment;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public interface MessageDetailCB extends BaseCB {
    void initView();
    void refreshComent(boolean isRefresh, List<MessageComment> list);
    void scrollToTop();
    void onCommentZanRequestFinish(int position);
    void refreshGiveInfo(int zanNum, String giveNames);
    void setPageTitle(String title);
    void endFresh();
}

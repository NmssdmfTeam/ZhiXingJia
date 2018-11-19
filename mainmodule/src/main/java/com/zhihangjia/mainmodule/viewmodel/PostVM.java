package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.PostCB;

/**
* @description  发帖viewmodel
* @author chenbin
* @date 2018/11/19 15:07
* @version v3.2.0
*/
public class PostVM extends BaseVM {
    private PostCB callBack;
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
}

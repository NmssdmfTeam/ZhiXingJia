package com.zhixingjia.personmodule.viewmodule;

import android.view.View;

import com.zhixingjia.personmodule.callback.ModifyAvatarWindowCB;

import java.util.Observable;

/**
 * Create by chenbin on 2018/12/9
 * <p>
 * <p>
 */
public class ModifyAvatarWindowVM extends Observable {

    private ModifyAvatarWindowCB callback;

    /**
     * 不需要callback可以传null
     */
    public ModifyAvatarWindowVM(ModifyAvatarWindowCB callback) {
        this.callback = callback;
    }

    public void onClicktvPhone(View view) {
        callback.toTakePhoto();
    }

    public void onClicktvGalley(View view) {
        callback.toPhotoGallay();
    }

    public void onClicktvCancel(View view) {
        callback.toCancle();
    }
}

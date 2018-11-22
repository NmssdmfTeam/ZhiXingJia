package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MaterialsMerchantFragmentCB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/14 0014.
 */

public class MaterialsMerchantFragmentVM extends BaseVM {
    private MaterialsMerchantFragmentCB cb;

    public final ObservableField<String> keyword = new ObservableField<>();

    private List<Base> historys = new ArrayList<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MaterialsMerchantFragmentVM(MaterialsMerchantFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void tvCancelClick(View view) {
        keyword.set("");
    }

    public void ivDeleteClick(View view) {
        cb.clearHistory();
    }


    public void doSearch() {

    }

    public List<Base> getHistorys() {
        return historys;
    }

    public void setHistorys(List<Base> historys) {
        this.historys = historys;
    }



}

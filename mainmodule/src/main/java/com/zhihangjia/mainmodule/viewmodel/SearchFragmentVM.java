package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.SearchResultActivity;
import com.zhihangjia.mainmodule.callback.SearchFragmentCB;
import com.zhixingjia.bean.mainmodule.Seller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/14 0014.
 */

public class SearchFragmentVM extends BaseVM {
    public static final String TYPE_MATERIALS_MERCHANT = "TYPE_MATERIALS_MERCHANT";//商家
    public static final String TYPE_MATERIALS_MERCHANDISE = "TYPE_MATERIALS_MERCHANDISE";//商品
    public static final String TYPE_INFORMATION_CENTER = "TYPE_INFORMATION_CENTER";//信息中心
    private SearchFragmentCB cb;
    private String type = TYPE_MATERIALS_MERCHANT;

    public final ObservableField<String> keyword = new ObservableField<>();

    private List<String> historys = new ArrayList<>();
    private List<Seller> list = new ArrayList<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SearchFragmentVM(SearchFragmentCB callBack) {
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
        cb.closeKeyBoard();
        if (historys.contains(keyword.get())) {
            historys.remove(keyword.get());
        }
        historys.add(keyword.get());
        PreferenceUtil.setStringValue(type, new Gson().toJson(historys));//数据刷新
        cb.refreshHistory();

        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.TYPE, type);
        bundle.putString(IntentConfig.KEYWORD, keyword.get());
        cb.doIntent(SearchResultActivity.class, bundle);
    }


    /**
     * 获取历史数据
     */
    public void getHistroyData(){
        String histroy = PreferenceUtil.getString(type, "");
        if (!StringUtil.isEmpty(histroy)) {
            historys = new Gson().fromJson(histroy, new TypeToken<List<String>>() {}.getType());
        }
        if (historys.size() > 0) {
            cb.refreshHistory();
        }
    }

    public List<String> getHistorys() {
        return historys;
    }

    public void setHistorys(List<String> historys) {
        this.historys = historys;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Seller> getList() {
        return list;
    }

    public void setList(List<Seller> list) {
        this.list = list;
    }
}

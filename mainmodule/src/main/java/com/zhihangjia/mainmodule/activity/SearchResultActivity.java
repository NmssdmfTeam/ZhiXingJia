package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleRecyclerViewActivity;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MerchandiseAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantAdapter;
import com.zhihangjia.mainmodule.callback.SearchResultCB;
import com.zhihangjia.mainmodule.viewmodel.SearchFragmentVM;
import com.zhihangjia.mainmodule.viewmodel.SearchResultVM;

import java.util.List;

public class SearchResultActivity extends BaseTitleRecyclerViewActivity implements SearchResultCB{

   private final String TAG = SearchResultActivity.class.getSimpleName();

    private SearchResultVM vm;

    private MerchantAdapter merchantAdapter;
    private MerchandiseAdapter merchandiseAdapter;

    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel() {
        vm = new SearchResultVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        switch (vm.getType()) {
            case SearchFragmentVM.TYPE_MATERIALS_MERCHANT:{
                merchantAdapter = new MerchantAdapter(list);
                return merchantAdapter;
            }
            case SearchFragmentVM.TYPE_MATERIALS_MERCHANDISE:{
                merchandiseAdapter = new MerchandiseAdapter(list);
                return merchandiseAdapter;
            }
            case SearchFragmentVM.TYPE_INFORMATION_CENTER:{
                break;
            }
        }
        return null;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        vm.getData();
        super.initContent(savedInstanceState);
    }

    @Override
    public void refreshTitle(String title) {
        setTitle(title);
    }
}

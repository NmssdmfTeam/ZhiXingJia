package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.AllCategoriesCB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCategoriesVM extends BaseVM {

    private List<String> mainCategoryList = new ArrayList<>();
    private int mainCategoryIndex = 0;//记录选中的大类,避免循环

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AllCategoriesVM(AllCategoriesCB callBack) {
        super(callBack);
    }

    public void getData() {
        mainCategoryList.add("建材");
        mainCategoryList.add("定制家具");
        mainCategoryList.add("家用电器");
    }

    public void doSearch() {

    }

    public void changeMainCategory() {

    }

    public List<String> getMainCategoryList() {
        return mainCategoryList;
    }

    public void setMainCategoryList(List<String> mainCategoryList) {
        this.mainCategoryList = mainCategoryList;
    }

    public int getMainCategoryIndex() {
        return mainCategoryIndex;
    }

    public void setMainCategoryIndex(int mainCategoryIndex) {
        this.mainCategoryIndex = mainCategoryIndex;
    }
}

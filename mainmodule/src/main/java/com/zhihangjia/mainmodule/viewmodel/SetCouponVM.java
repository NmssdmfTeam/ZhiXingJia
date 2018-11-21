package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.SetCouponCB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class SetCouponVM extends BaseVM implements WheelPickerWindowCB {

    private final int WHEEL_PICKER_TYPE_TIME = 0;//弹出选择时间的
    private final int WHEEL_PICKER_TYPE_USE = 1;//弹出使用条件的
    private int wheelPickType = 0;

    private List<String> useConditionList = new ArrayList<>();
    private List<String> timeTypeList = new ArrayList<>();
    private SetCouponCB cb;

    public final ObservableField<String> timeType = new ObservableField<>();
    public final ObservableField<String> useCondition = new ObservableField();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SetCouponVM(SetCouponCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getData() {
        useConditionList.add("无门槛");
        useConditionList.add("订单金额满减");

        timeTypeList.add("时间段");
        timeTypeList.add("发放后生存周期");
    }

    public void chooseUseCondition(View view) {
        wheelPickType = WHEEL_PICKER_TYPE_USE;
        cb.showUseConditionWindow();
    }

    public void chooseTimeType(View view) {
        wheelPickType = WHEEL_PICKER_TYPE_TIME;
        cb.chooseTimeTypeWindow();
    }

    @Override
    public void tvSureClick(String item, int position) {
        switch (wheelPickType) {
            case WHEEL_PICKER_TYPE_TIME:{
                timeType.set(item);
                break;
            }
            case WHEEL_PICKER_TYPE_USE:{
                useCondition.set(item);
                break;
            }
        }
    }

    public List<String> getUseConditionList() {
        return useConditionList;
    }

    public void setUseConditionList(List<String> useConditionList) {
        this.useConditionList = useConditionList;
    }

    public List<String> getTimeTypeList() {
        return timeTypeList;
    }

    public void setTimeTypeList(List<String> timeTypeList) {
        this.timeTypeList = timeTypeList;
    }
}

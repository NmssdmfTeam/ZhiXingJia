package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.SetCouponCB;
import com.zhixingjia.bean.mainmodule.CouponSeller;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class SetCouponVM extends BaseVM implements WheelPickerWindowCB {

    private final int WHEEL_PICKER_TYPE_TIME = 0;//弹出选择时间的
    private final int WHEEL_PICKER_TYPE_USE = 1;//弹出使用条件的
    private int wheelPickType = 0;
    public String coupon_id;
    private int position;

    private List<String> useConditionList = new ArrayList<>();
    private List<String> timeTypeList = new ArrayList<>();
    private SetCouponCB cb;

    public final ObservableField<String> timeType = new ObservableField<>();
    public final ObservableField<String> useCondition = new ObservableField();
    public final ObservableField<CouponSeller> couponSeller = new ObservableField<>();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SetCouponVM(SetCouponCB callBack) {
        super(callBack);
        cb = callBack;
        initData();
        if (TextUtils.isEmpty(coupon_id)) {
            couponSeller.set(new CouponSeller());
            couponSeller.get().setStatus("0");
        } else {
            if ("all".equals(couponSeller.get().getCond())) {
                useCondition.set("无门槛");
            } else {
                useCondition.set("订单金额满减");
            }
            if ("1".equals(couponSeller.get().getTimetype())) {
                timeType.set("时间段");
            } else {
                timeType.set("发放后周期");
            }
        }
    }

    private void initData() {
        Bundle bundle = cb.getIntentData();
        if (bundle != null) {
            coupon_id = bundle.getString(IntentConfig.COUPON_ID);
            position = bundle.getInt(IntentConfig.POSITION);
            CouponSeller coupon = (CouponSeller) bundle.getSerializable(IntentConfig.COUPON_SELLER);
            couponSeller.set(coupon);
        }
    }

    public void getData() {
        useConditionList.add("无门槛");
        useConditionList.add("订单金额满减");

        timeTypeList.add("时间段");
        timeTypeList.add("发放后周期");
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
                if ("时间段".equals(item)) {
                    couponSeller.get().setTimetype("1");
                } else {
                    couponSeller.get().setTimetype("2");
                }
                break;
            }
            case WHEEL_PICKER_TYPE_USE:{
                useCondition.set(item);
                if ("无门槛".equals(item)) {
                    couponSeller.get().setCond("all");
                } else {
                    couponSeller.get().setCond("morethan");
                }
                break;
            }
        }
    }

    /**
     * 添加保存优惠券
     */
    public void couponSave() {
        Map<String,String> map = new HashMap<>();
        if (!TextUtils.isEmpty(coupon_id)) {
            map.put("coupon_id", coupon_id);
        }
        map.put("title", couponSeller.get().getTitle());
        map.put("description", couponSeller.get().getDescription());
        map.put("timetype", couponSeller.get().getTimetype());
        map.put("cond", couponSeller.get().getCond());
        map.put("decrease", couponSeller.get().getDecrease());
        map.put("status", couponSeller.get().getStatus());
        if (!TextUtils.isEmpty(couponSeller.get().getStarttime()))
            map.put("starttime", couponSeller.get().getStarttime());
        if (!TextUtils.isEmpty(couponSeller.get().getEndtime()))
            map.put("endtime", couponSeller.get().getEndtime());
        if (!TextUtils.isEmpty(couponSeller.get().getExpireday()))
            map.put("expireday", couponSeller.get().getExpireday());
        if (!TextUtils.isEmpty(couponSeller.get().getMorethannumber()))
            map.put("morethannumber", couponSeller.get().getMorethannumber());
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_COUPON_SAVEINFO).couponSaveInfo(map),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                if (TextUtils.isEmpty(coupon_id)) {
                    RxBus.getInstance().send(RxEvent.PersonInfoEvent.COUPON_INSERT,null);
                } else {
                    EventInfo eventInfo = new EventInfo();
                    eventInfo.setIndex(position);
                    eventInfo.setContent(couponSeller.get());
                    RxBus.getInstance().send(RxEvent.PersonInfoEvent.COUPON_SAVE,eventInfo);
                }
                cb.finishActivity();
                cb.showToast("添加优惠券成功");
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    public void delete() {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_COUPON_DEL).couponDel(coupon_id),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                EventInfo eventInfo = new EventInfo();
                eventInfo.setIndex(position);
                RxBus.getInstance().send(RxEvent.PersonInfoEvent.COUPON_DELTE,eventInfo);
                cb.showToast("删除成功");
                cb.finishActivity();
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    /**
     * 数据是否有效
     */
    private boolean isDataValidate() {
        if (TextUtils.isEmpty(couponSeller.get().getTitle())) {
            cb.showToast("请填写优惠券名称");
            return false;
        }
        if (TextUtils.isEmpty(couponSeller.get().getDescription())) {
            cb.showToast("请填写规则描述");
            return false;
        }
        if (TextUtils.isEmpty(couponSeller.get().getTimetype())) {
            cb.showToast("请选择时间类型");
            return false;
        }
        if (TextUtils.isEmpty(couponSeller.get().getCond())) {
            cb.showToast("请选择使用条件");
            return false;
        }
        if (TextUtils.isEmpty(couponSeller.get().getDecrease())) {
            cb.showToast("请填写优惠券金额");
            return false;
        }
        if (TextUtils.isEmpty(couponSeller.get().getStatus())) {
            cb.showToast("请选择优惠券状态");
            return false;
        }
        if ("1".equals(couponSeller.get().getTimetype())) {
            if (TextUtils.isEmpty(couponSeller.get().getStarttime()) || TextUtils.isEmpty(couponSeller.get().getEndtime())) {
                cb.showToast("请选择起止时间");
                return false;
            }
        }
        if ("2".equals(couponSeller.get().getTimetype())) {
            if (TextUtils.isEmpty(couponSeller.get().getExpireday())) {
                cb.showToast("请填写领取后生存天数");
                return false;
            }
        }
        if ("morethan".equals(couponSeller.get().getCond())) {
            if (TextUtils.isEmpty(couponSeller.get().getMorethannumber())) {
                cb.showToast("请填写订单金额");
                return false;
            }
        }
        return true;
    }

    public void onCouponSaveClick(View view) {
        if (isDataValidate()) {
            cb.showLoaddingDialog();
            couponSave();
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

    public void onSetStartDateClick(View view) {
        cb.showDateStartSelected("-");
    }

    public void onSetEndDateClick(View view) {
        cb.showDateEndSelected("-");
    }
}

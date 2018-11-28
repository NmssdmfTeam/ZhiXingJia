package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;
import com.zhixingjia.bean.personmodule.Address;
import com.zhixingjia.bean.personmodule.AddressInsertResult;
import com.zhixingjia.personmodule.callback.AddOrEditAddressCB;
import com.zhixingjia.service.PersonService;

import java.util.HashMap;
import java.util.Map;

/**
 * 新增地址viewmodel
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class AddOrEditAddressVM extends BaseVM {
    private AddOrEditAddressCB callback;

    public final ObservableField<String> username = new ObservableField<>();//收货人姓名
    public final ObservableField<String> phonenum = new ObservableField<>();//收货人手机
    public final ObservableField<String> area = new ObservableField<>();//收货人所在区域
    public final ObservableField<String> address = new ObservableField<>();//收货人详情地址
    public final ObservableBoolean isDefault = new ObservableBoolean();     //是否默认地址
    private String addrId;  //地址id
    private int position;


    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddOrEditAddressVM(AddOrEditAddressCB callBack) {
        super(callBack);
        this.callback = callBack;
        iniData();
    }

    private void iniData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null){
            addrId = bundle.getString(IntentConfig.ADDRID);
            position = bundle.getInt(IntentConfig.POSITION);
            Address addressinfo = (Address) bundle.getSerializable(IntentConfig.ADDR);
            username.set(addressinfo.getNames());
            phonenum.set(addressinfo.getMobile());
            area.set(addressinfo.getArea());
            address.set(addressinfo.getAddr());
            isDefault.set("0".equals(addressinfo.getIs_default())?false:true);
        }
    }

    public void save() {
        if (!isValidate())
            return;
        callback.showLoaddingDialog();
        Map<String,Object> map = new HashMap<>();
        map.put("names",username.get());
        map.put("mobile",phonenum.get());
        map.put("area",area.get());
        map.put("addr",address.get());
        if (isDefault.get()) {
            map.put("is_default",1);
        }
        if (TextUtils.isEmpty(addrId)) {
            addressInsert(map);
        } else {
            map.put("addr_id", addrId);
            saveAddress(map);
        }

    }

    private void addressInsert(Map<String,Object> map) {
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_ADDRESS_INSERT).addressInsert(map),
                new ServiceCallback<BaseData<AddressInsertResult>>() {
                    @Override
                    public void onError(Throwable error) {
                        callback.dismissLoaddingDialog();
                    }

                    @Override
                    public void onSuccess(BaseData<AddressInsertResult> addressInsertResultBaseData) {
                        callback.dismissLoaddingDialog();
                        if (StringConfig.OK.equals(addressInsertResultBaseData.getStatus_code())) {
                            Address addressInfo = new Address();
                            addressInfo.setAddr_id(addressInsertResultBaseData.getData().getAddr_id());
                            addressInfo.setAddr(address.get());
                            addressInfo.setArea(area.get());
                            addressInfo.setMobile(phonenum.get());
                            addressInfo.setNames(username.get());
                            EventInfo eventInfo = new EventInfo();
                            eventInfo.setContent(addressInfo);
                            RxBus.getInstance().send(RxEvent.PersonInfoEvent.ADDRESS_INSERT, eventInfo);
                            callback.finishActivity();
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<AddressInsertResult> addressInsertResultBaseData) {
                        callback.dismissLoaddingDialog();
                    }
                });
    }

    private void saveAddress(Map<String,Object> map) {
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_ADDRESS_INSERT).addressSave(map),
                new ServiceCallback<BaseData<AddressInsertResult>>() {
                    @Override
                    public void onError(Throwable error) {
                        callback.dismissLoaddingDialog();
                    }

                    @Override
                    public void onSuccess(BaseData<AddressInsertResult> addressInsertResultBaseData) {
                        callback.dismissLoaddingDialog();
                        if (StringConfig.OK.equals(addressInsertResultBaseData.getStatus_code())) {
                            Address saveData = new Address();
                            saveData.setAddr(address.get());
                            saveData.setAddr_id(addrId);
                            saveData.setArea(area.get());
                            saveData.setMobile(phonenum.get());
                            saveData.setNames(username.get());
                            saveData.setIs_default(isDefault.get()?"1":"0");
                            EventInfo eventInfo = new EventInfo();
                            eventInfo.setContent(saveData);
                            eventInfo.setIndex(position);
                            RxBus.getInstance().send(RxEvent.PersonInfoEvent.ADDRESS_SAVE, eventInfo);
                            callback.finishActivity();
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<AddressInsertResult> addressInsertResultBaseData) {
                        callback.dismissLoaddingDialog();
                    }
                });
    }

    public void onSaveClick(View view) {
        save();
    }

    /**
     * 获取所在区域列表
     */
    public void getArea() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_AREA).getArea(),
                new ServiceCallback<BaseListData<String>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<String> areas) {
                        if (StringConfig.OK.equals(areas.getStatus_code())) {
                            if (areas.getData() != null)
                                callback.setArea(areas.getData());
                        }
                    }

                    @Override
                    public void onDefeated(BaseListData<String> stringBaseListData) {

                    }
                });
    }

    /**
     * 数据是否有效
     */
    private boolean isValidate() {
        if (TextUtils.isEmpty(username.get())) {
            ToastUtil.getInstance().showToast("请填写收货人姓名");
            return false;
        }
        if (TextUtils.isEmpty(phonenum.get())) {
            ToastUtil.getInstance().showToast("收货人手机号");
            return false;
        }
        if (TextUtils.isEmpty(area.get())) {
            ToastUtil.getInstance().showToast("请选择收货人所在区域");
            return false;
        }
        if (TextUtils.isEmpty(address.get())) {
            ToastUtil.getInstance().showToast("请填写收货人详情地址");
            return false;
        }
        return true;
    }
}

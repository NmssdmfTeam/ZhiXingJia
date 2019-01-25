package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.Upload;
import com.nmssdmf.commonlib.bean.UploadImage;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.net.IServiceLib;
import com.nmssdmf.commonlib.net.http.OkHttpClientProvider;
import com.nmssdmf.commonlib.net.retrofit.HttpObserver;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.ImageUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.goodsmanagemodel.CommodityShow;
import com.zhixingjia.bean.goodsmanagemodel.DescibePostBean;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.goodsmanagemodule.activity.AddProductDescribeActivity;
import com.zhixingjia.goodsmanagemodule.activity.LadderPriceSettingActivity;
import com.zhixingjia.goodsmanagemodule.activity.PriceSettingActivity;
import com.zhixingjia.goodsmanagemodule.activity.SelectStandardActivity;
import com.zhixingjia.goodsmanagemodule.bean.PriceSet;
import com.zhixingjia.goodsmanagemodule.bean.ProductDescribe;
import com.zhixingjia.goodsmanagemodule.bean.SepcPriceStockUnit;
import com.zhixingjia.goodsmanagemodule.callback.AddOrEditProductCB;
import com.zhixingjia.service.GoodsManageService;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
* @description 发布商品
* @author chenbin
* @date 2018/11/21 11:23
* @version v3.2.0
*/
public class AddOrEditProductVM extends BaseVM {
    private AddOrEditProductCB cb;
    private String commodity_id;            //商品ID
    private int status;                     //状态

    public CommodityInitialize commodityInitialize;
    public String categoryId;                                               //选择的类目id
    public int categoryIndex;                                               //选择的类目位置
    public String brandId;                                                     //品牌ID
    public Map<String,List<String>> selectedSepc = new LinkedHashMap<>();        //已选择的规格
    public PriceSet priceSet;                                               //价格库存
    public SepcPriceStockUnit sepcPriceStockUnit;                      //已选规格的价格库存
    public List<ProductDescribe> productDescribes = new ArrayList<>(); //图文描述
    public String[] imageIds;                                           //商品封面图片

    public List<String> categoryNames = new ArrayList<>();
    public List<String> brandNames = new ArrayList<>();

    public ObservableField<String> categoryName = new ObservableField<>();  //类目名称
    public ObservableField<String> brandName = new ObservableField<>();     //品牌名称
    public ObservableField<String> sepcName = new ObservableField<>();      //选择的规格
    public ObservableField<String> skuName = new ObservableField<>();       //设置价格库存
    public ObservableField<String> picContent = new ObservableField<>();       //设置图文详情
    public ObservableField<String> title = new ObservableField<>();       //标题
    public ObservableField<String> count = new ObservableField<>("0/50");       //标题
    public ObservableField<String> code = new ObservableField<>();          //商品货号
    public ObservableBoolean isSpec = new ObservableBoolean();          //是否有规格
    public ObservableField<String> cost_freight = new ObservableField<>(); //配送费

    public static final int SELECT_SEPC = 10355;
    public static final int SELECT_STOCK = 10356;
    public static final int SELECT_STOCK_SEPC = 10357;                      //有规格的价格库存
    public static final int PIC_CONTENT_DETAIL = 10358;                      //图文详情

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddOrEditProductVM(AddOrEditProductCB callBack) {
        super(callBack);
        this.cb = callBack;
        Bundle bundle = cb.getIntentData();
        if (bundle != null) {
            commodity_id = bundle.getString(IntentConfig.COMMODITY_ID);
        }
        initalize();
    }

    public void onSelectBrandClick(View view) {
        cb.showBrandWindow();
    }

    public void onSelectCategoryClick(View view) {
        cb.showCategoryWindow();
    }

    public void selectSpecificationClick(View view) {
        if (TextUtils.isEmpty(categoryId)) {
            baseCallBck.showToast("请选择类目");
            return;
        }
        Bundle bundle = new Bundle();
        List<CommodityInitialize.CateInfo.SepcInfo> sepcInfo = commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info();
        bundle.putSerializable(IntentConfig.SEPC_INFO, (Serializable) sepcInfo);
        bundle.putSerializable(IntentConfig.SEPC_INFO_SELECTED, (Serializable) selectedSepc);
        cb.doIntentForResult(SelectStandardActivity.class, bundle, SELECT_SEPC);
    }

    public void productDescriptionClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.PRODUCT_DESCRIBES, (Serializable) productDescribes);
        cb.doIntentForResult(AddProductDescribeActivity.class, bundle, PIC_CONTENT_DETAIL);
    }

    /**
     * 设置价格库存
     * @param view
     */
    public void selectSkuClick(View view) {
//        if ((selectedSepc == null || selectedSepc.size() == 0)
//                && (commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info() != null && commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info().size() > 0)) {
//            //跳转没有规格的商品规格页面
//
//            return;
//        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.STOCK_PRICE, priceSet);
        bundle.putSerializable(IntentConfig.UNIT, (Serializable) commodityInitialize.getUnit_info());
        if (selectedSepc != null && selectedSepc.size() > 0) {//若选择了商品规格,跳转商品规格价格库存页面
            bundle.putSerializable(IntentConfig.SEPC_INFO_SELECTED, (Serializable) selectedSepc);
            bundle.putSerializable(IntentConfig.SEPC_INFO, (Serializable) commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info());
            if (!TextUtils.isEmpty(categoryId)) {
                bundle.putSerializable(IntentConfig.STOCK_PRICE_SPEC, sepcPriceStockUnit);
            }
            cb.doIntentForResult(LadderPriceSettingActivity.class, bundle, SELECT_STOCK_SEPC);
        } else {
            cb.doIntentForResult(PriceSettingActivity.class, bundle, SELECT_STOCK);
        }

    }

    /**
     * 初始化数据
     */
    public void initalize() {
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_INITIALIZE).commodityInitialize(),
                new ServiceCallback<BaseData<CommodityInitialize>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<CommodityInitialize> commodityInitializeBaseData) {
                commodityInitialize = commodityInitializeBaseData.getData();
                categoryNames.clear();
                brandNames.clear();
                for (CommodityInitialize.CateInfo cateInfo : commodityInitializeBaseData.getData().getCateinfo()) {
                    categoryNames.add(cateInfo.getCate_name());
                }
                for (HouseBean.BrandsBean brandsBean : commodityInitializeBaseData.getData().getBrand_info()) {
                    brandNames.add(brandsBean.getTitle());
                }
                cb.initData();
                if (!TextUtils.isEmpty(commodity_id)) {
                    getCommodityInfo();
                }
            }

            @Override
            public void onDefeated(BaseData<CommodityInitialize> commodityInitializeBaseData) {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == SELECT_SEPC) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                selectedSepc = (Map<String, List<String>>) bundle.getSerializable(IntentConfig.SEPC_INFO_SELECTED);
                if (selectedSepc != null && selectedSepc.size() > 0) {
                    sepcName.set("已设置");
                    skuName.set(null);//重新填写价格库存
                } else {
                    sepcName.set(null);
                    if (sepcPriceStockUnit != null) {
                        sepcPriceStockUnit = null;
                        skuName.set(null);//重新填写价格库存
                    }
                }
            }
        } else if (requestCode == SELECT_STOCK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                priceSet = (PriceSet) bundle.getSerializable(IntentConfig.STOCK_PRICE);
                sepcPriceStockUnit = null;
                skuName.set("已设置");
            }
        } else if (requestCode == SELECT_STOCK_SEPC) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                sepcPriceStockUnit = (SepcPriceStockUnit) bundle.getSerializable(IntentConfig.STOCK_PRICE_SPEC);
                priceSet = null;
                skuName.set("已设置");
            }
        } else if (requestCode == PIC_CONTENT_DETAIL) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                productDescribes = (List<ProductDescribe>) bundle.getSerializable(IntentConfig.PRODUCT_DESCRIBES);
                picContent.set("已设置");
            }
        }
    }

    public void afterTextChanged(Editable s) {
        String str = s + "";
        if (str.length() > 50) {
            str = str.substring(0, 50);
            title.set(str);
            cb.showToast("输入的文字超过上限");
        }
        count.set(str.length() + "/50");
    }

    /**
     * 数据是否有效
     */
    private boolean isDataValidate() {
        if (TextUtils.isEmpty(categoryName.get())) {
            baseCallBck.showToast("请选择商品类目");
            return false;
        }
        if (TextUtils.isEmpty(title.get())) {
            baseCallBck.showToast("请填写商品标题");
            return false;
        }
        if (TextUtils.isEmpty(title.get())) {
            baseCallBck.showToast("请填写商品标题");
            return false;
        }
        int imgsize = cb.getImgSize();
        if (imgsize == 0) {
            baseCallBck.showToast("请上传商品图片");
            return false;
        }
        if (TextUtils.isEmpty(code.get())) {
            baseCallBck.showToast("请填写货号");
            return false;
        }
        if (selectedSepc != null && selectedSepc.size() > 0) {
            if (TextUtils.isEmpty(sepcName.get())) {
                baseCallBck.showToast("请填写商品规格");
                return false;
            }
        }
        if (TextUtils.isEmpty(skuName.get())) {
            baseCallBck.showToast("请填写价格库存");
            return false;
        }
        if (TextUtils.isEmpty(picContent.get())) {
            baseCallBck.showToast("请填写商品描述");
            return false;
        }
        return true;
    }

    /**
     * 上传商品描述图片
     */
    public void upLoadProductImg() {
        int productDescribeIndex = 0;
        boolean isFull = true;
        for (ProductDescribe productDescribe : productDescribes) {
            int imgIndex = 0;
            if (productDescribe.getImgs() != null) {
                for (UploadImage uploadImage : productDescribe.getImgs()) {
                    if (TextUtils.isEmpty(uploadImage.getImage_id())) {

                        File file = ImageUtil.getCompressFile( uploadImage.getUrl());
                        // create your getFile object here
                        if (file == null) {
                            ToastUtil.getInstance().showToast("商品描述上传图片失败");
                            return;
                        }
                        isFull = false;
                        doUploadImage(file, productDescribeIndex, imgIndex);
                    }
                    imgIndex++;
                }
            }
            productDescribeIndex++;
        }
        if (isFull) {
            commit(status);
        }
    }

    /**
     * upload image to the server
     *
     * @param file
     */
    private void doUploadImage(final File file, final int productDescribeIndex, final int imgIndex) {
        RequestBody request_body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), request_body);
        IServiceLib iService = new Retrofit.Builder()
                .baseUrl(BaseConfig.IMAGEUPLOADIP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClient()).build().create(IServiceLib.class);
        iService.uploadImage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new HttpObserver<Upload>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtil.getInstance().showToast("上传图片失败");
                        cb.dismissLoaddingDialog();
                    }

                    @Override
                    public void onNext(Upload uploadImage) {
                        if (StringConfig.OK.equals(uploadImage.getStatus_code())) {
                            productDescribes.get(productDescribeIndex).getImgs().get(imgIndex).setImage_id(uploadImage.getData().getImage_id());
                            boolean isFull = true;
                            for (ProductDescribe productDescribe : productDescribes) {
                                if (productDescribe.getImgs() != null) {
                                    for (UploadImage image : productDescribe.getImgs()) {
                                        if (TextUtils.isEmpty(image.getImage_id())) {
                                            isFull = false;
                                            break;
                                        }
                                    }
                                }
                                if (!isFull) {
                                    break;
                                }
                            }
                            if (isFull) {
                                //提交
                                commit(status);
                            }
                        } else {
                            cb.dismissLoaddingDialog();
                            ToastUtil.getInstance().showToast("上传图片失败");
                        }
                    }
                });
    }

    /**
     * 提交商品
     */
    private void commit(final int status) {
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(commodity_id)) {
            params.put("commodity_id", commodity_id);
        }
        params.put("cate_id", categoryId);
        params.put("commodity_name", title.get());
        params.put("imgs", new Gson().toJson(imageIds));
        params.put("bn", code.get());
        if (sepcPriceStockUnit != null && sepcPriceStockUnit.getSepcPriceStockSets().size() > 0) { //是否有规格的价格库存
            params.put("unit", sepcPriceStockUnit.getUnit());
            params.put("sku", new Gson().toJson(sepcPriceStockUnit.getSepcPriceStockSets()));
        } else {
            params.put("unit", priceSet.getUnit());
            params.put("price", priceSet.getPrice());
            params.put("stock", priceSet.getStock());
        }
        List<DescibePostBean> descibeContents = new ArrayList<>();
        for (ProductDescribe descibeContent:productDescribes) {
            DescibePostBean descibe = new DescibePostBean();
            if (descibeContent.getImgs() != null && descibeContent.getImgs().size() > 0) {
                List<String> ids = new ArrayList<>();
                for (UploadImage uploadImage : descibeContent.getImgs()) {
                    ids.add(uploadImage.getImage_id());
                }
                descibe.setImgs(ids);
            }
            descibe.setNote(descibeContent.getNote());
            descibeContents.add(descibe);
        }
        params.put("contents", new Gson().toJson(descibeContents));
        params.put("status", String.valueOf(status));
        if (TextUtils.isEmpty(brandName.get())) {
            params.put("brand", "0");
        } else {
            params.put("brand", brandId);
        }
        if (!TextUtils.isEmpty(cost_freight.get())) {
            params.put("cost_freight", cost_freight.get());
        }
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_OPERATION).commodityOperation(params),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                //提交成功，刷新列表
                if (TextUtils.isEmpty(categoryId)) {
                    if (status == 0) {
                        RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_UNAVAILABLE, null);
                    } else {
                        RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_AVAILABLE, null);
                    }
                } else {
                    RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_UNAVAILABLE, null);
                    RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_AVAILABLE, null);
                }
                cb.showToast("保存成功");
                cb.finishActivity();
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    /**
     * 放入仓库
     * @param view
     */
    public void addToStockClick(View view) {
        if (!isDataValidate()) {
            cb.dismissLoaddingDialog();
            return;
        }
        status = 0;
        //上传图片
        cb.upLoadProductImg();
    }

    /**
     * 立即上架
     * @param view
     */
    public void pullOnSaleClick(View view) {
        if (!isDataValidate()) {
            cb.dismissLoaddingDialog();
            return;
        }
        status = 1;
        //上传图片
        cb.upLoadProductImg();
    }

    /**
     * 获取商品编辑详情
     */
    public void getCommodityInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_SHOW).commodityShow(commodity_id),
                new ServiceCallback<BaseData<CommodityShow>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<CommodityShow> base) {
                //初始化数据
                CommodityShow commodityShow = base.getData();
                categoryId = commodityShow.getCate_id();
                int i = 0;
                for (CommodityInitialize.CateInfo cateInfo : commodityInitialize.getCateinfo()) {
                    if (categoryId.equals(cateInfo.getCate_id())) {
                        categoryIndex = i;
                        categoryName.set(cateInfo.getCate_name());
                        break;
                    }
                    i++;
                }
                title.set(commodityShow.getCommodity_name());
                cb.initImageSelectView(commodityShow.getImgs());
                code.set(commodityShow.getBn());
                isSpec.set(commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info() != null
                        && commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info().size() > 0);
                //初始化库存
                if (commodityShow.getSku() != null && commodityShow.getSku().size() > 0) {
                    sepcPriceStockUnit = new SepcPriceStockUnit();
                    sepcPriceStockUnit.setUnit(commodityShow.getUnit());
                    sepcPriceStockUnit.setSepcPriceStockSets(commodityShow.getSku());
                } else {
                    priceSet = new PriceSet();
                    priceSet.setUnit(commodityShow.getUnit());
                    priceSet.setStock(commodityShow.getStock());
                    priceSet.setPrice(commodityShow.getPrice());
                }
                skuName.set("已设置");
                //初始化图文描述
                for (CommodityShow.DescibeContent descibeContent : commodityShow.getContent()) {
                    ProductDescribe productDescribe = new ProductDescribe();
                    productDescribe.setNote(descibeContent.getNote());
                    List<UploadImage> uploadImages = new ArrayList<>();
                    if (descibeContent.getImgs() != null) {
                        for (CommodityShow.ImageBean imageBean : descibeContent.getImgs()) {
                            UploadImage uploadImage = new UploadImage();
                            uploadImage.setImage_id(imageBean.getImage_id());
                            uploadImage.setUrl(imageBean.getImgs_url());
                            uploadImages.add(uploadImage);
                        }
                        productDescribe.setImgs(uploadImages);
                    }
                    productDescribes.add(productDescribe);
                }
                picContent.set("已设置");
                //初始化规格
                if (commodityShow.getSepc_val() != null && commodityShow.getSepc_val().size() > 0) {
                    for (CommodityShow.SepcVal sepcVal : commodityShow.getSepc_val()) {
                        selectedSepc.put(sepcVal.getNorms_id(), sepcVal.getChild());
                    }
                    sepcName.set("已设置");
                }
                brandId = commodityShow.getBrand();
                for (HouseBean.BrandsBean brand : commodityInitialize.getBrand_info()) {
                    if (brandId.equals(brand.getId())) {
                        brandName.set(brand.getTitle());
                        break;
                    }
                }
                cost_freight.set(commodityShow.getCost_freight());
            }

            @Override
            public void onDefeated(BaseData<CommodityShow> base) {

            }
        });
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }
}

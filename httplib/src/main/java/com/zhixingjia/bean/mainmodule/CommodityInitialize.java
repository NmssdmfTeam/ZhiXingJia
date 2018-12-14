package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Create by chenbin on 2018/12/14
 * <p>
 * <p>
 */
public class CommodityInitialize extends BaseObservable {

    private List<HouseBean.BrandsBean> brand_info;      //品牌信息
    private List<String> unit_info;                     //单位
    private List<CateInfo> cateinfo;                   //商品类别数据

    @Bindable
    public List<HouseBean.BrandsBean> getBrand_info() {
        return brand_info;
    }

    public void setBrand_info(List<HouseBean.BrandsBean> brand_info) {
        this.brand_info = brand_info;
        notifyPropertyChanged(BR.brand_info);
    }

    @Bindable
    public List<String> getUnit_info() {
        return unit_info;
    }

    public void setUnit_info(List<String> unit_info) {
        this.unit_info = unit_info;
        notifyPropertyChanged(BR.unit_info);
    }

    @Bindable
    public List<CateInfo> getCateinfo() {
        return cateinfo;
    }

    public void setCateinfo(List<CateInfo> cateinfo) {
        this.cateinfo = cateinfo;
        notifyPropertyChanged(BR.cateinfo);
    }

    public static class CateInfo extends BaseObservable {      //商品类别数据
        private String cate_name;           //类别名称
        private String cate_id;             //类别ID
        private List<SepcInfo> sepc_info;   //规格数据，如果为空表示这个分类没有绑定规格，此时页面上规格选择这一行去掉不要

        @Bindable
        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
            notifyPropertyChanged(BR.cate_name);
        }

        @Bindable
        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
            notifyPropertyChanged(BR.cate_id);
        }

        @Bindable
        public List<SepcInfo> getSepc_info() {
            return sepc_info;
        }

        public void setSepc_info(List<SepcInfo> sepc_info) {
            this.sepc_info = sepc_info;
            notifyPropertyChanged(BR.sepc_info);
        }

        public static class SepcInfo extends BaseObservable {
            private String norms_id;        //规格类别ID
            private String type_name;       //规格类别名称
            private List<String> type_val;  //类别值数组

            @Bindable
            public String getNorms_id() {
                return norms_id;
            }

            public void setNorms_id(String norms_id) {
                this.norms_id = norms_id;
                notifyPropertyChanged(BR.norms_id);
            }

            @Bindable
            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
                notifyPropertyChanged(BR.type_name);
            }

            @Bindable
            public List<String> getType_val() {
                return type_val;
            }

            public void setType_val(List<String> type_val) {
                this.type_val = type_val;
                notifyPropertyChanged(BR.type_val);
            }
        }
    }
}

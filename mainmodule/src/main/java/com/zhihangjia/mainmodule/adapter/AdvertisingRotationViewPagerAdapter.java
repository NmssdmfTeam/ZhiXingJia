package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.rollviewpager.RollPagerView;
import com.nmssdmf.commonlib.rollviewpager.adapter.LoopPagerAdapter;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
 * Create by chenbin on 2017/11/22
 * <p>
 * 循环滑动adapter
 */
public class AdvertisingRotationViewPagerAdapter extends LoopPagerAdapter {

    private List<Banner.CommomBanner> advertisingRotations;

    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    // SetScaleType(ImageView.ScaleType.CENTER_CROP);
    // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

    public AdvertisingRotationViewPagerAdapter(List<Banner.CommomBanner> advertisingRotations, RollPagerView viewPager) {
        super(viewPager);
        this.advertisingRotations = advertisingRotations;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
        relativeLayout.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(container.getContext());
//        RequestOptions requestOptions = new RequestOptions()
//                .centerCrop()
//                .priority(Priority.HIGH);
//        Glide.with(imageView.getContext())
//                .load(advertisingRotations.get(position).getImg_url()).apply(requestOptions)
//                .into(imageView);
        GlideUtil.load(imageView,advertisingRotations.get(position).getImg_url());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(advertisingRotations.get(position).getLink_url()))
                    return;
                if ("1".equals(advertisingRotations.get(position).getJumps())) {
                    Intent intent = new Intent(container.getContext(), WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.LINK, advertisingRotations.get(position).getLink_url());
                    intent.putExtras(bundle);
                    container.getContext().startActivity(intent);
                } else if ("2".equals(advertisingRotations.get(position).getJumps())) {
                    Intent intent = new Intent(container.getContext(), MerchantMainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.ID, advertisingRotations.get(position).getLink_url());
                    intent.putExtras(bundle);
                } else if ("3".equals(advertisingRotations.get(position).getJumps())) {
                    Intent intent = new Intent(container.getContext(), MerchandiseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.COMMODITY_ID, advertisingRotations.get(position).getLink_url());
                    intent.putExtras(bundle);
                }
            }
        });
        relativeLayout.addView(imageView);
        return relativeLayout;
    }

    public List<Banner.CommomBanner> getAdvertisingRotations() {
        return advertisingRotations;
    }

    @Override
    public int getRealCount() {
        return advertisingRotations.size();
    }
}

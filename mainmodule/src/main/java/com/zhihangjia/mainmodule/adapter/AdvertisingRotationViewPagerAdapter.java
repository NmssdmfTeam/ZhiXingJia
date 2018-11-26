package com.zhihangjia.mainmodule.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.rollviewpager.RollPagerView;
import com.nmssdmf.commonlib.rollviewpager.adapter.LoopPagerAdapter;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
 * Create by chenbin on 2017/11/22
 * <p>
 * 循环滑动adapter
 */
public class AdvertisingRotationViewPagerAdapter extends LoopPagerAdapter {

    private List<IndexBean.BannersBean> advertisingRotations;

    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    // SetScaleType(ImageView.ScaleType.CENTER_CROP);
    // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

    public AdvertisingRotationViewPagerAdapter(List<IndexBean.BannersBean> advertisingRotations, RollPagerView viewPager) {
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
                //打点

//                Intent intent = new Intent(container.getContext(), WebViewActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(Config.URL, advertisingRotations.get(position).getLink());
//                intent.putExtras(bundle);
//                container.getContext().startActivity(intent);
            }
        });
        relativeLayout.addView(imageView);
        return relativeLayout;
    }

    public List<IndexBean.BannersBean> getAdvertisingRotations() {
        return advertisingRotations;
    }

    @Override
    public int getRealCount() {
        return advertisingRotations.size();
    }
}

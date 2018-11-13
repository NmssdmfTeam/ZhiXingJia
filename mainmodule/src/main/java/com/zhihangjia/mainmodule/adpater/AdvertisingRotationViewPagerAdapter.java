package com.zhihangjia.mainmodule.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.nmssdmf.commonlib.rollviewpager.RollPagerView;
import com.nmssdmf.commonlib.rollviewpager.adapter.LoopPagerAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.IndexAdvertise;

import java.util.List;

/**
 * Create by chenbin on 2017/11/22
 * <p>
 * 循环滑动adapter
 */
public class AdvertisingRotationViewPagerAdapter extends LoopPagerAdapter {
    public static final int MAIN_PAGER = 1, PART_PAGER = 2, CAPACITY_PAGER =3;
    private int type = MAIN_PAGER;

    private List<IndexAdvertise> advertisingRotations;

    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    // SetScaleType(ImageView.ScaleType.CENTER_CROP);
    // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

    public AdvertisingRotationViewPagerAdapter(int type, List<IndexAdvertise> advertisingRotations, RollPagerView viewPager) {
        super(viewPager);
        this.type = type;
        this.advertisingRotations = advertisingRotations;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
        relativeLayout.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(container.getContext());
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(R.drawable.no_pic).apply(requestOptions)
                .into(imageView);
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

    public List<IndexAdvertise> getAdvertisingRotations() {
        return advertisingRotations;
    }

    @Override
    public int getRealCount() {
        return advertisingRotations.size();
    }
}

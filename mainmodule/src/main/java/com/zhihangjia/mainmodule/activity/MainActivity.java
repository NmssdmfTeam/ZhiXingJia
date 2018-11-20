package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityMainBinding;
import com.zhihangjia.mainmodule.fragment.MainFragment;
import com.zhihangjia.mainmodule.fragment.MaterialsMarketFragment;
import com.zhihangjia.mainmodule.fragment.MessageFragment;
import com.zhihangjia.mainmodule.fragment.MineCustomerFragment;
import com.zhihangjia.mainmodule.fragment.MineProviderFragment;
import com.zhihangjia.mainmodule.fragment.ShopCarFragment;
import com.zhihangjia.mainmodule.viewmodel.MainVM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 知行家首页
 * @date 2018/11/13 15:00
 */
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private String TAG = MainActivity.class.getSimpleName();
    private MainVM vm;
    private ActivityMainBinding binding;

    private int current_index = 0;

    /**
     * 三个值分别为总首页fragment，配件首页fragment和成品首页fragment
     */

    /**
     * tab layout加载的fragment class，并使用class name当作tab的tag，所以要求fragment加载的class是不同的class，否则需要创建其他tag
     */
    private Class[] fragment_clazz = new Class[]{MainFragment.class, MaterialsMarketFragment.class, MessageFragment.class, ShopCarFragment.class, MineProviderFragment.class};
    private Integer[] titles_texts = {R.string.main, R.string.marketBuilding, R.string.message, R.string.shopCar, R.string.mine};
    private int[] icon_ons = {R.drawable.icon_home_selected, R.drawable.icon_materials_selected, R.drawable.message_center_selected, R.drawable.icon_home_selected, R.drawable.icon_mine_selected};
    private int[] icon_offs = {R.drawable.icon_home_unselected, R.drawable.icon_home_unselected, R.drawable.message_center_unselected, R.drawable.icon_home_unselected, R.drawable.icon_home_unselected};


    private List<ImageView> ivs = new ArrayList<>();
    private List<TextView> tvs = new ArrayList<>();
    private List<TextView> points = new ArrayList<>();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MainVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityMainBinding) baseBinding;
        WindowUtil.setWindowStatusBarTransParent(this);
        binding.mfth.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        binding.mfth.getTabWidget().setDividerDrawable(null); // 去掉分割线
        // 初始化fragment tab host
        for (int i = 0; i < fragment_clazz.length; i++) {
            // Tab按钮添加文字和图片
            TabHost.TabSpec spec = binding.mfth.newTabSpec(fragment_clazz[i].getName()).setIndicator(getTabView(i));
            JLog.d(TAG, "User fragment class:" + fragment_clazz[i].getSimpleName());

            // 添加Fragment
            binding.mfth.addTab(spec, fragment_clazz[i], new Bundle());
        }
        binding.mfth.setCurrentTab(current_index);
        setSelectedTab(current_index, true);// 设置初始选中状态
        binding.mfth.setOnTabChangedListener(this);

    }

    @Override
    public void onTabChanged(String tabId) {
        for (int i = 0; i < fragment_clazz.length; i++) {
            if (tabId.equals(fragment_clazz[i].getName())) {
                if (current_index != i) {
                    setSelectedTab(current_index, false);
                    setSelectedTab(i, true);
                    current_index = i;
                }
                break;
            }

        }
    }

    /**
     * 设置tab选中和没有选中的状态
     *
     * @param position
     * @param selected
     */
    private void setSelectedTab(final int position, boolean selected) {
        ivs.get(position).setImageResource(selected ? icon_ons[position] : icon_offs[position]);
        tvs.get(position).setTextColor(selected ? getResources().getColor(R.color.text_black) : getResources().getColor(R.color.text_gray));
    }

    /**
     * 设置tab的内容
     *
     * @param position tab的位置
     * @return tab
     */
    private View getTabView(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_tab, null);

        ImageView iv = view.findViewById(R.id.iv);
        iv.setImageResource(icon_offs[position]);

        TextView tv = view.findViewById(R.id.tv);

        tv.setText(titles_texts[position]);
        tv.setTextColor(getResources().getColor(R.color.text_gray));

        final TextView point = view.findViewById(R.id.tv_red_point);

        ivs.add(iv);
        tvs.add(tv);
        points.add(point);

        /**
         * 因为view被设置为tab widget 点击事件被拦截，所以需要设置onTouch事件
         */
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                }
                return false;
            }
        });

        return view;
    }
}

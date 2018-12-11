package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.PermissionCompat;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.behavior.BottomBehavior;
import com.zhihangjia.mainmodule.behavior.CommonBehavior;
import com.zhihangjia.mainmodule.callback.MainCB;
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
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, MainCB {
    private String TAG = MainActivity.class.getSimpleName();
    private MainVM vm;
    private ActivityMainBinding binding;
    private CommonBehavior bottomBehavior;

    private int current_index = 0;

    /**
     * 三个值分别为总首页fragment，配件首页fragment和成品首页fragment
     */

    /**
     * tab layout加载的fragment class，并使用class name当作tab的tag，所以要求fragment加载的class是不同的class，否则需要创建其他tag
     */
    private Class[] mine_class = new Class[]{MineCustomerFragment.class, MineProviderFragment.class};
    private Class[] fragment_clazz = new Class[]{MainFragment.class, MaterialsMarketFragment.class, MessageFragment.class, ShopCarFragment.class, MineProviderFragment.class};
    private Integer[] titles_texts = {R.string.main, R.string.marketBuilding, R.string.message, R.string.shopCar, R.string.mine};
    private int[] icon_ons = {R.drawable.icon_home_selected, R.drawable.icon_materials_selected, R.drawable.message_center_selected, R.drawable.icon_shopcar_selected, R.drawable.icon_mine_selected};
    private int[] icon_offs = {R.drawable.icon_home_unselected, R.drawable.index_market_unselected, R.drawable.message_center_unselected, R.drawable.index_shopcar, R.drawable.icon_my_unselected};


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
        initTabsView();
        bottomBehavior = BottomBehavior.from(binding.llBottomNavigation);
        PermissionCompat.getInstance().checkLocationPermission(this);
        vm.getShopCarAllNum();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int index = bundle.getInt(IntentConfig.POSITION);
            current_index = index;
            switchFragment(current_index);
            setSelectedTab(current_index, true);
            setSelectedTab(0, false);
        }
    }

    private void initTabsView() {
        // 初始化fragment tab host
        if (StringConfig.PROVIDER.equals(vm.identify)) { //是否买家卖家切换fragment
            fragment_clazz[fragment_clazz.length - 1] = mine_class[1];
        } else {
            fragment_clazz[fragment_clazz.length - 1] = mine_class[0];
        }
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
                    if (i == 3) {
                        bottomBehavior.isEnableScroll(false);
                    } else {
                        bottomBehavior.isEnableScroll(true);
                    }
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
        if (position == 3) {
            point.setVisibility(View.VISIBLE);
        }

        /**
         * 因为view被设置为tab widget 点击事件被拦截，所以需要设置onTouch事件
         */
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

            }
            return false;
        });

        return view;
    }

    public void changeIdentify(String identify) {
        vm.identify = identify;
        PreferenceUtil.setStringValue(PrefrenceConfig.IDENTIFY, identify);
        binding.mfth.clearAllTabs();
        ivs.clear();
        tvs.clear();
        binding.mfth.setOnTabChangedListener(null);
        //初始化fragment及底部导航栏
        initTabsView();
        if (StringConfig.PROVIDER.equals(identify))
            RxBus.getInstance().send(RxEvent.PersonInfoEvent.IDENTIFY_CHANGE, null);
    }

    public void setCurrentTabsIndex(int index) {
        binding.mfth.setCurrentTab(index);
    }

    @Override
    public void switchFragment(int index) {
        bottomBehavior.show();
        setCurrentTabsIndex(index);
    }

    @Override
    public void initTab() {
        changeIdentify(StringConfig.BUYER);
    }

    @Override
    public void setShopCarNumber(String num) {
        points.get(3).setText(num);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        moveTaskToBack(false);
    }
}

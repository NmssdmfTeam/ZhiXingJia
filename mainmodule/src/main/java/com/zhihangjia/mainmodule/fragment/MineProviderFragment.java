package com.zhihangjia.mainmodule.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.callback.MineProviderFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMineProviderBinding;
import com.zhihangjia.mainmodule.viewmodel.MineProviderFragmentVM;
import com.zhihangjia.mainmodule.activity.CaptureActivity;
import com.zhixingjia.bean.mainmodule.MessageUnread;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
* @description 知行家首页-- 建材家居fragment
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MineProviderFragment extends BaseFragment implements MineProviderFragmentCB {
    private final String TAG = MineProviderFragment.class.getSimpleName();
    private FragmentMineProviderBinding binding;
    private MineProviderFragmentVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new MineProviderFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_provider;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMineProviderBinding) baseBinding;
        binding.msfl.setSwipeableChildren(R.id.sv_customer_my);
        vm.getUserInfo();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.setData(null);
        vm.getMessageUnread();
    }

    private void setListener() {
        binding.tvChanggeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.changeIdentify(StringConfig.BUYER);
                }
            }
        });
        binding.msfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vm.getUserInfo();
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void bindVM() {
        binding.setVm(vm);
    }

    @Override
    public void endFresh() {
        binding.msfl.setRefreshing(false);
    }

    @Override
    public void initView() {

    }

    @Override
    public void showNotice(MessageUnread messageUnread) {
        if (TextUtils.isEmpty(messageUnread.getAll_message()) || "0".equals(messageUnread.getAll_message())) {
            binding.ivMessageNotice.setVisibility(View.GONE);
        } else {
            binding.ivMessageNotice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case MineProviderFragmentVM.REQUEST_CODE_SCAN:// 二维码
//                // 扫描二维码回传
//                if (resultCode == RESULT_OK) {
//                    if (data != null) {
//                        //获取扫描结果
//                        Bundle bundle = data.getExtras();
//                        String result = bundle.getString(CaptureActivity.EXTRA_STRING);
//                        JLog.d(TAG, "result = " + result);
////                        "types=zs_coupon&member_id=14&code=306339&code_id=3"
//                        if (!CommonUtils.isEmpty(result)) {
//                            String[] params = result.split("&");
//                            if (params.length > 0 && "zs_coupon".equals(params[0].split("=")[1])) {
//                                Map<String, String> map = new HashMap<>();
//                                map.put(params[1].split("=")[0], params[1].split("=")[1]);
//                                map.put(params[2].split("=")[0], params[2].split("=")[1]);
//                                map.put(params[3].split("=")[0], params[3].split("=")[1]);
//                                vm.getCouponWriteOff(map);
//                            }
//
//                        }
//                    }
//                }
//                break;

            default:
                break;
        }
    }

}

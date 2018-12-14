package com.zhixingjia.goodsmanagemodule.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.goodsmanagemodule.adapter.GoodManageAdapter;
import com.zhixingjia.goodsmanagemodule.callback.GoodManageFragmentCB;
import com.zhixingjia.goodsmanagemodule.viewmodel.GoodManageFragmentVM;

import java.util.List;


public class GoodManageFragment extends BaseRecyclerViewFragment implements GoodManageFragmentCB,GoodManageAdapter.GoodsManageOption {
    private final String TAG = GoodManageFragment.class.getSimpleName();

    private GoodManageAdapter adapter;
    private GoodManageFragmentVM vm;
    private String keyword;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        Bundle bundle = getArguments();
        int type = 0;
        if (bundle != null) {
            type = bundle.getInt(IntentConfig.TYPE);
        }
        vm = new GoodManageFragmentVM(this,type);
        vm.keyword = keyword;
        return vm;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        super.initAll(view, savedInstanceState);
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new GoodManageAdapter(list,this, vm.type);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void setKeyword(String keyword) {
        if (vm != null) {
            vm.keyword = keyword;
            binding.crv.setRefreshing(true);
            vm.initData(true);
        } else {
            this.keyword = keyword;
        }
    }

    @Override
    public void onTvPullOffClick(View view, final Commodity commodity, final int position) {
        String message = vm.type == 1?"是否下架":"是否上架";
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage(message).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                vm.pullOffGood(commodity, position);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        int width = DensityUtil.dpToPx(getActivity(), 340);
        alertDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onTvDeleteClick(View view, final Commodity commodity, final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("是否删除").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                vm.deleteGood(commodity, position);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        int width = DensityUtil.dpToPx(getActivity(), 340);
        alertDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public String getLastPageId() {
        if (adapter.getData().size() > 0) {
            String id = adapter.getData().get(adapter.getData().size() - 1).getCommodity_id();
            return id;
        }
        return "0";
    }

    @Override
    public void removeItem(int position) {
        adapter.remove(position);
    }
}

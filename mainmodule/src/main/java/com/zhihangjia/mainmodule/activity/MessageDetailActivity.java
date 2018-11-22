package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.CommentListContentAdapter;
import com.zhihangjia.mainmodule.callback.MessageDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityMessageDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageDetailHeadBinding;
import com.zhihangjia.mainmodule.viewmodel.MessageDetailVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 信息详情activity
* @author chenbin
* @date 2018/11/20 11:10
* @version v3.2.0
*/
public class MessageDetailActivity extends BaseTitleActivity implements MessageDetailCB {
    private final String TAG = MessageDetailActivity.class.getSimpleName();
    private MessageDetailVM vm;
    private ActivityMessageDetailBinding binding;
    private CommentListContentAdapter adapter;
    private ItemMessageDetailHeadBinding itemMessageDetailHeadBinding;
    @Override
    public String setTitle() {
        return "1/10";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMessageDetailBinding) baseViewBinding;
        List<Base> list = new ArrayList<>();
        for (int i =0; i < 10; i++) {
            list.add(new Base());
        }
        adapter = new CommentListContentAdapter(list);
        binding.crv.setAdapter(adapter);
        itemMessageDetailHeadBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_message_detail_head,null,false);
        adapter.addHeaderView(itemMessageDetailHeadBinding.getRoot());
        baseTitleBinding.tTitle.inflateMenu(R.menu.share);
        setListener();

        vm.getMessageDetail();
    }

    private void setListener() {
        baseTitleBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.svPage.getVisibility() == View.GONE) {
                    binding.vBlackBackgroud.setVisibility(View.VISIBLE);
                    binding.svPage.setVisibility(View.VISIBLE);
                } else {
                    binding.vBlackBackgroud.setVisibility(View.GONE);
                    binding.svPage.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_detail;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MessageDetailVM(this);
        return vm;
    }

    @Override
    public void initView() {
        setTitle(vm.getDetail().getComment_pages());
        itemMessageDetailHeadBinding.setData(vm.getDetail());
    }

}

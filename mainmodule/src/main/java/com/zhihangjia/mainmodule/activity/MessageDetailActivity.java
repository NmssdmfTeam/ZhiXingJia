package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.CommentListContentAdapter;
import com.zhihangjia.mainmodule.callback.MessageDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityMessageDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageDetailHeadBinding;
import com.zhihangjia.mainmodule.viewmodel.MessageDetailVM;
import com.zhixingjia.bean.mainmodule.MessageComment;
import com.zhixingjia.bean.mainmodule.MessageDetail;

import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 信息详情activity
 * @date 2018/11/20 11:10
 */
public class MessageDetailActivity extends BaseTitleActivity implements MessageDetailCB {
    private final String TAG = MessageDetailActivity.class.getSimpleName();
    private MessageDetailVM vm;
    private ActivityMessageDetailBinding binding;
    private CommentListContentAdapter adapter;
    private ItemMessageDetailHeadBinding itemMessageDetailHeadBinding;

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMessageDetailBinding) baseViewBinding;

        adapter = new CommentListContentAdapter(vm.getList());
        binding.crv.setAdapter(adapter);
        itemMessageDetailHeadBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_message_detail_head, null, false);
        adapter.addHeaderView(itemMessageDetailHeadBinding.getRoot());
        baseTitleBinding.tTitle.inflateMenu(R.menu.share);
        setListener();

        vm.getMessageDetail();
        vm.getCommentList(true);
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

        itemMessageDetailHeadBinding.cbLookBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemMessageDetailHeadBinding.cbLookBuilder.isChecked()) {
                    vm.setOnlyLookBuilder(1);
                } else {
                    vm.setOnlyLookBuilder(0);
                }
            }
        });

        itemMessageDetailHeadBinding.cbHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        setTitle(vm.getPage() + "/" + vm.getDetail().getComment_pages());
        itemMessageDetailHeadBinding.setData(vm.getDetail());
        if (vm.getDetail().getContents() != null && vm.getDetail().getContents().size() > 0) {
            for (MessageDetail.ContentsBean contentsBean : vm.getDetail().getContents()) {
                ItemMessageDetailBinding itemMessageDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_message_detail, null, false);
                itemMessageDetailBinding.setData(contentsBean);
                if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
                    for (String img : contentsBean.getImgs()) {
                        GlideImageView imageView = new GlideImageView(this);
                        GlideUtil.load(imageView, img);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        itemMessageDetailBinding.llContent.addView(imageView, params);
                    }
                }
                itemMessageDetailHeadBinding.llContent.addView(itemMessageDetailBinding.getRoot());
            }
        }

    }

    @Override
    public void refreshComent(boolean isRefresh, List<MessageComment> list) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh, list);
    }

}

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
import com.nmssdmf.customerviewlib.BaseQuickAdapter;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.CommentListContentAdapter;
import com.zhihangjia.mainmodule.adapter.FlipOverAdapter;
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
    private FlipOverAdapter flipOverAdapter;
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

        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getCommentList(true);
            }

            @Override
            public void onLoadMore() {
                vm.getCommentList(false);
            }
        });

        flipOverAdapter = new FlipOverAdapter(vm.getFlipList());
        binding.crvPage.setAdapter(flipOverAdapter);
        flipOverAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                vm.setPage(position + 1);
                vm.getCommentList(true);
            }
        });
    }

    private void setListener() {
        baseTitleBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vm.getFlipList().size() -1 <= 0) {//无法翻页
                    return;
                }
                if (binding.crvPage.getVisibility() == View.GONE) {
                    binding.vBlackBackgroud.setVisibility(View.VISIBLE);
                    binding.crvPage.setVisibility(View.VISIBLE);
                } else {
                    binding.vBlackBackgroud.setVisibility(View.GONE);
                    binding.crvPage.setVisibility(View.GONE);
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
        setTitle(vm.getPage() + "/" + vm.detail.get().getComment_pages());

        flipOverAdapter.notifyDataSetChanged();

        itemMessageDetailHeadBinding.setVm(vm);
        if (vm.detail.get().getContents() != null && vm.detail.get().getContents().size() > 0) {
            for (MessageDetail.ContentsBean contentsBean : vm.detail.get().getContents()) {
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

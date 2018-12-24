package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jushi.gallery.activity.BeautyImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.PermissionCompat;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.BaseQuickAdapter;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
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

import java.io.Serializable;
import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 信息详情activity
 * @date 2018/11/20 11:10
 */
public class MessageDetailActivity extends BaseTitleActivity implements MessageDetailCB, CommentListContentAdapter.ItemClickListener, FlipOverAdapter.OnItemClickListener, UMShareListener {
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
        binding.setVm(vm);
        adapter = new CommentListContentAdapter(vm.getList(), vm.messageId, this);
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
                vm.getMessageDetail();
                vm.page.set(1);
                vm.getCommentList(true);
            }

            @Override
            public void onLoadMore() {
                vm.getCommentList(false);
            }
        });

        flipOverAdapter = new FlipOverAdapter(vm.getFlipList(), this);
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
                if (vm.getFlipList().size() - 1 <= 0) {//无法翻页
                    return;
                }
                if (binding.crvPage.getVisibility() == View.GONE) {
                    binding.vBlackBackgroud.setVisibility(View.VISIBLE);
                    binding.crvPage.setVisibility(View.VISIBLE);
                    flipOverAdapter.setCurrentPage(vm.page.get());
                } else {
                    binding.vBlackBackgroud.setVisibility(View.GONE);
                    binding.crvPage.setVisibility(View.GONE);
                }
            }
        });
        baseTitleBinding.tTitle.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.share) {
                if (PermissionCompat.getInstance().checkAboutSharePermission(MessageDetailActivity.this)) {
                    UMWeb web = new UMWeb(vm.detail.get().getShare_url());
                    web.setTitle(vm.detail.get().getTitle());//标题
                    if (vm.imageUrls.size() > 0) {
                        UMImage image = new UMImage(this, vm.imageUrls.get(0).getPath());
                        web.setThumb(image);  //缩略图
                    }
                    if (vm.firstContent.length() > 30) {
                        vm.firstContent = vm.firstContent.substring(0, 30);
                    }
                    web.setDescription(vm.firstContent);//描述
                    new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(web).setCallback(MessageDetailActivity.this)
                            .open();
                }
            }
            return false;
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
            itemMessageDetailHeadBinding.llContent.removeAllViews();
            vm.imageUrls.clear();
            vm.firstContent = "";
            for (MessageDetail.ContentsBean contentsBean : vm.detail.get().getContents()) {
                ItemMessageDetailBinding itemMessageDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_message_detail, null, false);
                itemMessageDetailBinding.content.setText(contentsBean.getNote());
                if (!TextUtils.isEmpty(contentsBean.getNote()) && TextUtils.isEmpty(vm.firstContent)) {
                    vm.firstContent = contentsBean.getNote();
                }
                if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
                    for (String img : contentsBean.getImgs()) {
                        GlideImageView imageView = new GlideImageView(this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.topMargin = DensityUtil.dpToPx(this, 12);
                        layoutParams.bottomMargin = DensityUtil.dpToPx(this, 12);
                        imageView.setLayoutParams(layoutParams);
                        GlideUtil.load(imageView, img);
                        itemMessageDetailBinding.llContent.addView(imageView);
                        vm.imageUrls.add(Uri.parse(img));
                        int index = vm.imageUrls.size() - 1;
                        imageView.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BeautyImageGalleryActivity.PAGE_INDEX, index);
                            bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY, (Serializable) vm.imageUrls);
                            doIntent(BeautyImageGalleryActivity.class, bundle);
                        });
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

    @Override
    public void scrollToTop() {
        binding.crv.getRv().scrollToPosition(1);
        if (adapter.getData().size() > 0) {
            int[] location = new int[2];
            RecyclerView.ViewHolder vh = binding.crv.getRv().findViewHolderForAdapterPosition(1);
            if (vh == null) {
                itemMessageDetailHeadBinding.llCommentTop.getLocationInWindow(location);
                binding.crv.getRv().scrollBy(0, location[1] - DensityUtil.dpToPx(this, 44) - WindowUtil.getStatusBarHeight(this));
            } else {
                vh.itemView.getLocationInWindow(location);
                binding.crv.getRv().scrollBy(0, location[1] - DensityUtil.dpToPx(this, 64) - WindowUtil.getStatusBarHeight(this));
            }
            JLog.d(TAG, "location:" + location[0] + "   " + location[1]);

        } else {
            ToastUtil.showMsg("暂无评论，快点来发表评论吧");
        }
    }

    @Override
    public void onCommentZanRequestFinish(int position) {
        MessageComment messageComment = adapter.getData().get(position);
        if ("0".equals(messageComment.getGive_state())) {
            adapter.getData().get(position).setGive_sum(String.valueOf(Integer.valueOf(messageComment.getGive_sum()) + 1));
            adapter.getData().get(position).setGive_state("1");
        } else {
            adapter.getData().get(position).setGive_sum(String.valueOf(Integer.valueOf(messageComment.getGive_sum()) - 1));
            adapter.getData().get(position).setGive_state("0");
        }
        adapter.notifyItemChanged(position + 1);
    }

    @Override
    public void refreshGiveInfo(int zanNum, String giveNames) {
        if (!TextUtils.isEmpty(giveNames)) {
            String[] names = giveNames.split("、");
            if (names.length < zanNum)
                itemMessageDetailHeadBinding.tvGiveinfo.setText(giveNames + "等" + zanNum + "人点赞");
            else
                itemMessageDetailHeadBinding.tvGiveinfo.setText(giveNames);
        }
    }

    @Override
    public void setPageTitle(String title) {
        setTitle(title);
    }

    @Override
    public void endFresh() {
        binding.crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(true);
    }

    @Override
    public void onZanClick(MessageComment item, int position) {
        JLog.d(TAG, "position:" + position);
        vm.onZan("1", item.getComment_id(), position - 1);
    }

    @Override
    public void onPageClick(int page) {
        vm.setPage(page);
        vm.getCommentList(true);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
//        Toast.makeText(this, "分享成功了", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}

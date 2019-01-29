package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.jushi.gallery.activity.BeautyImageGalleryActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.DoubleClickListener;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.PermissionCompat;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.XYHeadLineDetailAdapter;
import com.zhihangjia.mainmodule.bean.HeadLineDetailInfo;
import com.zhihangjia.mainmodule.callback.XYHeadLineDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityYxheadlineDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxheadlineDetailHeadBinding;
import com.zhihangjia.mainmodule.viewmodel.XYHeadLineDetailVM;
import com.zhixingjia.bean.mainmodule.HeadLines;
import com.zhixingjia.bean.mainmodule.MessageComment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 信息详情activity
 * @date 2018/11/20 11:10
 */
public class XYHeadLineDetailActivity extends BaseTitleActivity implements XYHeadLineDetailCB,
        XYHeadLineDetailAdapter.ItemClickListener,
        UMShareListener {
    private final String TAG = XYHeadLineDetailActivity.class.getSimpleName();
    private XYHeadLineDetailVM vm;
    private ActivityYxheadlineDetailBinding binding;
    private XYHeadLineDetailAdapter adapter;
    private ItemYxheadlineDetailHeadBinding itemYxheadlineDetailHeadBinding;

    @Override
    public String setTitle() {
        return "宜兴头条";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityYxheadlineDetailBinding) baseViewBinding;
        binding.setVm(vm);
        adapter = new XYHeadLineDetailAdapter(new ArrayList(), this);
        adapter.setYxHeadlineId(vm.messageId);
        binding.crv.setAdapter(adapter);
        itemYxheadlineDetailHeadBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_yxheadline_detail_head, null, false);
        adapter.addHeaderView(itemYxheadlineDetailHeadBinding.getRoot());
        baseTitleBinding.tTitle.inflateMenu(R.menu.share);
        setListener();

        vm.getYXHeadLineDetail();

        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getYXHeadLineDetail();
                vm.page.set(1);
            }

            @Override
            public void onLoadMore() {
                vm.getCommentList(false);
            }
        });
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.share) {
                showShareWindow();
            }
            return false;
        });
        binding.ivCommentBackClick.setOnTouchListener(new DoubleClickListener(new DoubleClickListener.ClickCallBack() {
            @Override
            public void oneClick() {

            }

            @Override
            public void doubleClick() {
                scrollToTop();
            }
        }));
    }

    @Override
    public void showShareWindow() {
        if (PermissionCompat.getInstance().checkAboutSharePermission(XYHeadLineDetailActivity.this)) {
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
                    .withMedia(web).setCallback(XYHeadLineDetailActivity.this)
                    .open();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_yxheadline_detail;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new XYHeadLineDetailVM(this);
        return vm;
    }

    @Override
    public void initView() {

        itemYxheadlineDetailHeadBinding.setVm(vm);
        adapter.getData().clear();
        vm.imageUrls.clear();
        adapter.setYxZanPosition(-1);
        adapter.setYxCommentFilterPosition(-1);
        vm.firstContent = "";
        //放内容数据
        List<HeadLineDetailInfo> headLineDetailInfos = new ArrayList<>();
        if (vm.detail.get().getContents() != null && vm.detail.get().getContents().size() > 0) {
            int imageIndex = 0;
            for (HeadLines.ContentsBean contentsBean : vm.detail.get().getContents()) {
                if (!TextUtils.isEmpty(contentsBean.getNote()) && TextUtils.isEmpty(vm.firstContent)) {
                    vm.firstContent = contentsBean.getNote();
                }
                HeadLineDetailInfo headLineDetailInfo = new HeadLineDetailInfo();
                headLineDetailInfo.setType(0);
                headLineDetailInfo.setNote(contentsBean.getNote());
                headLineDetailInfos.add(headLineDetailInfo);
                if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
                    for (String image : contentsBean.getImgs()) {
                        HeadLineDetailInfo imageInfo = new HeadLineDetailInfo();
                        imageInfo.setImage(image);
                        imageInfo.setImageIndex(imageIndex);
                        imageInfo.setType(0);
                        headLineDetailInfos.add(imageInfo);
                        //添加到图片列表中，用于查看大图的传参
                        vm.imageUrls.add(Uri.parse(image));
                        imageIndex++;
                    }
                }
            }
        }
        //添加评论信息筛选
        HeadLineDetailInfo headLineDetailInfo = new HeadLineDetailInfo();
        headLineDetailInfo.setType(2);
        headLineDetailInfos.add(headLineDetailInfo);
        adapter.getData().addAll(headLineDetailInfos);
        adapter.setYxCommentFilterPosition(adapter.getData().size() - 1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshComent(boolean isRefresh, List<MessageComment> list) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        List<HeadLineDetailInfo> lineDetailInfos = new ArrayList<>();
        for (MessageComment messageComment : list) {
            HeadLineDetailInfo headLineDetailInfo = new HeadLineDetailInfo();
            headLineDetailInfo.setMessageComment(messageComment);
            headLineDetailInfo.setType(3);
            lineDetailInfos.add(headLineDetailInfo);
        }
        adapter.notifyDataChangedAfterLoadMore(false, lineDetailInfos);
    }

    @Override
    public void scrollToTop() {
        if (adapter.getData().size() - 1 > adapter.getYxCommentFilterPosition()) {
            int[] location = new int[2];
            RecyclerView.ViewHolder vh = binding.crv.getRv().findViewHolderForAdapterPosition(adapter.getYxCommentFilterPosition() + 1);
            if (vh == null) {
                binding.crv.getRv().scrollToPosition(adapter.getYxCommentFilterPosition() + 1);
//                itemYxheadlineDetailHeadBinding.llCommentTop.getLocationInWindow(location);
//                binding.crv.getRv().scrollBy(0, location[1] - DensityUtil.dpToPx(this, 44) - WindowUtil.getStatusBarHeight(this));
            } else {
                vh.itemView.getLocationInWindow(location);
                binding.crv.getRv().scrollBy(0, location[1] - WindowUtil.getStatusBarHeight(this));
            }
            JLog.d(TAG, "location:" + location[0] + "   " + location[1]);

        } else {
            ToastUtil.showMsg("暂无评论，快点来发表评论吧");
        }
    }

    @Override
    public void onCommentZanRequestFinish(int position) {
        HeadLineDetailInfo headLineDetailInfo = adapter.getData().get(position);
        MessageComment messageComment = headLineDetailInfo.getMessageComment();
        if ("0".equals(messageComment.getGive_state())) {
            messageComment.setGive_sum(String.valueOf(Integer.valueOf(messageComment.getGive_sum()) + 1));
            messageComment.setGive_state("1");
        } else {
            messageComment.setGive_sum(String.valueOf(Integer.valueOf(messageComment.getGive_sum()) - 1));
            messageComment.setGive_state("0");
        }
        adapter.notifyItemChanged(position + 1);
    }

    /**
     * 刷新点赞信息
     *
     * @param zanNum
     * @param giveNames
     */
    @Override
    public void refreshGiveInfo(int zanNum, String giveNames) {
        if (!TextUtils.isEmpty(giveNames)) {
            String[] names = giveNames.split("、");
            if (names.length < zanNum) {
                adapter.notifyGiveInfo(giveNames + "等" + zanNum + "人点赞", zanNum);
                return;
            }
        }
        adapter.notifyGiveInfo(giveNames, zanNum);
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
    public void tvSortClick(boolean isHot, boolean isSortDesc) {
        int filterPosition = adapter.getYxCommentFilterPosition();
        int size = adapter.getData().size();
        while (filterPosition + 1 <= adapter.getData().size() - 1) {
            adapter.getData().remove(filterPosition + 1);
        }
        adapter.notifyItemRangeRemoved(filterPosition+1, size - 1);
        vm.tvSortClick(isHot,isSortDesc);
    }

    @Override
    public void onImageClick(int imageIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(BeautyImageGalleryActivity.PAGE_INDEX, imageIndex);
        bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY, (Serializable) vm.imageUrls);
        doIntent(BeautyImageGalleryActivity.class, bundle);
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

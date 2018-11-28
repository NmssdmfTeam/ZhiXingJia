package com.zhihangjia.mainmodule;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.AllCategoriesActivity;
import com.zhihangjia.mainmodule.activity.CommentActivity;
import com.zhihangjia.mainmodule.activity.ConfirmOrderActivity;
import com.zhihangjia.mainmodule.activity.ConfirmPayActivity;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.activity.MerchantMerchandiseActivity;
import com.zhihangjia.mainmodule.activity.MessageCenterActivity;
import com.zhihangjia.mainmodule.activity.MessageCenterModuleActivity;
import com.zhihangjia.mainmodule.activity.MessageDetailActivity;
import com.zhihangjia.mainmodule.activity.MyPostActivity;
import com.zhihangjia.mainmodule.activity.OrderDetailActivity;
import com.zhihangjia.mainmodule.activity.OrderListPurchaserActivity;
import com.zhihangjia.mainmodule.activity.PaySuccessActivity;
import com.zhihangjia.mainmodule.activity.PoliticsNoticeActivity;
import com.zhihangjia.mainmodule.activity.ReplyActivity;
import com.zhihangjia.mainmodule.activity.SearchActivity;
import com.zhihangjia.mainmodule.activity.SetCouponActivity;
import com.zhihangjia.mainmodule.activity.ShopCouponListActivity;
import com.zhihangjia.mainmodule.databinding.ActivityLunchBinding;
import com.zhihangjia.mainmodule.viewmodel.LunchVM;

public class LunchActivity extends BaseTitleActivity {
    private String TAG = LunchActivity.class.getSimpleName();
    private ActivityLunchBinding binding;

    private LunchVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new LunchVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "跳转首页";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLunchBinding) baseViewBinding;

        vm.doLogin();
        binding.btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doIntent(MainActivity.class,null);
            }
        });

        binding.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doIntent(MessageCenterActivity.class,null);
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(SearchActivity.class,null);
            }
        });

        binding.btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(AllCategoriesActivity.class,null);
            }
        });

        binding.btnMerchantMerchandise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MerchantMerchandiseActivity.class,null);
            }
        });

        binding.btnMerchantMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MerchantMainActivity.class,null);
            }
        });
        binding.btnPoliticsNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(PoliticsNoticeActivity.class,null);
            }
        });

        binding.btnMerchandiseDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MerchandiseDetailActivity.class,null);
            }
        });

        binding.btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(ConfirmOrderActivity.class,null);
            }
        });

        binding.btnPaySuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(PaySuccessActivity.class,null);
            }
        });

        binding.btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(ConfirmPayActivity.class,null);
            }
        });

        binding.btnMessageCenterModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MessageCenterModuleActivity.class,null);
            }
        });
        binding.btnMessageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MessageDetailActivity.class,null);
            }
        });
        binding.btnMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MyPostActivity.class,null);
            }
        });
        binding.btnMyOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(OrderListPurchaserActivity.class,null);
            }
        });

        binding.btnOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(OrderDetailActivity.class,null);
            }
        });

        binding.btnShopCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(ShopCouponListActivity.class,null);
            }
        });

        binding.btnSetShopCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(SetCouponActivity.class,null);
            }
        });
        binding.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(ReplyActivity.class,null);
            }
        });
        binding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(CommentActivity.class,null);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_lunch;
    }
}

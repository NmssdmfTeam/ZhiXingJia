package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.WindowQrcodeBinding;

public class QrCodeWindow extends PopupWindow {
    private WindowQrcodeBinding binding;
    private Context context;

    public QrCodeWindow(final Context context) {
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_qrcode, null, false);
        setContentView(binding.getRoot());
        setHeight(DensityUtil.dpToPx(context, 280f));
        setWidth(DensityUtil.dpToPx(context, 292f));
        binding.ivClose.setOnClickListener(v -> dismiss());

        setOnDismissListener(() -> WindowUtil.setBackgroundAlpha((Activity) context, 1f));
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        // 设置PopupWindow是否能响应外部点击事件
        setOutsideTouchable(true);
        setTouchable(true);
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void loadImage(String qrcode) {
        String[] data = qrcode.split(",");
        if (data.length > 1) {
            String qrcodeImageData = data[1];
            Bitmap bitmap = base64ToBitmap(qrcodeImageData);
//            Glide.with(context)
//                    .load(bitmap)
//                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
//                    .into(binding.glideimageQrcode);
            binding.glideimageQrcode.setImageBitmap(bitmap);
        }
    }
}

package com.nmssdmf.commonlib.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.databinding.WindowWheelPickerBinding;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 * 单个wheelpicker 的window
 */

public class WheelPickerWindow extends PopupWindow {
    private WindowWheelPickerBinding binding;

    public WheelPickerWindow(final Context context, final List<String> list, final WheelPickerWindowCB cb) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_wheel_picker, null, false);
        setContentView(binding.getRoot());
        setHeight(DensityUtil.dpToPx(context, 256.5f));
        setWidth(MATCH_PARENT);

        if (list == null || list.size() == 0) {
            return;
        }
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cb.tvSureClick(list.get(binding.wp.getCurrentItemPosition()), binding.wp.getCurrentItemPosition());
            }
        });

        binding.wp.setData(list);


        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowUtil.setBackgroundAlpha((Activity) context, 1);
            }
        });
    }

    public void setCurrentItem(int position) {
        binding.wp.setSelectedItemPosition(position);
    }

    public void changeDataList(List<String> list){
        binding.wp.setData(list);
    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }
}

package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.ContentBean;
import com.zhihangjia.mainmodule.databinding.ItemContentNoteandimageBinding;
import com.zhihangjia.mainmodule.generated.callback.OnClickListener;
import com.zhixingjia.bean.mainmodule.ContentsBean;

import java.util.List;

public class LifeServiceDetailAdapter extends BaseDataBindingAdapter<ContentBean, ItemContentNoteandimageBinding> {
    private ImageClickListener listener;

    public interface ImageClickListener {
        void onImageClick(int position, ContentBean contentBean);
    }

    public LifeServiceDetailAdapter(@Nullable List<ContentBean> data, ImageClickListener listener) {
        super(R.layout.item_content_noteandimage, data);
        this.listener = listener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemContentNoteandimageBinding> helper, ContentBean item, int position) {
        ItemContentNoteandimageBinding itemContentNoteandimageBinding = helper.getBinding();
        itemContentNoteandimageBinding.setData(item);
//        itemContentNoteandimageBinding.iv.setOnClickListener(new OnClickListener())
        itemContentNoteandimageBinding.iv.setOnClickListener(v -> {
            listener.onImageClick(position, item);
        });
    }

    public ImageClickListener getListener() {
        return listener;
    }

    public void setListener(ImageClickListener listener) {
        this.listener = listener;
    }
}

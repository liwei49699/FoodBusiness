package com.chengzhen.foodbusiness.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengzhen.foodbusiness.R;

import java.util.List;

public class NotificationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public NotificationAdapter() {
        super(R.layout.item_notification);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}

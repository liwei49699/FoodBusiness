package com.chengzhen.foodbusiness.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengzhen.foodbusiness.R;

import java.util.List;

public class LocalCheckAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LocalCheckAdapter() {
        super(R.layout.item_local_check);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}

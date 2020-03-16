package com.chengzhen.foodbusiness.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengzhen.foodbusiness.R;

import java.util.List;

public class ToDoTaskAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ToDoTaskAdapter() {
        super(R.layout.item_to_do_task);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

        helper.setText(R.id.rtv_index,helper.getLayoutPosition() + "");
    }
}

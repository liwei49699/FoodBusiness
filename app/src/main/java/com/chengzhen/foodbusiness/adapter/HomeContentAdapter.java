package com.chengzhen.foodbusiness.adapter;

import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengzhen.foodbusiness.R;

public class HomeContentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private String[] mTitles = {"系统报表", "许可档案","备案管理", "数据统计"};
    private int[] mIcons = {R.drawable.ic_system_form
            ,R.drawable.ic_permission_files_home,R.drawable.ic_record_manager_home,
            R.drawable.ic_data_statistics_home};

    public HomeContentAdapter() {
        super(R.layout.item_home_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_content,mTitles[helper.getLayoutPosition()]);
        helper.setImageResource(R.id.iv_content,mIcons[helper.getLayoutPosition()]);
//        int layoutPosition = helper.getLayoutPosition();
//        RelativeLayout rlContent = helper.getView(R.id.rrl_content);
//        if(layoutPosition >= 3) {
//            rlContent.setPadding(0,0,0,0);
//        } else {
//            rlContent.setPadding(0,0,0, ConvertUtils.dp2px(6));
//        }
    }
}

package com.chengzhen.foodbusiness.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.adapter.NotificationAdapter;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.view.RecycleViewDivider;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.rv_notification)
    RecyclerView mRvNotification;
    @BindView(R.id.srl_notification)
    SwipeRefreshLayout mSrlNotification;
    private NotificationAdapter mNotificationAdapter;

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_notification;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();
        setCenterTitle("通知公告");

        mSrlNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSrlNotification.setRefreshing(false);
            }
        });
    }

    @Override
    protected void getData() {

        mNotificationAdapter = new NotificationAdapter();
        mRvNotification.setLayoutManager(new LinearLayoutManager(this));
        mRvNotification.setAdapter(mNotificationAdapter);
        mNotificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(NotificationDetailsActivity.class);
            }
        });

//        RecycleViewDivider itemDecoration = new RecycleViewDivider.Builder(mContext)
//                .setOrientation(RecycleViewDivider.VERTICAL)
//                .setStyle(RecycleViewDivider.Style.BETWEEN)
//                .setColorRes(R.color.color_b3b6be)
//                .setMarginLeft(COMPLEX_UNIT_DIP,75)
//                .setMarginRight(COMPLEX_UNIT_DIP,20)
//                .setSize(COMPLEX_UNIT_DIP,1)
//                .build();
//
//        mRvNotification.addItemDecoration(itemDecoration);

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            stringList.add("");
        }

        mNotificationAdapter.setNewData(stringList);
    }
}

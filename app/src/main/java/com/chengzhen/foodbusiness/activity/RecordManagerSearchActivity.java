package com.chengzhen.foodbusiness.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.adapter.RecordManagerAdapter;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.view.RecycleViewDivider;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class RecordManagerSearchActivity extends BaseActivity {

    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    @BindView(R.id.srl_record)
    SwipeRefreshLayout mSrlRecord;
    private RecordManagerAdapter mRecordManagerAdapter;

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_record_manager_search;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        setCenterTitle("备案查询列表");
    }

    @Override
    protected void getData() {

        mSrlRecord.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlRecord.setRefreshing(false);
            }
        });

        mRecordManagerAdapter = new RecordManagerAdapter();
        mRvRecord.setLayoutManager(new LinearLayoutManager(this));
        mRvRecord.setAdapter(mRecordManagerAdapter);

        mRecordManagerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                ToastUtils.showShort(position + "");
                startActivity(RecordManagerDetailsActivity.class);
            }
        });

        RecycleViewDivider itemDecoration = new RecycleViewDivider.Builder(mContext)
                .setOrientation(RecycleViewDivider.VERTICAL)
                .setStyle(RecycleViewDivider.Style.START)
                .setColorRes(R.color.color_f8f8f8)
                .setSize(COMPLEX_UNIT_DIP,8)
                .build();
        mRvRecord.addItemDecoration(itemDecoration);

        initHeadView();

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            stringList.add("");
        }

        mRecordManagerAdapter.setNewData(stringList);
    }

    private void initHeadView() {

        View headView = LayoutInflater.from(this).inflate(R.layout.head_record_manager_search, mRvRecord, false);
        mRecordManagerAdapter.addHeaderView(headView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // Return whether touch the view.
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom);
        }
        return false;
    }

}

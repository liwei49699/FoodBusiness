package com.chengzhen.foodbusiness.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.adapter.ToDoTaskAdapter;
import com.chengzhen.foodbusiness.base.BaseFragment;
import com.chengzhen.foodbusiness.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class TaskCheckGoingFragment extends BaseFragment {

    @BindView(R.id.rv_task_check)
    RecyclerView mRvTaskCheck;
    @BindView(R.id.srl_check)
    SwipeRefreshLayout mSrlCheck;
    private ToDoTaskAdapter mToDoTaskAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_check_going;
    }

    @Override
    protected void initView() {

        mSrlCheck.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlCheck.setRefreshing(false);
            }
        });
        mToDoTaskAdapter = new ToDoTaskAdapter();
        mRvTaskCheck.setLayoutManager(new LinearLayoutManager(mContext));
        mRvTaskCheck.setAdapter(mToDoTaskAdapter);
        RecycleViewDivider itemDecoration = new RecycleViewDivider.Builder(mContext)
                .setOrientation(RecycleViewDivider.VERTICAL)
                .setStyle(RecycleViewDivider.Style.BOTH)
                .setColorRes(R.color.color_f8f8f8)
                .setSize(COMPLEX_UNIT_DIP, 5)
                .build();
        mRvTaskCheck.addItemDecoration(itemDecoration);

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("");
        }
        mToDoTaskAdapter.setNewData(stringList);
    }

    @Override
    protected void getData() {

    }
}

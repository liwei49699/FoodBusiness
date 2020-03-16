package com.chengzhen.foodbusiness.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.activity.LocalCheckActivity;
import com.chengzhen.foodbusiness.activity.NotificationActivity;
import com.chengzhen.foodbusiness.activity.PermissionFilesActivity;
import com.chengzhen.foodbusiness.activity.RecordManagerActivity;
import com.chengzhen.foodbusiness.adapter.HomeContentAdapter;
import com.chengzhen.foodbusiness.adapter.TaskPagerAdapter;
import com.chengzhen.foodbusiness.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePermissionFragment extends BaseFragment {

    @BindView(R.id.iv_message_more)
    ImageView mIvMessageMore;
    @BindView(R.id.tv_message_count)
    TextView mTvMessageCount;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.mi_task_type)
    MagicIndicator mMiTaskType;
    @BindView(R.id.vp_task_type)
    ViewPager mVpTaskType;

    private String[] mTitles = {"待核查", "已核查"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_permission;
    }

    @Override
    protected void initView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        mRvContent.setLayoutManager(gridLayoutManager);
        mRvContent.setNestedScrollingEnabled(false);
        HomeContentAdapter homeContentAdapter = new HomeContentAdapter();
        mRvContent.setAdapter(homeContentAdapter);

        homeContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (position) {
                    case 0:

                        break;
                    case 1:
                        startActivity(PermissionFilesActivity.class);
                        break;
                    case 2:
                        startActivity(LocalCheckActivity.class);
                        break;
                    case 3:
                        startActivity(RecordManagerActivity.class);
                        break;
                }
            }
        });

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            strings.add("");
        }
        homeContentAdapter.setNewData(strings);
    }

    @Override
    protected void getData() {

        initViewpager();
        initIndicator();
    }

    private void initViewpager() {

        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(getChildFragmentManager());
        TaskCheckGoingFragment taskCheckGoingFragment = new TaskCheckGoingFragment();
        TaskCheckGoingFragment homePermissionFragment1 = new TaskCheckGoingFragment();
        taskPagerAdapter.setFragmentList(taskCheckGoingFragment,homePermissionFragment1);
        mVpTaskType.setOffscreenPageLimit(2);
        mVpTaskType.setAdapter(taskPagerAdapter);
    }

    private void initIndicator() {

        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                View customLayout = LayoutInflater.from(context).inflate(R.layout.item_indicator_task, null);
                TextView titleText = customLayout.findViewById(R.id.title_text);
                View vLine = customLayout.findViewById(R.id.v_line);
                titleText.setText(mTitles[index]);
                commonPagerTitleView.setContentView(customLayout);

                if(index == 0) {
                    vLine.setVisibility(View.VISIBLE);
                } else if(index == 1) {
                    vLine.setVisibility(View.INVISIBLE);
                }

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(getResources().getColor(R.color.color_primary_blue));
                        titleText.setTypeface(Typeface.DEFAULT_BOLD);

                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(getResources().getColor(R.color.color_b4b4b4));
                        titleText.setTypeface(Typeface.DEFAULT);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                        titleImg.setScaleX(1.3f + (1.0f - 1.3f) * leavePercent);
//                        titleImg.setScaleY(1.3f + (1.0f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                        titleImg.setScaleX(1.0f + (1.3f - 1.0f) * enterPercent);
//                        titleImg.setScaleY(1.0f + (1.3f - 1.0f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpTaskType.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setYOffset(ConvertUtils.dp2px(6));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(ConvertUtils.dp2px(5));
                indicator.setLineWidth(ScreenUtils.getScreenWidth()/2);
//                indicator.setRoundRadius(ConvertUtils.dp2px(2));
                indicator.setColors(Color.parseColor("#2F8AFD"));
                return indicator;
            }
        });
        mMiTaskType.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMiTaskType, mVpTaskType);
    }

    @OnClick({R.id.rl_message})
    public void homeTopClick(View view){

        switch (view.getId()) {
            case R.id.rl_message :
                startActivity(NotificationActivity.class);
                break;
        }
    }
}

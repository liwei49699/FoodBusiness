package com.chengzhen.foodbusiness;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.adapter.MainBottomPagerAdapter;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.fragment.HomePermissionFragment;
import com.chengzhen.foodbusiness.fragment.PersonInfoFragment;
import com.chengzhen.foodbusiness.manager.ActivityManager;
import com.chengzhen.foodbusiness.view.ScrollViewPager;
import com.gyf.immersionbar.ImmersionBar;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;


import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mi_bottom_nav)
    MagicIndicator mMiBottomNav;
    @BindView(R.id.vp_main)
    ScrollViewPager mVpMain;

    private String[] mTitles = {"许可", "监督","统计", "我的"};
    private int[] mSelectIcons = {R.drawable.ic_bottom_permission_select
            ,R.drawable.ic_bottom_permission_select,R.drawable.ic_bottom_permission_select,
            R.drawable.ic_bottom_permission_select};
    private int[] mNormalIcons = {R.drawable.ic_bottom_permission_normal
            ,R.drawable.ic_bottom_permission_normal,R.drawable.ic_bottom_permission_normal,
            R.drawable.ic_bottom_permission_normal};

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    protected void getData() {
        initViewpager();
        initIndicator();
    }

    private void initViewpager() {

        MainBottomPagerAdapter mainBottomPagerAdapter = new MainBottomPagerAdapter(getSupportFragmentManager());
        HomePermissionFragment homePermissionFragment = new HomePermissionFragment();
        HomePermissionFragment homePermissionFragment1 = new HomePermissionFragment();
        HomePermissionFragment homePermissionFragment2 = new HomePermissionFragment();
        PersonInfoFragment personInfoFragment = new PersonInfoFragment();
        mainBottomPagerAdapter.setFragmentList(homePermissionFragment,homePermissionFragment1,homePermissionFragment2,personInfoFragment);
        mVpMain.setOffscreenPageLimit(4);
        mVpMain.setAdapter(mainBottomPagerAdapter);
    }

    private void initIndicator() {

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                View customLayout = LayoutInflater.from(context).inflate(R.layout.item_indicator_bottom, null);
                ImageView titleImg = customLayout.findViewById(R.id.title_img);
                TextView titleText = customLayout.findViewById(R.id.title_text);
                titleText.setText(mTitles[index]);
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(getResources().getColor(R.color.color_primary_blue));
                        titleImg.setImageResource(mSelectIcons[index]);

                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(getResources().getColor(R.color.color_B3B3B3));
                        titleImg.setImageResource(mNormalIcons[index]);
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


                        mVpMain.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMiBottomNav.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMiBottomNav, mVpMain);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                exit();
                return true;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            // 结束Activity从堆栈中移除
            ActivityManager.getInstance().exitApp();
        }
    }
}

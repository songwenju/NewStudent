package com.panting.newStudent.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.panting.newStudent.R;
import com.panting.newStudent.base.BaseActivity;
import com.panting.newStudent.common.AppConstants;
import com.panting.newStudent.utils.LogUtil;
import com.panting.newStudent.utils.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.sp_tv_title)
    TextView mSpTvTitle;
    @BindView(R.id.sp_tv_button)
    TextView mSpTvButton;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onInitView() {
        ButterKnife.bind(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/childFont.ttf");
        mSpTvButton.setTypeface(custom_font);
        custom_font = Typeface.createFromAsset(getAssets(),"fonts/yizhi.ttf");
        mSpTvTitle.setTypeface(custom_font);
    }

    @Override
    protected void onInitData() {
    }

    @Override
    protected void onSetViewData() {
        //判断应用是否是第一次开启应用
        boolean isFirstOpen = SpUtil.getBoolean(AppConstants.FIRST_OPEN, true);
        if (isFirstOpen) {
            Intent intent = new Intent(this, FirstGuideActivity.class);
            startActivity(intent);
            finish();
        } else {
//            //获得包管理器
//            PackageManager packageManager = getPackageManager();
//            try {
//                PackageInfo packageInfo = packageManager.getPackageInfo("com.panting.newStudent", 0);
//                //查看是否有版本更新
//                getUpdate(packageInfo);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
            enterHomeActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHomeActivity();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    protected void onInitListener() {

    }

    public void enterHomeActivity() {
        LogUtil.i(this,"SplashActivity.enterHomeActivity.");
        Intent intent = new Intent(mContext, HomeActivity.class);
        mContext.startActivity(intent);
        if (mContext instanceof SplashActivity) {
            ((SplashActivity) mContext).finish();
        }
    }


    /**
     * 请求网络更新
     *
     * @param packageInfo packageInfo
     */
    public void getUpdate(PackageInfo packageInfo) {
        LogUtil.i(this,"SplashActivity.getUpdate.");
        new Handler().postDelayed(new Runnable(){

            public void run() {
               enterHomeActivity();
            }

        }, 2000);
    }
}

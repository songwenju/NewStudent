package com.panting.newStudent.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.panting.newStudent.Adapter.LeftMenuAdapter;
import com.panting.newStudent.R;
import com.panting.newStudent.base.BaseActivity;
import com.panting.newStudent.common.AppConstants;
import com.panting.newStudent.utils.CommonUtil;
import com.panting.newStudent.utils.LogUtil;
import com.panting.newStudent.utils.MD5Utils;
import com.panting.newStudent.utils.SpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeActivity
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tb_custom)
    Toolbar mToolbar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.iv_avatar)
    SimpleDraweeView mIvAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.tv_left_setting)
    TextView mTvLeftSetting;
    @BindView(R.id.rv_left_menu_content)
    RecyclerView mRvLeftMenuContent;
    @BindView(R.id.rl_left_head)
    RelativeLayout mRlLeftHead;

    private ActionBar mActionBar;
    private AlertDialog mPwdSetDialog, mPwdInputDialog;
    private List<String> mMenuInfoList;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onInitView() {
        ButterKnife.bind(this);
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        initPwdSetDialog(builder);
        initPwdInputDialog(builder);

    }

    /**
     * 初始化输入密码的dialog
     *
     * @param builder
     */
    private void initPwdInputDialog(AlertDialog.Builder builder) {
        mPwdInputDialog = builder.create();
        View dialogView = View.inflate(mContext, R.layout.dialog_input_password, null);
        mPwdInputDialog.setView(dialogView, 0, 0, 0, 0);
        final EditText pwd = (EditText) dialogView.findViewById(R.id.et_pwd);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button confirmBtn = (Button) dialogView.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdRaw = pwd.getText().toString().trim();
                String pwdStr = MD5Utils.decode(pwdRaw);
                String spPwd = SpUtil.getString(AppConstants.ENTER_PROOF_PWD, "");
                if (TextUtils.isEmpty(pwdRaw)) {
                    toast("密码不能为空！");
                } else if (!pwdStr.equals(spPwd)) {
                    toast("密码输入错误！");
                } else {
                    mPwdInputDialog.dismiss();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPwdInputDialog.dismiss();
            }
        });
    }

    /**
     * 初始化设置密码的dialog
     *
     * @param builder
     */
    private void initPwdSetDialog(AlertDialog.Builder builder) {
        mPwdSetDialog = builder.create();
        View dialogView = View.inflate(mContext, R.layout.dialog_set_password, null);
        mPwdSetDialog.setView(dialogView, 0, 0, 0, 0);
        final EditText pwd = (EditText) dialogView.findViewById(R.id.et_pwd);
        final EditText rePwd = (EditText) dialogView.findViewById(R.id.et_rePwd);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button confirmBtn = (Button) dialogView.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdStr = pwd.getText().toString().trim();
                String rePwdStr = rePwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwdStr)) {
                    toast("密码不能为空！");
                } else if (TextUtils.isEmpty(rePwdStr)) {
                    toast("再次输入的密码不能为空！");
                } else if (!pwdStr.equals(rePwdStr)) {
                    toast("两次密码不一致！");
                } else {
                    SpUtil.putString(AppConstants.ENTER_PROOF_PWD, MD5Utils.decode(pwdStr));
                    mPwdSetDialog.dismiss();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPwdSetDialog.dismiss();
            }
        });
    }


    @Override
    protected void onInitData() {
        //设置Toolbar
        mToolbar.setTitle("新生入学管理系统");
        setSupportActionBar(mToolbar);
        //设置drawerLayout
        setDrawerLayout();

        mMenuInfoList = new ArrayList<>();
        mMenuInfoList.add("学校信息");
        mMenuInfoList.add("宿舍信息");
        mMenuInfoList.add("教学楼信息");
        mMenuInfoList.add("餐厅信息");

    }

    @Override
    protected void onSetViewData() {

        HashMap<String, Integer> windowSize = CommonUtil.getWindowSize(this);
        int windowHeight = windowSize.get("height");

        //设置左侧布局各部分的高度
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRlLeftHead.getLayoutParams();
        params.height = (int) (windowHeight / 3 + 0.5);
        mRlLeftHead.setLayoutParams(params);
        mRvLeftMenuContent.setLayoutParams(params);

        //设置RecyclerView
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置垂直滚动，也可以设置横向滚动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //RecyclerView设置布局管理器
        mRvLeftMenuContent.setLayoutManager(layoutManager);
        //设置侧拉菜单的recyclerView的布局

        LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(mContext, mMenuInfoList, windowSize);
        mRvLeftMenuContent.setAdapter(leftMenuAdapter);
        leftMenuAdapter.setOnItemClickListener(new LeftMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    Intent intent = new Intent(mContext, QrCodeCaptureActivity.class);
                    mContext.startActivity(intent);
                } else if (position == 1) {

                } else if (position == 2) {

                }
            }
        });
        //添加分割线
        mRvLeftMenuContent.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
        ));
        //mRvLeftMenuContent.setOnClickListener(this);
    }

    /**
     * 设置drawerLayout
     */
    private void setDrawerLayout() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true); //设置返回键可用
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        mDrawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(this, "resume");
        String spUrl = SpUtil.getString(AppConstants.AVATAR_SERVER_PATH, "");
        LogUtil.i(this, "spUrl：" + spUrl);
        Uri uri = Uri.parse(spUrl);
        mIvAvatar.setImageURI(uri);
        String userName = SpUtil.getString(AppConstants.LOGIN_USER, "");
        mUserName.setText(userName.equals("") ? "未登录" : userName);
    }




    @Override
    protected void onInitListener() {
        mIvAvatar.setOnClickListener(this);
        mTvLeftSetting.setOnClickListener(this);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                if (!TextUtils.isEmpty(SpUtil.getString(AppConstants.LOGIN_USER, ""))) {
                    //显示用户信息
//                    Intent intent = new Intent(this, UserInfoActivity.class);
//                    startActivity(intent);
                } else {
                    //注册登录界面
//                    Intent intent = new Intent(this, LoginRegActivity.class);
//                    startActivity(intent);
                }
                break;
            case R.id.tv_left_setting:
//                Intent intent = new Intent(this, AppSettingActivity.class);
//                startActivity(intent);
                break;
        }
    }

}

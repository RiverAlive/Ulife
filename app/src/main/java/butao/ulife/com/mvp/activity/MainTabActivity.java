package butao.ulife.com.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import butao.ulife.com.R;
import butao.ulife.com.base.ActivityManager;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.jpush.activity.ChatListActivity;
import butao.ulife.com.mvp.fragment.ChatListFragment;
import butao.ulife.com.mvp.fragment.HomeFragment;
import butao.ulife.com.mvp.fragment.IdleFragment;
import butao.ulife.com.mvp.fragment.MyFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MainTabActivity extends MvpActivity {
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;
    private FragmentManager mFM = null;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        if(TextUtils.isEmpty(position)) {
            changeHome();
        }else{
            if("3".equals(position)){
                if(CApplication.getIntstance().isLogin()){
                    changeMessage();
                }else{
                    gotoActivity(MainTabActivity.this, LoginActivity.class);
                }
            }
        }
    }

    @OnClick({R.id.ll_idle, R.id.ll_center, R.id.ll_message, R.id.ll_my, R.id.ll_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                changeHome();
                break;
            case R.id.ll_idle:
                changeIdle();
                break;
            case R.id.ll_center:
                    if(CApplication.getIntstance().isLogin()){
                        gotoActivity(MainTabActivity.this, FabuActivity.class);
                    }else{
                        gotoActivity(MainTabActivity.this, LoginActivity.class);
                    }
                break;
            case R.id.ll_message:
                    if(CApplication.getIntstance().isLogin()){
                        changeMessage();
                    }else{
                        gotoActivity(MainTabActivity.this, LoginActivity.class);
                    }
                break;
            case R.id.ll_my:
                    if(CApplication.getIntstance().isLogin()){
                        changeMY();
                    }else{
                        gotoActivity(MainTabActivity.this, LoginActivity.class);
                    }
                break;
        }
    }

    /**
     * 切换fragement
     */
    private void changeHome() {
        Fragment f = new HomeFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.fl_fragment, f);
        ft.commit();
    }

    /**
     * 切换fragement
     */
    public void changeIdle() {
        Fragment f = new IdleFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.fl_fragment, f);
        ft.commit();
    }

    /**
     * 切换fragement
     */
    public void changeMessage() {
        Fragment f = new ChatListFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.fl_fragment, f);
        ft.commit();
    }

    /**
     * 切换fragement
     */
    public void changeMY() {
        Fragment f = new MyFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.fl_fragment, f);
        ft.commit();
    }

    private long exitTime = 0;

    //重写 onKeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().AppExit(MainTabActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

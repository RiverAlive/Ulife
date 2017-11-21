package butao.ulife.com.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.utils.CleanUtils;

import java.io.File;

import butao.ulife.com.R;
import butao.ulife.com.base.ActivityManager;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.jpush.chatting.utils.FileHelper;
import butao.ulife.com.jpush.chatting.utils.SharePreferenceManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class SettingActivity extends MvpActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.ll_back, R.id.txt_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_exit:
                cApplication.setLogin(false);
                cApplication.setToken(null);
                CleanUtils.cleanInternalSP();
                CleanUtils.cleanInternalCache();
                CleanUtils.cleanInternalFiles();
                SharedPreferences settings = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("guide_activity", "false");
                editor.commit();
                Logout();
                break;
        }
    }
    //退出登录
    public void Logout() {
        // TODO Auto-generated method stub
        final Intent intent = new Intent();
        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            intent.putExtra("userName", info.getUserName());
            File file = info.getAvatarFile();
            if (file != null && file.isFile()) {
                intent.putExtra("avatarFilePath", file.getAbsolutePath());
            } else {
                String path = FileHelper.getUserAvatarPath(info.getUserName());
                file = new File(path);
                if (file.exists()) {
                    intent.putExtra("avatarFilePath", file.getAbsolutePath());
                }
            }
            SharePreferenceManager.setCachedUsername(info.getUserName());
            SharePreferenceManager.setCachedAvatarPath(file.getAbsolutePath());
            JMessageClient.logout();
            intent.setClass(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            gotoActivity(SettingActivity.this, LoginActivity.class);
            finish();
        }
    }
}

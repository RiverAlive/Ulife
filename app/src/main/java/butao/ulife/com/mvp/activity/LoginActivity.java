package butao.ulife.com.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.jpush.chatting.utils.HandleResponseCode;
import butao.ulife.com.jpush.chatting.utils.SharePreferenceManager;
import butao.ulife.com.jpush.database.UserEntry;
import butao.ulife.com.jpush.utils.BitMap;
import butao.ulife.com.mvp.login.LoginApi;
import butao.ulife.com.mvp.login.OnLoginListener;
import butao.ulife.com.mvp.login.Tool;
import butao.ulife.com.mvp.login.UserInfo;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.LoginModel;
import butao.ulife.com.mvp.presenter.LoginPresenter;
import butao.ulife.com.mvp.view.LoginView;
import butao.ulife.com.wxapi.WXEntryActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.api.JCoreInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * 创建时间 ：2017/6/29.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView {
    public static LoginActivity loginActivity;
    private UserInfo mInfo;
    LoginModel loginModel = new LoginModel();
    Platform[] platformlist;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MobSDK.init(getApplicationContext());
        platformlist = ShareSDK.getPlatformList();
    }

    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        //第一次登录需要设置昵称
        boolean flag = SharePreferenceManager.getCachedFixProfileFlag();
        cn.jpush.im.android.api.model.UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo == null) {
        } else {
            //已经登录过但是没设置头像,就跳转到设置头像界面
            cApplication.setPicturePath(myInfo.getAppKey());
        }
        super.onResume();
    }

    @Override
    public void getLoginSuccess(BaseModel<LoginModel> model) {
        if ("200".equals(model.getCode())) {
            loginModel = model.getData();
            final String userId = loginModel.getSocialLogin().getUid();
            final String password = loginModel.getSocialLogin().getUid();
            cApplication.setLogin(true);
            cApplication.setLoginIMG(loginModel.getSocialLogin().getImgPath());
            cApplication.setLoginName(loginModel.getSocialLogin().getName());
            cApplication.setLoginId(loginModel.getSocialLogin().getId());
            cApplication.setLoginUID(loginModel.getSocialLogin().getUid());
            cApplication.setWxcode(loginModel.getSocialLogin().getWxcode());
            cApplication.setLocation(loginModel.getSocialLogin().getLocation());
            cApplication.setSchool(loginModel.getSocialLogin().getSchool());
            finish();
            if ("0".equals(model.getData().getIsFirst())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JMessageClient.register(loginModel.getSocialLogin().getUid(), loginModel.getSocialLogin().getUid(), new BasicCallback() {
                            @Override
                            public void gotResult(final int status, final String desc) {
                                if (status == 0) {
                                    juphLogin(loginModel.getSocialLogin().getUid(), loginModel.getSocialLogin().getUid());
                                } else {
                                    if (810007 == status || 899001 == status || 898001 == status) {
                                        juphLogin(userId, password);
                                    } else {
//                                HandleResponseCode.onHandle(LoginActivity.this, status, false);
                                        finish();
                                    }
                                }
                            }
                        });
                    }
                }).start();
            } else {
                juphLogin(userId, password);
            }
        }
    }

    public void juphLogin(String userId, String password) {
        JMessageClient.login(userId, password, new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                if (status == 0) {
                    String username = JMessageClient.getMyInfo().getUserName();
                    String appKey = JMessageClient.getMyInfo().getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    if ("0".equals(loginModel.getIsFirst())) {
                        updateinfo();
                    } else {
                        hideLoading();
                        finish();
                    }
                } else {
                    hideLoading();
                    finish();
//                    HandleResponseCode.onHandle(LoginActivity.this, status, false);
                }
            }
        });
    }

    public void updateinfo() {
        cn.jpush.im.android.api.model.UserInfo myUserInfo = JMessageClient.getMyInfo();
        myUserInfo.setNickname(loginModel.getSocialLogin().getName());
        JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname, myUserInfo, new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                if (status == 0) {
                    //更新跳转标志
                    SharePreferenceManager.setCachedFixProfileFlag(false);
                    new Thread() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitMap.returnBitMap(loginModel.getSocialLogin().getImgPath());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = bitmap;
                            handler.sendMessage(message);
                        }
                    }.start();
                } else {
                    hideLoading();
                    finish();
//                    HandleResponseCode.onHandle(LoginActivity.this, status, false);
                }
            }
        });


    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                final File file;
                try {
                    file = BitMap.saveBitmap((Bitmap) msg.obj, JMessageClient.getMyInfo().getUserName() + ".png");
                    JMessageClient.updateUserAvatar(file, new BasicCallback() {
                        @Override
                        public void gotResult(final int status, final String desc) {
                            ToastUtils.showShortToast(status + "");
                            if (status == 0) {
                                //如果头像上传失败，删除剪裁后的文件
                                finish();
                            } else {
//                                HandleResponseCode.onHandle(LoginActivity.this, status, false);
                                if (file.delete()) {
                                }
                            }
                            hideLoading();
                            finish();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    hideLoading();
                    finish();
                }
                finish();
            }else{
                finish();
            }
        }
    };


    @OnClick({R.id.txt_cancl, R.id.img_weixin, R.id.img_QQ, R.id.img_sina})
    public void onClick(View view) {
        Platform platform = null;
        switch (view.getId()) {
            case R.id.txt_cancl:
                finish();
                break;
            case R.id.img_weixin:
                for (int i = 0; i < platformlist.length; i++) {
                    if ("Wechat".equals(platformlist[i].getName())) {
                        platform = platformlist[i];
                        if (!Tool.canGetUserInfo(platform)) {
                            continue;
                        }

                        if (platform instanceof CustomPlatform) {
                            continue;
                        }
                    }
                }
                login(platform.getName());
                break;
            case R.id.img_QQ:
                for (int i = 0; i < platformlist.length; i++) {
                    if ("QQ".equals(platformlist[i].getName())) {
                        platform = platformlist[i];
                        if (!Tool.canGetUserInfo(platform)) {
                            continue;
                        }

                        if (platform instanceof CustomPlatform) {
                            continue;
                        }
                    }
                }
                login(platform.getName());
                break;
            case R.id.img_sina:
                for (int i = 0; i < platformlist.length; i++) {
                    if ("SinaWeibo".equals(platformlist[i].getName())) {
                        platform = platformlist[i];
                        if (!Tool.canGetUserInfo(platform)) {
                            continue;
                        }

                        if (platform instanceof CustomPlatform) {
                            continue;
                        }
                    }
                }
                login(platform.getName());
                break;
        }
    }

    /*
         * 演示执行第三方登录/注册的方法
         * <p>
         * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
         *
         * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
         */
    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                HashMap<String, Object> hashMap = res;
                if ("QQ".equals(platform)) {
                    mvpPresenter.loadLogin("QQ", (String) res.get("UserId"), (String) res.get("Token"), (String) res.get("figureurl"), (String) res.get("nickname"));
                } else if ("Wechat".equals(platform)) {
                    mvpPresenter.loadLogin("WEIXIN", (String) res.get("UserId"), (String) res.get("Token"), (String) res.get("headimgurl"), (String) res.get("nickname"));
                } else if ("SinaWeibo".equals(platform)) {
                    mvpPresenter.loadLogin("XINLANG", (String) res.get("UserId"), (String) res.get("Token"), (String) res.get("profile_image_url"), (String) res.get("name"));
                }
                return false;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(LoginActivity.this);
    }


}

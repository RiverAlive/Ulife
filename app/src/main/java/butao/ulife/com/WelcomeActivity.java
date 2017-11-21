package butao.ulife.com;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butao.ulife.com.base.BaseActivity;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.activity.MainTabActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.WelcomeModel;
import butao.ulife.com.permissiongen.PermissionGen;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.util.NetUtil;
import butao.ulife.com.util.SysConst;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 初始化界面
 *
 * @author gujs
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.txt_loading)
    TextView txtLoading;
    @Bind(R.id.txt_secend)
    TextView txtSecend;
    private boolean isfirst_login;
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private ImageView imgBiz;
    CApplication app;
    /**
     * 编辑expired_date中存储的数据
     */
    SharedPreferences.Editor editor;
    /**
     * 轻量级的存储类：主要是保存一些常用的配置
     */
    SharedPreferences sharepre;
    /**
     * Called when the activity is first created.
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    StartHome();
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }
    };
    private TimeCount time;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        sharepre = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        editor = sharepre.edit();
        time = new TimeCount(3000, 1000);
        PermissionGen.needPermission(this, 200, Manifest.permission.ACCESS_FINE_LOCATION);
        PermissionGen.needPermission(this, 200, Manifest.permission.READ_EXTERNAL_STORAGE);
        PermissionGen.needPermission(this, 200, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionGen.needPermission(this, 200, Manifest.permission.MOUNT_FORMAT_FILESYSTEMS);
        app = (CApplication) getApplication();
        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        String isFirstIn = sharepre.getString(KEY_GUIDE_ACTIVITY, "");
        imgBiz = (ImageView) findViewById(R.id.img_biz);
        isfirst_login = !"false".equals(isFirstIn);
        if (isfirst_login) {
            Glide.with(this).load(R.mipmap.img_wecome).into(imgBiz);
            getWelcome();
            txtLoading.setVisibility(View.GONE);
            editor.putString(KEY_GUIDE_ACTIVITY, "false");
            editor.commit();
        } else {
            if (!TextUtils.isEmpty(cApplication.getWelcomeImg())) {
                txtLoading.setVisibility(View.VISIBLE);
                Glide.with(this).load(cApplication.getWelcomeImg()).into(imgBiz);
                getWelcome();
            } else {
                txtLoading.setVisibility(View.GONE);
                Glide.with(this).load(R.mipmap.img_wecome).into(imgBiz);
                getWelcome();
            }
        }
        time.start();
    }


    private void StartHome() {
        boolean flag = NetUtil.NetInfoState(WelcomeActivity.this);
        if (flag) {
            Intent gotoIntent = null;
//            if (isfirst_login) {
            gotoIntent = new Intent(WelcomeActivity.this, MainTabActivity.class);
//            } else {
//                if (app.isLogin()) {
//                } else {
//                    gotoIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                }
//            }
            startActivity(gotoIntent);
            WelcomeActivity.this.finish();
        } else {
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("没有可用的网络连接哦")
                    .setPositiveButton("退出应用", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    }).setNegativeButton("网络设置", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                            if (Build.VERSION.SDK_INT > 10) {
                                intent = new Intent(
                                        Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName component = new ComponentName(
                                        "com.android.settings",
                                        "com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            startActivity(intent);
                        }
                    }).create();
            dialog.show();
            dialog.setCancelable(false);
        }
    }

    @OnClick(R.id.txt_secend)
    public void onClick() {
        StartHome();
        if (time != null) {
            time.cancel();
            time = null;
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtSecend.setClickable(true);
            txtSecend.setText(millisUntilFinished / 1000 + "秒过");
        }

        @Override
        public void onFinish() {
            StartHome();
//            Message msgMessage = new Message();
//            msgMessage.arg1 = 1;
//            handler.sendMessage(msgMessage);
        }
    }
    /**
     * 获取欢迎页图片
     *
     * @param
     */
    private void getWelcome() {
        String path = SysConst.HTTPSNEW_IP + "startArt.do";
        OkHttpUtils.get()
                .url(path)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        WelcomeModel welcomeModel = JsonUtil.fromMJson(response, WelcomeModel.class);
                        if("200".equals(welcomeModel.getCode())) {
                            WelcomeModel.Welcome welcome = welcomeModel.getData().getInfo();
                            if (welcome != null) {
                                if (!TextUtils.isEmpty(welcome.getPath())) {
                                    cApplication.setWelcomeImg(welcome.getPath());
                                }
                            }
                        }
                    }
                });
    }
}

package butao.ulife.com.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.mob.MobSDK;

import butao.ulife.com.cockroach.Cockroach;
import butao.ulife.com.jpush.database.UserEntry;
import butao.ulife.com.jpush.receiver.NotificationClickEventReceiver;
import butao.ulife.com.jpush.utils.SharePreferenceManager;
import butao.ulife.com.util.FileUtil;
import cn.jpush.im.android.api.JMessageClient;

import java.io.IOException;


/**
 * @author gujc
 * @fileName CApplication.java
 * @description 系统程序初始化
 */
public class CApplication extends com.activeandroid.app.Application  {
    public static CApplication mApplication;
    /**
     * 编辑expired_date中存储的数据
     */
    SharedPreferences.Editor editor;
    /**
     * 轻量级的存储类：主要是保存一些常用的配置
     */
    SharedPreferences sharepre;
    /**
     * @Fields latitude : 维度
     */
    public String latitude;
    /**
     * @Fields longitude : 经度
     */
    public String longitude;
    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_SELECT_ALBUM = 10;
    public static final int RESULT_CODE_SELECT_ALBUM = 11;
    public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int REQUEST_CODE_CHAT_DETAIL = 14;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_FRIEND_INFO = 17;
    public static final int REQUEST_CODE_CROP_PICTURE = 18;
    public static final int REQUEST_CODE_ME_INFO = 19;
    public static final int RESULT_CODE_ME_INFO = 20;
    public static final int REQUEST_CODE_ALL_MEMBER = 21;
    public static final int RESULT_CODE_ALL_MEMBER = 22;
    public static final int RESULT_CODE_SELECT_FRIEND = 23;
    public static final int REQUEST_CODE_SEND_LOCATION = 24;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int REQUEST_CODE_SEND_FILE = 26;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int REQUEST_CODE_EDIT_NOTENAME = 28;
    public static final int RESULT_CODE_EDIT_NOTENAME = 29;
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int ON_GROUP_EVENT = 3004;

    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static final String CONV_TITLE = "convTitle";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String TARGET_ID = "targetId";
    public static final String AVATAR = "avatar";
    public static final String NAME = "name";
    public static final String NICKNAME = "nickname";
    public static final String GROUP_ID = "groupId";
    public static final String GROUP_NAME = "groupName";
    public static final String NOTENAME = "notename";
    public static final String GENDER = "gender";
    public static final String REGION = "region";
    public static final String SIGNATURE = "signature";
    public static final String STATUS = "status";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String DRAFT = "draft";
    public static final String DELETE_MODE = "deleteMode";
    public static final String MEMBERS_COUNT = "membersCount";
    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static boolean isNeedAtMsg = true;

    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this, this.getAppkey(), this.getAppSecret());
        sharepre = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        editor = sharepre.edit();
        mApplication = this;
//        WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("Cockroach", thread + "\n" + throwable.toString());
                            throwable.printStackTrace();
                            try {
                                FileUtil.writeToSDcard(mApplication.getApplicationContext(),throwable);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        } catch (Throwable e) {
                        }
                    }
                });
            }
        });
        // 卸载代码
//        Cockroach.uninstall();

        JMessageClient.setDebugMode(true);
        JMessageClient.init(getApplicationContext(), true);
        SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
        //设置Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());
    }

    public static void setPicturePath(String appKey) {
        if (!SharePreferenceManager.getCachedAppKey().equals(appKey)) {
            SharePreferenceManager.setCachedAppKey(appKey);
            PICTURE_DIR = "sdcard/JChatDemo/pictures/" + appKey + "/";
        }
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }

    public static CApplication getIntstance() {
        return mApplication;
    }
    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return sharepre.getBoolean("LOGIN", false);
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean("LOGIN", isLogin).commit();
    }

    /**
     * 获取token
     * @return
     */
    public String getToken() {
        return sharepre.getString("TOKEN", null);
    }
    /**
     *保存token
     * @return
     */
    public void setToken(String token) {
        editor.putString("TOKEN", token).commit();
    }

    /**
     * 获取token
     * @return
     */
    public int getTabPosition() {
        return sharepre.getInt("TabPosition", -1);
    }
    /**
     *保存token
     * @return
     */
    public void setTabPosition(int TabPosition) {//0代表主页,1代表闲置,2发布,3私信,4我的
        editor.putInt("TabPosition", TabPosition).commit();
    }
    /**
     * 获取用户UID
     *
     * @return
     */
    public String getLoginUID() {
        return sharepre.getString("LoginUID", null);
    }

    /**
     * 保存用户UID
     *
     * @return
     */
    public void setLoginUID(String LoginUID) {
        editor.putString("LoginUID", LoginUID).commit();
    }


    /**
     * 获取用户名
     *
     * @return
     */
    public String getLoginName() {
        return sharepre.getString("LoginName", null);
    }

    /**
     * 保存用户名
     *
     * @return
     */
    public void setLoginName(String LoginName) {
        editor.putString("LoginName", LoginName).commit();
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public String getLoginIMG() {
        return sharepre.getString("LoginIMG", null);
    }

    /**
     * 保存用户头像
     *
     * @return
     */
    public void setLoginIMG(String LoginIMG) {
        editor.putString("LoginIMG", LoginIMG).commit();
    }
    /**
     * 获取用户Id
     *
     * @return
     */
    public String getLoginId() {
        return sharepre.getString("LoginId", null);
    }

    /**
     * 保存用户id
     *
     * @return
     */
    public void setLoginId(String LoginId) {
        editor.putString("LoginId", LoginId).commit();
    }
    /**
     * 获取用户wxcode
     *
     * @return
     */
    public String getWxcode() {
        return sharepre.getString("Wxcode", null);
    }

    /**
     * 保存用户wxcode
     *
     * @return
     */
    public void setWxcode(String Wxcode) {
        editor.putString("Wxcode", Wxcode).commit();
    }

    /**
     * 获取用户Location
     *
     * @return
     */
    public String getLocation() {
        return sharepre.getString("Location", null);
    }

    /**
     * 保存用户Location
     *
     * @return
     */
    public void setLocation(String Location) {
        editor.putString("Location", Location).commit();
    }

    /**
     * 获取用户School
     *
     * @return
     */
    public String getSchool() {
        return sharepre.getString("School", null);
    }

    /**
     * 保存用户School
     *
     * @return
     */
    public void setSchool(String School) {
        editor.putString("School", School).commit();
    }
    /**
     * 获取欢迎页图片
     *
     * @return
     */
    public String getWelcomeImg() {
        return sharepre.getString("WelcomeImg", null);
    }

    /**
     * 保存欢迎页图片
     *
     * @return
     */
    public void setWelcomeImg(String WelcomeImg) {
        editor.putString("WelcomeImg", WelcomeImg).commit();
    }

    /**
     * 获取汇率
     *
     * @return
     */
    public float getExchangeRate() {
        return sharepre.getFloat("ExchangeRate",0);
    }

    /**
     * 保存汇率
     *
     * @return
     */
    public void setExchangeRate(float ExchangeRate) {
        editor.putFloat("ExchangeRate", ExchangeRate).commit();
    }
}

package butao.ulife.com.mvp.ClassUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.utils.ToastUtils;

import java.io.File;
import java.io.IOException;

import butao.ulife.com.jpush.chatting.utils.HandleResponseCode;
import butao.ulife.com.jpush.chatting.utils.SharePreferenceManager;
import butao.ulife.com.jpush.database.UserEntry;
import butao.ulife.com.jpush.utils.BitMap;
import butao.ulife.com.mvp.model.LoginModel;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 创建时间 ：2017/7/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class JMessageUtil {
    public Activity mActivity;
    public String userId;
    LoginModel loginModel;
    Bitmap bitmap;
    public JMessageUtil(Activity activity, String userId, LoginModel loginModel) {
        mActivity = activity;
        this.userId = userId;
        this.loginModel = loginModel;
    }
    public  void JMessage(){
        JMessageClient.register(userId, userId, new BasicCallback() {

            @Override
            public void gotResult(final int status, final String desc) {
                if (status == 0) {
                    juphLogin(userId,userId);
                } else {
                    HandleResponseCode.onHandle(mActivity, status, false);
                }
            }
        });
    }
    public void juphLogin(String userId,String password){
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
                    if("0".equals(loginModel.getIsFirst())) {
                        updateinfo();
                    }else{
                        mActivity.finish();
                    }
                } else {
                    HandleResponseCode.onHandle(mActivity, status, false);
                }
            }
        });
    }

    public void updateinfo(){
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
                            bitmap = BitMap.returnBitMap(loginModel.getSocialLogin().getImgPath());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = bitmap;
                            handler.sendMessage(message);
                        }
                    }.start();
                } else {
                    HandleResponseCode.onHandle(mActivity, status, false);
                }
            }
        });



    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                try {
                    final File file = BitMap.saveBitmap((Bitmap) msg.obj, JMessageClient.getMyInfo().getUserName()+".png");
                    JMessageClient.updateUserAvatar(file, new BasicCallback() {
                        @Override
                        public void gotResult(final int status, final String desc) {
                            if (status == 0) {

                                //如果头像上传失败，删除剪裁后的文件
                            } else {
                                HandleResponseCode.onHandle(mActivity, status, false);
                                if (file.delete()) {
                                }
                            }
                            mActivity.finish();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

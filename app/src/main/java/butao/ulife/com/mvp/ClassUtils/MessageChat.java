package butao.ulife.com.mvp.ClassUtils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import butao.ulife.com.base.CApplication;
import butao.ulife.com.jpush.chatting.ChatActivity;
import butao.ulife.com.jpush.chatting.utils.HandleResponseCode;
import butao.ulife.com.jpush.database.UserEntry;
import butao.ulife.com.jpush.entity.Event;
import butao.ulife.com.jpush.entity.EventType;
import butao.ulife.com.mvp.activity.MainTabActivity;
import butao.ulife.com.mvp.activity.WareDetailActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MessageChat {
    Activity mActivity;
    private String UId;
    private UserInfo mToUserInfo;

    public MessageChat(Activity activity, String uid) {
        mActivity = activity;
        UId = uid;
    }

    public void inModule() {
        JMessageClient.getUserInfo(UId, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String desc, UserInfo userInfo) {
                if (status == 0) {
                    JMessageClient.getUserInfo(userInfo.getUserName(), userInfo.getAppKey(), new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int status, String desc, final UserInfo userInfo) {
                            if (status == 0) {
                                mToUserInfo = userInfo;
                                Intent intentChat = new Intent(mActivity, ChatActivity.class);
                                String title = mToUserInfo.getNotename();
                                if (TextUtils.isEmpty(title)) {
                                    title = mToUserInfo.getNickname();
                                    if (TextUtils.isEmpty(title)) {
                                        title = mToUserInfo.getUserName();
                                    }
                                }
                                Conversation conv = JMessageClient.getSingleConversation(mToUserInfo.getUserName(), mToUserInfo.getAppKey());
                                //如果会话为空，使用EventBus通知会话列表添加新会话
                                if (conv == null) {
                                    conv = Conversation.createSingleConversation(mToUserInfo.getUserName(), mToUserInfo.getAppKey());
                                    EventBus.getDefault().post(new Event.Builder()
                                            .setType(EventType.createConversation)
                                            .setConversation(conv)
                                            .build());
                                }
                                intentChat.putExtra(CApplication.CONV_TITLE, title);
                                intentChat.putExtra(CApplication.TARGET_ID, mToUserInfo.getUserName());
                                intentChat.putExtra(CApplication.TARGET_APP_KEY, mToUserInfo.getAppKey());
                                mActivity.startActivity(intentChat);
                            } else {
                                if(status==871300){
                                    juphLogin(CApplication.getIntstance().getLoginUID(),CApplication.getIntstance().getLoginUID());
                                }else{
                                    HandleResponseCode.onHandle(mActivity, status, false);
                                }
                            }
                        }
                    });
                } else {
                    HandleResponseCode.onHandle(mActivity, status, false);
                    Intent intent = new Intent(mActivity, MainTabActivity.class);
                    intent.putExtra("position", "3");
                    mActivity.startActivity(intent);
                }
            }
        });
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
                    inModule();
                }
            }
        });
    }
}

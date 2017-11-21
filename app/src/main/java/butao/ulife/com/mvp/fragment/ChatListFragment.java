package butao.ulife.com.mvp.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.blankj.utilcode.utils.ScreenUtils;

import org.greenrobot.eventbus.Subscribe;

import butao.ulife.com.ChatListController;
import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.base.MvpFragment;
import butao.ulife.com.jpush.ChatListView;
import butao.ulife.com.jpush.chatting.utils.HandleResponseCode;
import butao.ulife.com.jpush.controller.ConversationListController;
import butao.ulife.com.jpush.entity.Event;
import butao.ulife.com.jpush.view.ConversationListView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ChatListFragment extends MvpFragment{
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    private static String TAG = ChatListFragment.class.getSimpleName();
    private View mRootView;
    private ChatListView mConvListView;
    private ChatListController mConvListController;
    private NetworkReceiver mReceiver;
    private Activity mContext;
    private BackgroundHandler mBackgroundHandler;
    private HandlerThread mThread;
    private static final int REFRESH_CONVERSATION_LIST = 0x3000;
    private static final int DISMISS_REFRESH_HEADER = 0x3001;
    private static final int ROAM_COMPLETED = 0x3002;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        //订阅接收消息,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_chat_list,
                null, false);
        mConvListView = new ChatListView(mRootView, this.getActivity());
        mConvListView.initModule();

        mThread = new HandlerThread("Work on MainActivity");
        mThread.start();
        mBackgroundHandler = new BackgroundHandler(mThread.getLooper());
        mConvListController = new ChatListController(mConvListView, this, ScreenUtils.getScreenWidth());
        mConvListView.setItemListeners(mConvListController);
        mConvListView.setLongClickListener(mConvListController);
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (null == activeInfo) {
            mConvListView.showHeaderView();
        } else {
            mConvListView.dismissHeaderView();
            mConvListView.showLoadingHeader();
            mBackgroundHandler.sendEmptyMessageDelayed(DISMISS_REFRESH_HEADER, 1000);
        }
        initReceiver();

    }

    private void initReceiver() {
        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(mReceiver, filter);
    }

    //监听网络状态的广播
    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (null == activeInfo) {
                    mConvListView.showHeaderView();
                } else {
                    mConvListView.dismissHeaderView();
                }
            }
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }


    /**
     * 在会话列表中接收在线消息
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();
        Log.d(TAG, "收到消息：msg = " + msg.toString());
        ConversationType convType = msg.getTargetType();
        if (convType == ConversationType.group) {
            long groupID = ((GroupInfo) msg.getTargetInfo()).getGroupID();
            Conversation conv = JMessageClient.getGroupConversation(groupID);
            if (conv != null && mConvListController != null) {
                if (msg.isAtMe()) {
                    CApplication.isNeedAtMsg = true;
                    mConvListController.getAdapter().putAtConv(conv, msg.getId());
                }
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST,
                        conv));
            }
        } else {
            final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
            final String targetId = userInfo.getUserName();
            final Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
            if (conv != null && mConvListController != null) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //如果设置了头像
                        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
                            //如果本地不存在头像
                            userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                @Override
                                public void gotResult(int status, String desc, Bitmap bitmap) {
                                    if (status == 0) {
                                        mConvListController.getAdapter().notifyDataSetChanged();
                                    } else {
                                        HandleResponseCode.onHandle(mContext, status, false);
                                    }
                                }
                            });
                        }
                    }
                });
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST,
                        conv));
            }
        }
    }

    /**
     * 接收离线消息
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
    }

    /**
     * 消息漫游完成事件
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEvent(ConversationRefreshEvent event) {
        Conversation conv = event.getConversation();
        mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
    }

    private class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_CONVERSATION_LIST:
                    Conversation conv = (Conversation) msg.obj;
                    mConvListController.getAdapter().setToTop(conv);
                    break;
                case DISMISS_REFRESH_HEADER:
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mConvListView.dismissLoadingHeader();
                        }
                    });
                    break;
                case ROAM_COMPLETED:
                    conv = (Conversation) msg.obj;
                    mConvListController.getAdapter().addAndSort(conv);
                    break;
            }
        }
    }

    @Subscribe
    public void createConv(Event event) {
        switch (event.getType()) {
            case createConversation:
                Conversation conv = event.getConversation();
                if (conv != null) {
                    mConvListController.getAdapter().addNewConversation(conv);
                }
                break;
        }
    }

    public void onEventMainThread(Event event) {
        switch (event.getType()) {
            case createConversation:
                Conversation conv = event.getConversation();
                if (conv != null) {
                    mConvListController.getAdapter().addNewConversation(conv);
                }
                break;
            case deleteConversation:
                conv = event.getConversation();
                if (null != conv) {
                    mConvListController.getAdapter().deleteConversation(conv);
                }
                break;
            //收到保存为草稿事件
            case draft:
                conv = event.getConversation();
                String draft = event.getDraft();
                //如果草稿内容不为空，保存，并且置顶该会话
                if (!TextUtils.isEmpty(draft)) {
                    mConvListController.getAdapter().putDraftToMap(conv, draft);
                    mConvListController.getAdapter().setToTop(conv);
                    //否则删除
                } else {
                    mConvListController.getAdapter().delDraftFromMap(conv);
                }
                break;
            case addFriend:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvListController.getAdapter().notifyDataSetChanged();
    }



    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        mContext.unregisterReceiver(mReceiver);
        mBackgroundHandler.removeCallbacksAndMessages(null);
        mThread.getLooper().quit();
        super.onDestroy();
    }



    public void sortConvList() {
        if (mConvListController != null) {
            mConvListController.getAdapter().sortConvList();
        }
    }
}

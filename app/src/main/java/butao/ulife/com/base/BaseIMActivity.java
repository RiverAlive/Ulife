package butao.ulife.com.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.toolbar.StatusBarUtil;
import butao.ulife.com.util.AutoUtils;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * activity基础
 */
public  class BaseIMActivity extends AppCompatActivity {
    public Activity mActivity;
    private CompositeSubscription mCompositeSubscription;
    private List<Call> calls;
    protected CApplication cApplication;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mAvatarSize;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cApplication = CApplication.getIntstance();
        ActivityManager.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //订阅接收消息,子类只要重写onEvent就能收到
        JMessageClient.registerEventReceiver(this);
        AutoUtils.auto(this);
        Utils.init(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_tab_line), 38);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
        Utils.init(mActivity);
        setStatusBar();
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
        Utils.init(mActivity);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;
        Utils.init(mActivity);
    }


    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        ActivityManager.getInstance().finishActivity(this);
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }
    /**
     * 显示弹框
     */
    public void showProgressDialog() {
        BaseDialog.show(BaseIMActivity.this);
    }

    /**
     * 显示弹框，并控制点击消失和加载显示的文字
     */
    public void showProgressDialog(boolean cancelable, String message) {
        BaseDialog.showable(BaseIMActivity.this, message, cancelable);
    }

    /**
     * 关闭弹框
     */
    public void dismissProgressDialog() {
        BaseDialog.dismiss(BaseIMActivity.this);
    }
    /**
     * @param activity 起始对象
     * @param cls      跳转对象
     * @Title: gotoActivity
     * @Description: 界面跳转
     */
    protected void gotoActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        startActivity(intent);
    }

    /**
     * @param activity 起始对象
     * @param cls      跳转对象
     * @param buld     参数
     * @Title: gotoActivity
     * @Description: 界面跳转
     */
    protected void gotoActivity(Activity activity, Class<?> cls, Bundle buld) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(buld);
        startActivity(intent);
    }

    /**
     * 接收登录状态相关事件:登出事件,修改密码事件及被删除事件
     * @param event 登录状态相关事件
     */
    public void onEventMainThread(LoginStateChangeEvent event) {
//        LoginStateChangeEvent.Reason reason = event.getReason();
//        myInfo = event.getMyInfo();
//        if (null != myInfo) {
//            String path;
//            File avatar = myInfo.getAvatarFile();
//            if (avatar != null && avatar.exists()) {
//                path = avatar.getAbsolutePath();
//            } else {
//                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
//            }
//            Log.i(TAG, "userName " + myInfo.getUserName());
//            SharePreferenceManager.setCachedUsername(myInfo.getUserName());
//            SharePreferenceManager.setCachedAvatarPath(path);
//            JMessageClient.logout();
//        }
//        switch (reason) {
//            case user_password_change:
//                String title = mContext.getString(R.string.change_password);
//                String msg = mContext.getString(R.string.change_password_message);
//                dialog = DialogCreator.createBaseCustomDialog(mContext, title, msg, onClickListener);
//                break;
//            case user_logout:
//                title = mContext.getString(R.string.user_logout_dialog_title);
//                msg = mContext.getString(R.string.user_logout_dialog_message);
//                dialog = DialogCreator.createBaseCustomDialog(mContext, title, msg, onClickListener);
//                break;
//            case user_deleted:
//                View.OnClickListener listener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Intent intent = new Intent();
//                        intent.setClass(BaseActivity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        BaseActivity.this.finish();
//                    }
//                };
//                title = mContext.getString(R.string.user_logout_dialog_title);
//                msg = mContext.getString(R.string.user_delete_hint_message);
//                dialog = DialogCreator.createBaseCustomDialog(mContext, title, msg, listener);
//                break;
//        }
//        dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
//        dialog.show();
    }

}

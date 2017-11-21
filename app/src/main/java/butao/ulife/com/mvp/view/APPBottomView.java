package butao.ulife.com.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import butao.ulife.com.R;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.jpush.activity.ChatListActivity;
import butao.ulife.com.mvp.activity.IdleTabActivity;
import butao.ulife.com.mvp.activity.LoginActivity;
import butao.ulife.com.mvp.activity.MainActivity;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class APPBottomView extends LinearLayout{
    Activity mContext;
    LinearLayout llHome,llIdle,llCenter,llMessage,llMy;
    CApplication cApplication;
    public APPBottomView(Activity context) {
        super(context);
        mContext = context;
        cApplication = CApplication.getIntstance();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_bottom, this);
        llHome= (LinearLayout) view.findViewById(R.id.ll_home);
        llIdle= (LinearLayout) view.findViewById(R.id.ll_idle);
        llCenter= (LinearLayout) view.findViewById(R.id.ll_center);
        llMessage= (LinearLayout) view.findViewById(R.id.ll_message);
        llMy= (LinearLayout) view.findViewById(R.id.ll_my);
        initListener();
    }
    private void initListener() {
        llHome.setOnClickListener(listener);
        llIdle.setOnClickListener(listener);
        llCenter.setOnClickListener(listener);
        llMessage.setOnClickListener(listener);
        llMy.setOnClickListener(listener);
    }
    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_home:
                   if(0!=cApplication.getTabPosition()){
                       mContext.startActivity(new Intent(mContext, MainActivity.class));
                       mContext.finish();
                   }
                    break;
                case R.id.ll_idle:
                    if(1!=cApplication.getTabPosition()){
                        mContext.startActivity(new Intent(mContext, IdleTabActivity.class));
                        mContext.finish();
                    }
                    break;
                case R.id.ll_center:
                    if(2!=cApplication.getTabPosition()) {
                        if(CApplication.getIntstance().isLogin()){

                        }else{
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }
                        mContext.finish();
                    }
                    break;
                case R.id.ll_message:
                    if(3!=cApplication.getTabPosition()) {
                        if(CApplication.getIntstance().isLogin()){
                            mContext.startActivity(new Intent(mContext, ChatListActivity.class));
                        }else{
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }
                        mContext.finish();
                    }
                    break;
                case R.id.ll_my:
                    if(4!=cApplication.getTabPosition()) {
                        if(CApplication.getIntstance().isLogin()){

                        }else{
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }
                        mContext.finish();
                    }
                    break;
            }
        }

    };
}

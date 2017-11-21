package butao.ulife.com.mvp.activity;

import android.view.KeyEvent;
import android.widget.Toast;

import butao.ulife.com.base.ActivityManager;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;

/**
 * 创建时间 ：2017/6/29.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyTabActivity extends MvpActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
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
                ActivityManager.getInstance().AppExit(MyTabActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

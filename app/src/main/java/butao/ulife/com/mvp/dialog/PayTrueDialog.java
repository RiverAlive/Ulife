package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;

import butao.ulife.com.R;


/**
 * Created by bodong on 2017/2/7.
 * 退出登录dialog
 */
public class PayTrueDialog {
    View rootView;
    TextView txtTrue;
    TextView txtCancel;
    private Activity mActivity;
    private AlertDialog dialog;

    public PayTrueDialog(Activity context) {
        this.mActivity = context;
    }

    public void showDialog() {
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_paytrue, (ViewGroup) mActivity.findViewById(R.id.dialog_taste));
        dialog = new AlertDialog.Builder(mActivity, AlertDialog.THEME_HOLO_LIGHT).create();
        dialog.setCanceledOnTouchOutside(true);
        txtTrue = (TextView)rootView.findViewById(R.id.txt_true);
        txtCancel = (TextView)rootView.findViewById(R.id.txt_cancel);
        txtTrue.setOnClickListener(Listener);
        txtCancel.setOnClickListener(Listener);
        dialog.setView(rootView);
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int mWidth = ScreenUtils.getScreenWidth();
        int mHeight = ScreenUtils.getScreenHeight();
        params.width = mWidth * 580 / 750;
        params.height = (mWidth * 580 / 750)*4/3;
        dialog.getWindow().setAttributes(params);
    }

  View.OnClickListener Listener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
    switch (v.getId()){
        case R.id.txt_true:
            payTruelistener.PayTrue();
            closeDialog();
            break;
        case R.id.txt_cancel:
            closeDialog();
            break;
    }
      }
  };
    public void closeDialog() {
        dialog.cancel();
    }
    public interface PayTruelistener{
        void PayTrue();
    }
    public  PayTruelistener payTruelistener;

    public void setPayTruelistener(PayTruelistener payTruelistener) {
        this.payTruelistener = payTruelistener;
    }
}

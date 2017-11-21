package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ScreenUtils;

import butao.ulife.com.R;


/**
 * Created by bodong on 2017/2/7.
 */
public class NewEventDialog {
    View rootView;
    ImageView imgContent;
    ImageView imgCanal;
    private Activity mActivity;
    private AlertDialog dialog;

    public NewEventDialog(Activity context) {
        this.mActivity = context;
    }

    public void showDialog() {
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_new_event, (ViewGroup) mActivity.findViewById(R.id.dialog_taste));
        dialog = new AlertDialog.Builder(mActivity, AlertDialog.THEME_HOLO_LIGHT).create();
        imgContent = (ImageView)rootView.findViewById(R.id.img_content);
        imgCanal = (ImageView)rootView.findViewById(R.id.img_canal);
        imgContent.setImageResource(R.mipmap.yindaoye_a);
        imgCanal.setOnClickListener(Listener);
        dialog.setView(rootView);
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int mWidth = ScreenUtils.getScreenWidth();
        int mHeight = ScreenUtils.getScreenHeight();
        params.width = mWidth * 694 / 750;
        params.height = (mWidth * 694 / 750)*4/3;
        dialog.getWindow().setAttributes(params);
    }

  View.OnClickListener Listener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
    switch (v.getId()){
        case R.id.img_canal:
            dialog.dismiss();
            break;
    }
      }
  };
    public void closeDialog() {
        dialog.cancel();
    }
}

package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butao.ulife.com.R;
import butao.ulife.com.mvp.model.IdleClassModel;

/**
 * 拨打电话
 * Created by bodong on 2017/2/22.
 */
public class ImgMesDialog {
    private  static Activity activity;
    String phone ;
    public ImgMesDialog(final Activity activity)
    {
        this.activity = activity;
        showDialog(activity);
    }
    public void dialogShow()
    {
        if (backdialog != null)
        {
            backdialog.show();
        }
    }
    Dialog backdialog = null;
    private void showDialog(final Activity activity) {

        backdialog = new Dialog(activity, R.style.back_dialog);
        backdialog.setContentView(R.layout.dialog_phone);
        backdialog.setCanceledOnTouchOutside(true);
        TextView btncall = (TextView) backdialog.findViewById(R.id.btn_call);
        TextView btnback = (TextView) backdialog.findViewById(R.id.btn_back);
        TextView txt_phone = (TextView)backdialog.findViewById(R.id.txt_phone);
        TextView txt_title = (TextView)backdialog.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.VISIBLE);
            txt_phone.setText("亲!添加一张图片吧!");
        btnback.setText("添加");
        btncall.setText("不了");
        btncall.setOnClickListener(listener);
        btnback.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    imGlistener.comfit();
                    backdialog.dismiss();
                    break;
                case R.id.btn_call:
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    public interface IMGlistener{
        void comfit();
    }
    public  IMGlistener imGlistener;

    public void setIMGlistener(IMGlistener imGlistener) {
        this.imGlistener = imGlistener;
    }

}

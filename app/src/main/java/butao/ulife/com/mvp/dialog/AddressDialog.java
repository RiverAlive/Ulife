package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butao.ulife.com.R;

/**
 * 拨打电话
 * Created by bodong on 2017/2/22.
 */
public class AddressDialog {
    private  static Activity activity;
    public AddressDialog(final Activity activity)
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
        backdialog.setContentView(R.layout.dialog_address);
        backdialog.setCanceledOnTouchOutside(true);
        TextView btncall = (TextView) backdialog.findViewById(R.id.btn_call);
        TextView btnback = (TextView) backdialog.findViewById(R.id.btn_back);
        TextView txt_phone = (TextView)backdialog.findViewById(R.id.txt_phone);
        btncall.setOnClickListener(listener);
        btnback.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    addresslistener.AddressTrue(false);
                    backdialog.dismiss();
                    break;
                case R.id.btn_call:
                    addresslistener.AddressTrue(true);
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    public interface Addresslistener{
        void AddressTrue(boolean flag);
    }
    public Addresslistener addresslistener;

    public void setAddresslistener(Addresslistener addresslistener) {
        this.addresslistener = addresslistener;
    }
}

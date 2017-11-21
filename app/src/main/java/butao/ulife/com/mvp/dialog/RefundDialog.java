package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import butao.ulife.com.R;
import butao.ulife.com.util.TimeUtil;


/**
 * Created by Administrator on 2016/4/28.
 */
public class RefundDialog extends PopupWindow {
    Activity activity1;
    ImageView imgOverTime;
    ImageView imgSelect;
    ImageView imgerroe;
    String reamrk = "";
    public interface Refundlistener{
        void Refund(String remark);
    }
    public Refundlistener refundlistener;

    public void setRefundlistener(Refundlistener refundlistener) {
        this.refundlistener = refundlistener;
    }

    public RefundDialog(Activity activity) {
        super(activity);
        activity1 = activity;
        initView(activity);
    }

    private void initView(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_refund, null);

        imgOverTime=  (ImageView)view.findViewById(R.id.img_overTime);
      imgSelect=  (ImageView)view.findViewById(R.id.img_select);
      imgerroe=  (ImageView)view.findViewById(R.id.img_error);
        view.findViewById(R.id.txt_cancel).setOnClickListener(listener);
        view.findViewById(R.id.txt_Submit).setOnClickListener(listener);
        view.findViewById(R.id.ll_error).setOnClickListener(listener);
        view.findViewById(R.id.ll_select).setOnClickListener(listener);
        view.findViewById(R.id.ll_overtime).setOnClickListener(listener);
        reamrk="超时";
        setContentView(view);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        View view1=view.findViewById(R.id.view);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_cancel:
                    dismiss();
                    break;
                case R.id.txt_SendNow:
                    if (refundlistener != null) {
                        refundlistener.Refund(reamrk);
                    }
                    dismiss();
                    break;
                case R.id.ll_overtime:
                    imgOverTime.setVisibility(View.VISIBLE);
                   imgSelect.setVisibility(View.INVISIBLE);
                  imgerroe.setVisibility(View.INVISIBLE);
                    reamrk="超时";
                    break;
                case R.id.ll_select:
                    imgOverTime.setVisibility(View.INVISIBLE);
                    imgSelect.setVisibility(View.VISIBLE);
                    imgerroe.setVisibility(View.INVISIBLE);
                    reamrk="多点";
                    break;
                case R.id.ll_error:
                    imgOverTime.setVisibility(View.INVISIBLE);
                    imgSelect.setVisibility(View.INVISIBLE);
                    imgerroe.setVisibility(View.VISIBLE);
                    reamrk="选错";
                    break;
                default:
                    dismiss();
                    break;
            }
        }
    };
}

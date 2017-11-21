package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.util.TimeUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;


/**
 * Created by Administrator on 2016/4/28.
 */
public class DeliveryDialog extends PopupWindow {
    Activity activity1;
    RecyclerView recyclerView;
    List<String> hourlist =new ArrayList<>();
    int select=-1;
    public interface Deliverylistener{
        void Delivery(String sendNow);
    }
    public Deliverylistener deliverylistener;

    public void setDeliverylistener(Deliverylistener deliverylistener) {
        this.deliverylistener = deliverylistener;
    }

    public DeliveryDialog(Activity activity) {
        super(activity);
        activity1 = activity;
        initView(activity);
        initList();
    }

    private void initView(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delivery, null);
        recyclerView = (RecyclerView)view.findViewById(R.id.ry_sendtime);
        initRyView();
        view.findViewById(R.id.txt_cancel).setOnClickListener(l);
        TextView txt_week =  (TextView)view.findViewById(R.id.txt_week);
        try {
            txt_week.setText(TimeUtil.DateToWeek(TimeUtil.getDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        view.findViewById(R.id.txt_SendNow).setOnClickListener(l);
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
    private void initList(){
        String  hour = TimeUtil.getHour();
        hourlist.add("60分钟后送达");
        if(Integer.parseInt(hour)<22) {
            for (int i = (Integer.parseInt(hour) + 2); i < 24; i++) {
                int starthour = i;
                int endhour = i + 1;
                String hourItem = starthour + ":00--" + endhour + ":00";
                hourlist.add(hourItem);
            }
        }
    }
    private void initRyView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity1);
        recyclerView.setLayoutManager(layoutManager);
        CommonRecycleAdapter recycleAdapter = new CommonRecycleAdapter<String>(activity1,R.layout.adapter_sendtime,hourlist) {
            @Override
            protected void convert(ViewHolder holder, final String s, final int position) {
                if(!TextUtils.isEmpty(s)){
                    holder.setText(R.id.txt_SendNow,s);
                }else{
                    holder.setText(R.id.txt_SendNow,"");
                }
//                if(select==position){
//                    holder.setTextColorRes(R.id.txt_SendNow,R.color.login_mian);
//                }else{
//                    holder.setTextColorRes(R.id.txt_SendNow,R.color.main_font);
//                }
                holder.setOnClickListener(R.id.txt_SendNow, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select = position;
                        if (deliverylistener != null) {
                            deliverylistener.Delivery(s);
                        }
                        dismiss();
//                        notifyDataSetChanged();
                    }
                });
            }
        };
        recyclerView.setAdapter(recycleAdapter);
    }
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_cancel:
                    dismiss();
                    break;
//                case R.id.txt_SendNow:
//                    if (deliverylistener != null) {
//                        deliverylistener.Delivery("");
//                    }
//                    dismiss();
//                    break;
                default:
                    dismiss();
                    break;
            }
        }
    };
}

package butao.ulife.com.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.view.recyclerview.BaseItemDecoration;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;


/**
 * 分类
 * Created by Administrator on 2016/4/28.
 */
public class FBClassDialog {
    private  static Activity activity;
    CommonRecycleAdapter recycleAdapter;
    IdleClassModel classModel = new IdleClassModel();
    RecyclerView ryClass;
    public FBClassDialog(final Activity activity, IdleClassModel classModel)
    {
        this.activity = activity;
        this.classModel = classModel;
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
        backdialog.setContentView(R.layout.dialog_fa_class);
        backdialog.setCanceledOnTouchOutside(true);
        ryClass = (RecyclerView) backdialog.findViewById(R.id.ry_class);
        initClass();
    }

    public void initClass() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        ryClass.addItemDecoration(new BaseItemDecoration(activity, R.color.bg_boss, 2, ScreenUtils.getScreenWidth()));
        ryClass.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<IdleClassModel.IdleClass>(activity, R.layout.adapter_class, classModel.getCategorys()) {
            @Override
            protected void convert(ViewHolder holder, IdleClassModel.IdleClass sCardModel, int position) {
                if (sCardModel != null) {
                    if (!TextUtils.isEmpty(sCardModel.getName())) {
                        holder.setText(R.id.txt_class, sCardModel.getName());
                    } else {
                        holder.setText(R.id.txt_class, "");
                    }
                }
            }
        };
        ryClass.setAdapter(recycleAdapter);
        initClassClick();
    }
    public void initClassClick(){
        recycleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                List<IdleClassModel.IdleClass> classes = new ArrayList<IdleClassModel.IdleClass>();
                if(recycleAdapter.getDatas()!=null&&recycleAdapter.getDatas().size()>0){
                    classes = recycleAdapter.getDatas();
                    if(classes.get(position)!=null){
                        if(!TextUtils.isEmpty(classes.get(position).getName())){
                            fbClasslistener.comfit(classes.get(position));
                            backdialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    public interface FBClasslistener{
        void comfit(IdleClassModel.IdleClass idleClass);
    }
    public  FBClasslistener fbClasslistener;

    public void setFbClasslistener(FBClasslistener fbClasslistener) {
        this.fbClasslistener = fbClasslistener;
    }
}

package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.presenter.CitiFxPresenter;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class CitiFXActivity extends MvpActivity<CitiFxPresenter> implements CitiFxView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.ry_citifx)
    RecyclerView ryCitifx;
    CommonRecycleAdapter commonRecycleAdapter;
    CitiFXModel citiFXModels = new CitiFXModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citifx);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtRightTitle.setText("换汇点我");
        mvpPresenter.loadCitiFx();
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CitiFXActivity.this,2);
        ryCitifx.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<List<String>>(CitiFXActivity.this,R.layout.adapter_citifx,citiFXModels.getCurrency().getList()) {
            @Override
            protected void convert(ViewHolder holder, List<String> strings, int position) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth()/2,(ScreenUtils.getScreenWidth()/2-80)/2);
                holder.setLayoutParams(R.id.img_citifx,params);
                if(strings!=null&&strings.size()>0){
                    for(int i = 0;i<strings.size();i++){
                        if(i==2){
                            if(!TextUtils.isEmpty(strings.get(i))){
                                holder.setText(R.id.txt_import,strings.get(i));
                            }else{
                                holder.setText(R.id.txt_import,"");
                            }
                        }else if(i==3){
                            if(!TextUtils.isEmpty(strings.get(i))){
                                holder.setText(R.id.txt_into,strings.get(i));
                            }else{
                                holder.setText(R.id.txt_into,"");
                            }
                        }else if(i==4){
                            if(!TextUtils.isEmpty(strings.get(i))){
                                holder.setText(R.id.txt_offer,strings.get(i));
                            }else{
                                holder.setText(R.id.txt_offer,"");
                            }
                        }else if(i==5){
                            if(!TextUtils.isEmpty(strings.get(i))){
                                holder.setText(R.id.txt_middle,strings.get(i));
                            }else{
                                holder.setText(R.id.txt_middle,"");
                            }
                        }else if(i==6){
                            if(!TextUtils.isEmpty(strings.get(i))){
                                holder.setImageRoundGlide(R.id.img_citifx,strings.get(i));
                            }else{
                                holder.setImageResource(R.id.img_citifx,R.mipmap.zhanweitu);
                            }
                        }
                    }

                }
            }
        };
        ryCitifx.setAdapter(commonRecycleAdapter);
    }

    @Override
    protected CitiFxPresenter createPresenter() {
        return new CitiFxPresenter(this);
    }

    @Override
    public void getCitiFxSuccess(BaseModel<CitiFXModel> model) {
        if ("200".equals(model.getCode())) {
            citiFXModels=model.getData();
            initView();
        } else{
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
ToastUtils.showShortToast(msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                Bundle bundle = getIntent().getExtras();
                gotoActivity(CitiFXActivity.this,ExChageCitiFXActivity.class,bundle);
                break;
        }
    }
}

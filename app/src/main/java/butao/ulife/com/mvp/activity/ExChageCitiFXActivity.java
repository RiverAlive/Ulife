package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.ClassUtils.MessageChat;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.presenter.CitiFxPresenter;
import butao.ulife.com.mvp.presenter.EXCitiFxPresenter;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.EXCitiFxView;
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
public class ExChageCitiFXActivity extends MvpActivity<EXCitiFxPresenter> implements EXCitiFxView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ry_citifx)
    RecyclerView ryCitifx;
    CommonRecycleAdapter commonRecycleAdapter;
    WareModel wareModel = new WareModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excitifx);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadEXCitiFx(bundle.getString("cid"));
    }

    private void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(ExChageCitiFXActivity.this);
        ryCitifx.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<WareModel.Ware>(ExChageCitiFXActivity.this,R.layout.adapter_excitifx_ware,wareModel.getWares()) {
            @Override
            protected void convert(ViewHolder holder, final WareModel.Ware ware, int position) {
                if(ware!=null) {
                    if (!TextUtils.isEmpty(ware.getPath())) {
                        holder.setImageGlide(R.id.img_head, ware.getPath());
                    } else {
                        holder.setImageResource(R.id.img_head, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(ware.getTitle())) {
                        holder.setText(R.id.txt_name, ware.getTitle());
                    } else {
                        holder.setText(R.id.txt_name, "");
                    }
                }
                holder.setOnClickListener(R.id.txt_message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cApplication.isLogin()){
                        if(ware!=null) {
                            if (!TextUtils.isEmpty(ware.getUid())) {
                                MessageChat messageChat = new MessageChat(ExChageCitiFXActivity.this,ware.getUid());
                                messageChat.inModule();
                            }else{
                                ToastUtils.showShortToast("本店暂时不可私信");
                            }
                        }
                        }else{
                            gotoActivity(ExChageCitiFXActivity.this,LoginActivity.class);
                        }

                    }
                });
            }
        };
        ryCitifx.setAdapter(commonRecycleAdapter);
    }

    @Override
    protected EXCitiFxPresenter createPresenter() {
        return new EXCitiFxPresenter(this);
    }

    @Override
    public void getEXCitiFxSuccess(BaseModel<WareModel> model) {
        if ("200".equals(model.getCode())) {
            wareModel = model.getData();
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
        }
    }
}

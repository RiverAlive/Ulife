package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.GoodLikeModel;
import butao.ulife.com.mvp.model.MyIssueModel;
import butao.ulife.com.mvp.presenter.GoodLikePresenter;
import butao.ulife.com.mvp.presenter.MyIssuePresenter;
import butao.ulife.com.mvp.view.GoodLikeView;
import butao.ulife.com.mvp.view.MyIssueView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/22.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class GoodLikeActivity extends MvpActivity<GoodLikePresenter> implements GoodLikeView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ry_issue)
    RecyclerView ryIssue;
    GoodLikeModel goodLikeModel = new GoodLikeModel();
    CommonRecycleAdapter commonRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issue);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        if("good".equals(bundle.getString("type"))){
            mvpPresenter.loadMYGood();
        }else if("like".equals(bundle.getString("type"))){
            mvpPresenter.loadMyLike();
        }
    }

    @Override
    protected GoodLikePresenter createPresenter() {
        return new GoodLikePresenter(this);
    }


    @Override
    public void getGoodSuccess(BaseModel<GoodLikeModel> model) {
        if ("200".equals(model.getCode())) {
            goodLikeModel = model.getData();
            initView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getLikeSuccess(BaseModel<GoodLikeModel> model) {
        if ("200".equals(model.getCode())) {
            goodLikeModel = model.getData();
            initView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }


    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GoodLikeActivity.this,3);
        ryIssue.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<GoodLikeModel.GoodLike>(GoodLikeActivity.this, R.layout.adapter_my_wares, goodLikeModel.getList()) {
            @Override
            protected void convert(ViewHolder holder, final GoodLikeModel.GoodLike goodLike, int position) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth()/3-60,ScreenUtils.getScreenWidth()/3-60);
                holder.setLayoutParams(R.id.img_ware,params);
                if (goodLike != null) {
                    if (!TextUtils.isEmpty(goodLike.getPath())) {
                        holder.setImageGlide(R.id.img_ware, goodLike.getPath());
                    } else {
                        holder.setImageResource(R.id.img_ware, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(goodLike.getTitle())) {
                        holder.setText(R.id.txt_ware, goodLike.getTitle());
                    } else {
                        holder.setText(R.id.txt_ware, "");
                    }
                }
            }
        };
        ryIssue.setAdapter(commonRecycleAdapter);
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }
}

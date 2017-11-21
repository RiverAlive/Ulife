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
import butao.ulife.com.mvp.model.MyIssueModel;
import butao.ulife.com.mvp.presenter.MyIssuePresenter;
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
public class MyIssueActivity extends MvpActivity<MyIssuePresenter> implements MyIssueView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ry_issue)
    RecyclerView ryIssue;
    MyIssueModel issueModel = new MyIssueModel();
    CommonRecycleAdapter commonRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issue);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        mvpPresenter.loadMyIssue();
    }

    @Override
    protected MyIssuePresenter createPresenter() {
        return new MyIssuePresenter(this);
    }

    @Override
    public void getMyIssueSuccess(BaseModel<MyIssueModel> model) {
        if ("200".equals(model.getCode())) {
            issueModel = model.getData();
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyIssueActivity.this,3);
        ryIssue.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<MyIssueModel.MyIssue>(MyIssueActivity.this, R.layout.adapter_my_wares, issueModel.getMyWares()) {
            @Override
            protected void convert(ViewHolder holder, final MyIssueModel.MyIssue myIssue, int position) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth()/3-60,ScreenUtils.getScreenWidth()/3-60);
                holder.setLayoutParams(R.id.img_ware,params);
                if (myIssue != null) {
                    if (!TextUtils.isEmpty(myIssue.getPath())) {
                        holder.setImageGlide(R.id.img_ware, myIssue.getPath());
                    } else {
                        holder.setImageResource(R.id.img_ware, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(myIssue.getTitle())) {
                        holder.setText(R.id.txt_ware, myIssue.getTitle());
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

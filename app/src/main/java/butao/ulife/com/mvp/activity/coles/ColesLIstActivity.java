package butao.ulife.com.mvp.activity.coles;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.activity.diner.DinerDetailActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.DinerColesModel;
import butao.ulife.com.mvp.presenter.DinerColesPresenter;
import butao.ulife.com.mvp.view.DinerColesView;
import butao.ulife.com.view.LoadMoreView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/23.
 * 编写人 ：bodong
 * 功能描述 ：餐馆列表
 */
public class ColesLIstActivity extends MvpActivity<DinerColesPresenter> implements DinerColesView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_diner)
    RecyclerView ryDiner;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    CommonRecycleAdapter commonRecycleAdapter;
int pagenum=0;
    String cid="";
    DinerColesModel dinerColesModel = new DinerColesModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinercoles);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        pagenum=0;
        cid =bundle.getString("cid");
        mvpPresenter.loadDinerColes(cid,pagenum);
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        initView();
    }

    @Override
    public void getDinerColesSuccess(BaseModel<DinerColesModel> model) {
        if ("200".equals(model.getCode())) {
            dinerColesModel = model.getData();
            initRyView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
        if (pagenum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    @Override
    protected DinerColesPresenter createPresenter() {
        return new DinerColesPresenter(this);
    }

    /**
     * 初始化界面
     */
    public void initView() {
        SinaRefreshView headerView = new SinaRefreshView(ColesLIstActivity.this);
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(ColesLIstActivity.this);
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pagenum = 0;
                mvpPresenter.loadDinerColes(cid, pagenum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if(dinerColesModel.getWares().size()>9){
                    pagenum ++;
                    mvpPresenter.loadDinerColes(cid, pagenum);
                }else{
                    refreshLayout.finishLoadmore();
                    ToastUtils.showShortToast("暂无更多数据");
                }
            }
        });
    }
    private void initRyView() {
        if (pagenum == 0) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(ColesLIstActivity.this);
        ryDiner.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<DinerColesModel.DinerColes>(ColesLIstActivity.this, R.layout.adapter_dinercoles_item, dinerColesModel.getWares()) {
            @Override
            protected void convert(ViewHolder holder, final DinerColesModel.DinerColes dinerColes, int position) {
                LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth()-40, (ScreenUtils.getScreenWidth() * 41) / 75 + 40);
               rvBannerparams.setMargins(20,10,20,0);
                holder.setLayoutParams(R.id.img_main,rvBannerparams);
                if (dinerColes != null) {
                    if (!TextUtils.isEmpty(dinerColes.getPath())) {
                        holder.setImageGlide(R.id.img_main, dinerColes.getPath());
                    } else {
                        holder.setImageResource(R.id.img_main, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(dinerColes.getTitle())) {
                        holder.setText(R.id.txt_name, dinerColes.getTitle());
                    } else {
                        holder.setText(R.id.txt_name, "");
                    }
                    if (!TextUtils.isEmpty(dinerColes.getMiaoshu())) {
                        holder.setText(R.id.txt_remark,dinerColes.getMiaoshu());
                    } else {
                        holder.setText(R.id.txt_remark,"");
                    }
                }
            }
        };
        ryDiner.setAdapter(commonRecycleAdapter);
    }else {
        commonRecycleAdapter.setupdateDatas(dinerColesModel.getWares());
    }
    if (pagenum == 0) {
        trlView.finishRefreshing();
    } else {
        trlView.finishLoadmore();
    }
        commonRecycleAdapter.setOnItemClickListener(ItemListener);

    }
    MultiItemTypeAdapter.OnItemClickListener ItemListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            if (dinerColesModel != null) {
                if (dinerColesModel.getWares() != null && dinerColesModel.getWares().size() > 0) {
                    if (!TextUtils.isEmpty(dinerColesModel.getWares().get(position).getId())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("wareId", dinerColesModel.getWares().get(position).getId());
                        bundle.putString("title", dinerColesModel.getWares().get(position).getTitle());
                        gotoActivity(ColesLIstActivity.this, ColesDetailActivity.class, bundle);
                    }
                }
            }
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }
}

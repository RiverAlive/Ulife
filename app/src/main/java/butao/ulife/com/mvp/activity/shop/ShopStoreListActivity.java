package butao.ulife.com.mvp.activity.shop;

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

import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopListModel;
import butao.ulife.com.mvp.presenter.shop.ShopStoreListPresenter;
import butao.ulife.com.mvp.view.shop.ShopStoreListView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.LoadMoreView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ShopStoreListActivity extends MvpActivity<ShopStoreListPresenter> implements ShopStoreListView {

    int pageNum = 0;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_diner)
    RecyclerView ryDiner;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    CommonRecycleAdapter commonRecycleAdapter;
    ShopListModel shopListModel = new ShopListModel();
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinercoles);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        pageNum=0;
        type=bundle.getString("type");
        mvpPresenter.loadShopStoreList(type,pageNum);
    }

    @Override
    protected ShopStoreListPresenter createPresenter() {
        return new ShopStoreListPresenter(this);
    }

    @Override
    public void getShopStoreListSuccess(BaseModel<ShopListModel> model) {
        if ("200".equals(model.getCode())) {
            shopListModel = model.getData();
            initRyView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }
    /**
     * 初始化界面
     */
    public void initView() {
        SinaRefreshView headerView = new SinaRefreshView(ShopStoreListActivity.this);
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(ShopStoreListActivity.this);
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 0;
                mvpPresenter.loadShopStoreList(type,pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if(shopListModel.getStoreList().size()>9){
                    pageNum ++;
                    mvpPresenter.loadShopStoreList(type,pageNum);
                }else{
                    refreshLayout.finishLoadmore();
                    ToastUtils.showShortToast("暂无更多数据");
                }
            }
        });
    }
    private void initRyView() {
        if (pageNum == 0) {
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(ShopStoreListActivity.this);
            ryDiner.setLayoutManager(gridLayoutManager);
            commonRecycleAdapter = new CommonRecycleAdapter<ShopListModel.StoreModel>(ShopStoreListActivity.this, R.layout.adapter_dinercoles_item, shopListModel.getStoreList()) {
                @Override
                protected void convert(ViewHolder holder, final ShopListModel.StoreModel storeModel, int position) {
                    LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth()-40, (ScreenUtils.getScreenWidth() * 41) / 75 + 40);
                    rvBannerparams.setMargins(20,10,20,10);
                    holder.setLayoutParams(R.id.img_main,rvBannerparams);
                    if (storeModel != null) {
                        if (!TextUtils.isEmpty(storeModel.getImgUrl())) {
                            holder.setImageGlide(R.id.img_main, storeModel.getImgUrl());
                        } else {
                            holder.setImageResource(R.id.img_main, R.mipmap.zhanweitu);
                        }
                        if (!TextUtils.isEmpty(storeModel.getStoreName())) {
                            holder.setText(R.id.txt_name, storeModel.getStoreName());
                        } else {
                            holder.setText(R.id.txt_name, "");
                        }
                    }
                }
            };
            ryDiner.setAdapter(commonRecycleAdapter);
        }else {
            commonRecycleAdapter.setupdateDatas(shopListModel.getStoreList());
        }
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }

        commonRecycleAdapter.setOnItemClickListener(ItemListener);

    }
    MultiItemTypeAdapter.OnItemClickListener ItemListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            List<ShopListModel.StoreModel> storeModels = commonRecycleAdapter.getDatas();
            if (!JsonUtil.isEmpty(storeModels)) {
                if (!TextUtils.isEmpty(storeModels.get(position).getStoreId())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("StoreId", storeModels.get(position).getStoreId());
                    bundle.putString("status", storeModels.get(position).getStatus());
                    bundle.putString("title", storeModels.get(position).getStoreName());
                    gotoActivity(ShopStoreListActivity.this, ShopDetailActivity.class, bundle);
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

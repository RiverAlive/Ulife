package butao.ulife.com.mvp.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpLoadFragment;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopEvaModel;
import butao.ulife.com.mvp.presenter.shop.ShopEvaPresenter;
import butao.ulife.com.mvp.view.shop.ShopEvaView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.LoadMoreView;
import butao.ulife.com.view.StarBarView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/4.
 * 编写人 ：bodong
 * 功能描述 ：商品评价
 */
public class ShopEvaluateFragment extends MvpLoadFragment<ShopEvaPresenter> implements ShopEvaView {
    @Bind(R.id.star_view)
    StarBarView starview;
    @Bind(R.id.txt_givescore)
    TextView txtGivescore;
    @Bind(R.id.ry_evas)
    RecyclerView ryEvas;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    @Bind(R.id.edt_remark)
    EditText edtRemark;
    private String status = "", storeId = "",score="";
    int pageNum = 0;
    ShopEvaModel shopEvaModel = new ShopEvaModel();
    CommonRecycleAdapter recycleAdapter;
    List<ShopEvaModel.ShopEva> shopEvas = new ArrayList<>();
int select =-1;
    public static ShopEvaluateFragment getInstance(String status) {
        ShopEvaluateFragment sf = new ShopEvaluateFragment();
        sf.status = status;
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eva, null);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        storeId = bundle.getString("StoreId");
        starview.setIntegerMark(true);
        starview.setOnStarChangeListener(new StarBarView.OnStarChangeListener() {
            @Override
            public void onStarChange(float mark) {
                score = (int)mark+"";
                txtGivescore.setText((int)mark + "分");
            }
        });
        initRFView();
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_eva;
    }

    @Override
    protected void lazyLoad() {
        pageNum = 0;
        mvpPresenter.loadShopEvas(pageNum, storeId);
    }

    @Override
    protected ShopEvaPresenter createPresenter() {
        return new ShopEvaPresenter(this);
    }

    /**
     * 评论列表
     *
     * @param model
     */
    @Override
    public void getShopEvasSuccess(BaseModel<ShopEvaModel> model) {
        if ("200".equals(model.getCode())) {
            shopEvaModel = model.getData();
            if (shopEvaModel != null) {
                if (!JsonUtil.isEmpty(shopEvaModel.getList())) {
                    if (pageNum == 0) {
                        shopEvas = shopEvaModel.getList();
                    } else {
                        shopEvas.addAll(shopEvaModel.getList());
                    }
                }
                if (!JsonUtil.isEmpty(shopEvas)) {
                    if (pageNum == 0) {
                        initRyView();
                    } else {
                        recycleAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    /**
     * 点赞
     *
     * @param model
     */
    @Override
    public void getDianzanSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("点赞成功");
            shopEvas.get(select).setLikeNum(shopEvas.get(select).getLikeNum()+1);
            recycleAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }


    @Override
    public void getReviewerEvaSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("评论成功");
            edtRemark.setText("");
            pageNum = 0;
            mvpPresenter.loadShopEvas(pageNum, storeId);
        } else {
            ToastUtils.showShortToast(model.getData().toString());
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

    private void initRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ryEvas.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<ShopEvaModel.ShopEva>(getActivity(), R.layout.adapter_shop_eva, shopEvas) {
            @Override
            protected void convert(ViewHolder holder, final ShopEvaModel.ShopEva shopEva, final int position) {
                StarBarView starBarView = holder.getView(R.id.star_view);
                starBarView.setClick(true);
                starBarView.setIntegerMark(true);
                if (shopEva != null) {
                    if (!TextUtils.isEmpty(shopEva.getImgUrl())) {
                        holder.setImageRoundGlide(R.id.img_eva, shopEva.getImgUrl());
                    }
                    if (!TextUtils.isEmpty(shopEva.getReviewerName())) {
                        holder.setText(R.id.txt_evaName, shopEva.getReviewerName());
                    } else {
                        holder.setText(R.id.txt_evaName, "");
                    }
                    if (!TextUtils.isEmpty(shopEva.getRemark())) {
                        holder.setText(R.id.txt_evaContent, shopEva.getRemark());
                    } else {
                        holder.setText(R.id.txt_evaContent, "");
                    }
                    if (!TextUtils.isEmpty(shopEva.getCreateTime())) {
                        holder.setText(R.id.txt_evaTime, shopEva.getCreateTime());
                    } else {
                        holder.setText(R.id.txt_evaTime, "");
                    }
                    if (!TextUtils.isEmpty(shopEva.getScore())) {
                        holder.setText(R.id.txt_branch, shopEva.getScore() + "branch");
                        starBarView.setStarMark(Float.parseFloat(shopEva.getScore()));
                    } else {
                        holder.setText(R.id.txt_branch, "");
                    }
                    holder.setText(R.id.txt_evaZan, shopEva.getLikeNum()+"");
                    holder.setOnClickListener(R.id.txt_evaZan, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            select =position;
                            mvpPresenter.loadDianzan(shopEva.getReviewerId());
                        }
                    });
                }
            }
        };
        ryEvas.setAdapter(recycleAdapter);
    }

    /**
     * 初始化刷新控件
     */
    public void initRFView() {
        SinaRefreshView headerView = new SinaRefreshView(getActivity());
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(getActivity());
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 0;
                mvpPresenter.loadShopEvas(pageNum, storeId);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (shopEvaModel != null) {
                    if (shopEvaModel.getList().size() > 9) {
                        pageNum++;
                        mvpPresenter.loadShopEvas(pageNum, storeId);
                    } else {
                        refreshLayout.finishLoadmore();
                        ToastUtils.showShortToast("暂无更多数据");
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.txt_send)
    public void onViewClicked() {
        String remark = edtRemark.getText().toString();
        if(TextUtils.isEmpty(remark)){
            edtRemark.requestFocus();
            ToastUtils.showShortToast("请输入评论信息");
            return;
        }
        mvpPresenter.loadReviewerEva(storeId,remark,score);
    }
}

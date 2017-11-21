package butao.ulife.com.mvp.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpFragment;
import butao.ulife.com.mvp.activity.CitiFXActivity;
import butao.ulife.com.mvp.activity.HTMLActivity;
import butao.ulife.com.mvp.activity.HomeImgDetailActivity;
import butao.ulife.com.mvp.activity.HomeTravelActivity;
import butao.ulife.com.mvp.activity.HotboomActivity;
import butao.ulife.com.mvp.activity.LoginActivity;
import butao.ulife.com.mvp.activity.VisaExpressActivity;
import butao.ulife.com.mvp.activity.coles.ColesLIstActivity;
import butao.ulife.com.mvp.activity.diner.DinerLIstActivity;
import butao.ulife.com.mvp.activity.shop.ShopStoreListActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.HTMLModel;
import butao.ulife.com.mvp.model.LoginModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.presenter.MainPresenter;
import butao.ulife.com.mvp.view.HomeImgDetailView;
import butao.ulife.com.mvp.view.MainView;
import butao.ulife.com.view.recyclerview.RecyclerViewBanner;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HomeFragment extends MvpFragment<MainPresenter> implements MainView {

    @Bind(R.id.main_recycler)
    RecyclerView mainRecycler;
    MainModel mainModel = new MainModel();
    CommonRecycleAdapter recycleAdapter;
    @Bind(R.id.rv_banner)
    RecyclerViewBanner rvBanner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        mvpPresenter.loadMainData();
        initView();
        return view;
    }
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }
    @Override
    public void getMainSuccess(BaseModel<MainModel> model) {
        if ("200".equals(model.getCode())) {
            mainModel = model.getData();
            initViewData();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    public void initView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mainRecycler.setNestedScrollingEnabled(false);
        mainRecycler.setLayoutManager(layoutManager);
        LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), (ScreenUtils.getScreenWidth() * 40) / 75 + 40);
        rvBanner.setLayoutParams(rvBannerparams);
        rvBanner.setIndicatorInterval(3000);
    }

    public void initViewData() {
        if (mainModel != null) {
            if (mainModel.getCategorys() != null && mainModel.getCategorys().size() > 0) {
                recycleAdapter = new CommonRecycleAdapter<MainModel.Category>(getActivity(), R.layout.adapter_main, mainModel.getCategorys()) {
                    @Override
                    protected void convert(ViewHolder holder, final MainModel.Category category, int position) {
                        holder.setGlideFillImg(R.id.iv_pic, category.getMainImg());
                    }
                };
            }
            mainRecycler.setAdapter(recycleAdapter);
            recycleAdapter.setOnItemClickListener(ItemListener);
            if (mainModel.getBests() != null && mainModel.getBests().size() > 0) {
                rvBanner.setRvBannerData(mainModel.getBests());
                rvBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, AppCompatImageView bannerView) {
                        bannerView.setScaleType(ImageView.ScaleType.FIT_XY);
                        if (!TextUtils.isEmpty(mainModel.getBests().get(position).getMainImg())) {
                            Glide.with(bannerView.getContext())
                                    .load(mainModel.getBests().get(position).getMainImg())
                                    .placeholder(R.mipmap.shiyu_zhanweitu)
                                    .into(bannerView);
                        } else {
                            Glide.with(bannerView.getContext())
                                    .load(R.mipmap.shiyu_zhanweitu)
                                    .placeholder(R.mipmap.shiyu_zhanweitu)
                                    .into(bannerView);
                        }
                    }
                });
            }
            rvBanner.setOnRvBannerClickListener(new RecyclerViewBanner.OnRvBannerClickListener() {
                @Override
                public void onClick(int position) {
                    if(mainModel!=null) {
                        if (mainModel.getBests() != null && mainModel.getBests().size() > 0) {
                            if(mainModel.getBests().get(position)!=null){
                                if(!TextUtils.isEmpty(mainModel.getBests().get(position).getId())){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Id",mainModel.getBests().get(position).getId());
                                    startActivity(getActivity(), HomeImgDetailActivity.class, bundle);
                                }
                            }
                        }
                    }
                }
            });

        }
    }
    MultiItemTypeAdapter.OnItemClickListener ItemListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            Bundle bundle = new Bundle();
            if(cApplication.isLogin()){
                if(mainModel!=null) {
                    if (mainModel.getCategorys() != null && mainModel.getCategorys().size() > 0) {
                        if (mainModel.getCategorys().get(position) != null) {
                            if ("1010".equals(mainModel.getCategorys().get(position).getCid())) {//外汇市场
                                bundle.putString("cid", mainModel.getCategorys().get(position).getCid());
                                startActivity(getActivity(), CitiFXActivity.class, bundle);
                            } else if ("1005".equals(mainModel.getCategorys().get(position).getCid())
                                    || "1003".equals(mainModel.getCategorys().get(position).getCid())
                                    || "1011".equals(mainModel.getCategorys().get(position).getCid())
                                    || "1012".equals(mainModel.getCategorys().get(position).getCid())
                                    || "1006".equals(mainModel.getCategorys().get(position).getCid())) {
                                //全英活动、布村生活、全英折扣、学习辅导（实习兼职）、吃货专栏
                                bundle.putString("cid", mainModel.getCategorys().get(position).getCid());
                                bundle.putString("title", mainModel.getCategorys().get(position).getName());
                                startActivity(getActivity(), HTMLActivity.class, bundle);
                            } else if ("1009".equals(mainModel.getCategorys().get(position).getCid())) {//代购专栏
                                bundle.putString("cid", mainModel.getCategorys().get(position).getCid());
                                bundle.putString("title", mainModel.getCategorys().get(position).getName());
                                startActivity(getActivity(), HotboomActivity.class, bundle);
                            } else if ("1007".equals(mainModel.getCategorys().get(position).getCid())
                                    || "1008".equals(mainModel.getCategorys().get(position).getCid())) {
                                bundle.putString("cid", mainModel.getCategorys().get(position).getCid());
                                bundle.putString("title", mainModel.getCategorys().get(position).getName());
                                startActivity(getActivity(), VisaExpressActivity.class, bundle);
                            } else if ("1004".equals(mainModel.getCategorys().get(position).getCid())) {//房屋出行
                                bundle.putString("cid", mainModel.getCategorys().get(position).getCid());
                                bundle.putString("title", mainModel.getCategorys().get(position).getName());
                                startActivity(getActivity(), HomeTravelActivity.class, bundle);
                            } else if ("1002".equals(mainModel.getCategorys().get(position).getCid())) {//餐馆列表
                                bundle.putString("type", "1");
                                bundle.putString("title", mainModel.getCategorys().get(position).getName());
                                startActivity(getActivity(), ShopStoreListActivity.class, bundle);
                            } else if ("1001".equals(mainModel.getCategorys().get(position).getCid())) {//超市
                                bundle.putString("type", "2");
                                bundle.putString("title", mainModel.getCategorys().get(position).getName());
                                startActivity(getActivity(), ShopStoreListActivity.class, bundle);
                            }
                        }
                    }
                }
            }else{
                startActivity(getActivity(), LoginActivity.class);
            }
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };
}

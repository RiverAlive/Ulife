package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.ActivityManager;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.presenter.MainPresenter;
import butao.ulife.com.mvp.view.APPBottomView;
import butao.ulife.com.mvp.view.MainView;
import butao.ulife.com.view.recyclerview.RecyclerViewBanner;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @Bind(R.id.main_recycler)
    RecyclerView mainRecycler;
    MainModel mainModel = new MainModel();
    CommonRecycleAdapter recycleAdapter;
    @Bind(R.id.rv_banner)
    RecyclerViewBanner rvBanner;
    APPBottomView appBottomView;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        cApplication.setTabPosition(0);
        appBottomView = new APPBottomView(this);
        mvpPresenter.loadMainData();
        llBottom.removeAllViews();
        llBottom.addView(appBottomView);
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
            ToastUtils.showShortToast(model.getCode());
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
                recycleAdapter = new CommonRecycleAdapter<MainModel.Category>(MainActivity.this, R.layout.adapter_main, mainModel.getCategorys()) {
                    @Override
                    protected void convert(ViewHolder holder, final MainModel.Category category, int position) {
                        holder.setGlideFillImg(R.id.iv_pic, category.getMainImg());
                    }
                };
            }
            mainRecycler.setAdapter(recycleAdapter);

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

        }
    }


    private long exitTime = 0;

    //重写 onKeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().AppExit(MainActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

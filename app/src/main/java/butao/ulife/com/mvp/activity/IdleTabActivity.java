package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.ActivityManager;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.IdleWareModel;
import butao.ulife.com.mvp.presenter.IdlePresenter;
import butao.ulife.com.mvp.view.APPBottomView;
import butao.ulife.com.mvp.view.IdleView;
import butao.ulife.com.view.LoadMoreView;
import butao.ulife.com.view.recyclerview.BaseItemDecoration;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butao.ulife.com.view.slide.DragLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class IdleTabActivity extends MvpActivity<IdlePresenter> implements IdleView {

    @Bind(R.id.ry_idle)
    RecyclerView ryIdle;
    @Bind(R.id.txt)
    TextView txt;
    @Bind(R.id.ry_class)
    RecyclerView ryClass;
    @Bind(R.id.dl)
    DragLayout dl;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    APPBottomView appBottomView;
    String cId = "";
    int pagNum = 0;
    CommonRecycleAdapter recycleAdapter;
    CommonRecycleAdapter IdleAdapter;
    IdleClassModel classModel = new IdleClassModel();
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    IdleWareModel idleWareModel = new IdleWareModel();
    @Override
    protected IdlePresenter createPresenter() {
        return new IdlePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idletab);
        ButterKnife.bind(this);
        cApplication.setTabPosition(1);
        appBottomView = new APPBottomView(this);
        llBottom.removeAllViews();
        llBottom.addView(appBottomView);
        mvpPresenter.loadClassData();
        cId = "1013";
        mvpPresenter.loadWareData(cId, pagNum);
        txt.setText("分类");
        txt.setTextColor(this.getResources().getColor(R.color.bg_tab_line));
        initView();
    }
    /**
     * 初始化界面
     */
    public void initView() {
        SinaRefreshView headerView = new SinaRefreshView(mActivity);
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(mActivity);
        trlView.setBottomView(loadingView);
    }
    /**
     * 分类列表
     *
     * @param model
     */
    @Override
    public void getClassSuccess(BaseModel<IdleClassModel> model) {
        if ("200".equals(model.getCode())) {
            classModel = model.getData();
            initClass();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    /**
     * 商品列表
     *
     * @param model
     */
    @Override
    public void getIdleSuccess(BaseModel<IdleWareModel> model) {
        if ("200".equals(model.getCode())) {
            idleWareModel= model.getData();
            initIdle();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }

    }

    @Override
    public void getLikeSuccess(BaseModel model) {

    }

    /**
     * 失败
     *
     * @param msg
     */
    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
        if (pagNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    public void initIdle() {
        if (pagNum == 0) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(IdleTabActivity.this, 2);
            ryIdle.setLayoutManager(gridLayoutManager);
            IdleAdapter = new CommonRecycleAdapter<IdleWareModel.IdleWare>(IdleTabActivity.this, R.layout.adapter_idle_wares, idleWareModel.getWares()) {
                @Override
                protected void convert(ViewHolder holder, IdleWareModel.IdleWare idleWare, int position) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtils.getScreenWidth() *2)/ 5, (ScreenUtils.getScreenWidth() *2)/ 5);
                    holder.setLayoutParams(R.id.img_ware ,params);
                    LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams((ScreenUtils.getScreenWidth() *2)/ 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.setLayoutParams(R.id.ll_ware ,llparams);
                    if(idleWare!=null){
                        if(!TextUtils.isEmpty(idleWare.getPath())){
                            holder.setImageGlide(R.id.img_ware,idleWare.getPath());
                        }else{
                            holder.setImageResource(R.id.img_ware,R.mipmap.zhanweitu);
                        }
                        if(!TextUtils.isEmpty(idleWare.getTitle())){
                            holder.setText(R.id.txt_ware,idleWare.getTitle());
                        }else{
                            holder.setText(R.id.txt_ware,"");
                        }
                        if(!TextUtils.isEmpty(idleWare.getLikeCheck())){
                            if("-1".equals(idleWare.getLikeCheck())) {
                                holder.setImageResource(R.id.img_like, R.mipmap.like1);
                            }else{
                                holder.setImageResource(R.id.img_like, R.mipmap.like2);
                            }
                        }else{
                            holder.setImageResource(R.id.img_like, R.mipmap.like1);
                        }
                    }
                }
            };
            ryIdle.setAdapter(IdleAdapter);
        } else {
            IdleAdapter.setupdateDatas(idleWareModel.getWares());
        }
        if (pagNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
        initClick();
    }

    public void initClass() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(IdleTabActivity.this);
        ryClass.addItemDecoration(new BaseItemDecoration(this, R.color.bg_boss, 2, ScreenUtils.getScreenWidth()));
        ryClass.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<IdleClassModel.IdleClass>(IdleTabActivity.this, R.layout.adapter_class, classModel.getCategorys()) {
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

    @OnClick(R.id.ll_back)
    public void onClick() {
        DragLayout.Status status = dl.getStatus();
        if (status.equals(DragLayout.Status.CLOSE)) {
            dl.open();
        } else if (status.equals(DragLayout.Status.OPEN)) {
            dl.close();
        }
    }

    /**
     * 点击事件
     */
    public void initClick() {
        IdleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                List<IdleWareModel.IdleWare>  idleWares = new ArrayList<IdleWareModel.IdleWare>();
                if(IdleAdapter.getDatas()!=null&&IdleAdapter.getDatas().size()>0){
                    idleWares = IdleAdapter.getDatas();
                    if(idleWares.get(position)!=null){
                        if(!TextUtils.isEmpty(idleWares.get(position).getId())){

                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pagNum = 0;
                mvpPresenter.loadWareData(cId, pagNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if(idleWareModel.getWares().size()>9){
                    pagNum ++;
                    mvpPresenter.loadWareData(cId, pagNum);
                }else{
                    refreshLayout.finishLoadmore();
                    ToastUtils.showShortToast("暂无更多数据");
                }
            }
        });
    }
    public void initClassClick(){
        recycleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                List<IdleClassModel.IdleClass>  classes = new ArrayList<IdleClassModel.IdleClass>();
                if(recycleAdapter.getDatas()!=null&&recycleAdapter.getDatas().size()>0){
                    classes = recycleAdapter.getDatas();
                    if(classes.get(position)!=null){
                        if(!TextUtils.isEmpty(classes.get(position).getCid())){
                            pagNum=0;
                            cId = classes.get(position).getCid();
                            mvpPresenter.loadWareData(classes.get(position).getCid(),pagNum);
                            dl.close();
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
                ActivityManager.getInstance().AppExit(IdleTabActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

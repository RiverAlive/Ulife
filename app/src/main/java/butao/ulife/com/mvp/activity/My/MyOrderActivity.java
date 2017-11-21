package butao.ulife.com.mvp.activity.My;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.activity.shop.ShopStoreListActivity;
import butao.ulife.com.mvp.dialog.RefundDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.MyOrderModel;
import butao.ulife.com.mvp.presenter.shop.MyOrderPresenter;
import butao.ulife.com.mvp.view.shop.MyOrderView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.LoadMoreView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/29.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class MyOrderActivity extends MvpActivity<MyOrderPresenter> implements MyOrderView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_order)
    RecyclerView ryOrder;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    int pageNum = 0;
    int select= -1;
    MyOrderModel myOrderModel = new MyOrderModel();
    List<MyOrderModel.OrderModel> orderModels = new ArrayList<>();
    CommonRecycleAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("我的订单");
        initRFView();
        pageNum=0;
        mvpPresenter.loadMYOrderList(pageNum);
    }

    @Override
    protected MyOrderPresenter createPresenter() {
        return new MyOrderPresenter(this);
    }

    @Override
    public void getMyOrderListSuccess(BaseModel<MyOrderModel> model) {
        if ("200".equals(model.getCode())) {
            myOrderModel = model.getData();
            if (myOrderModel != null) {
                if (!JsonUtil.isEmpty(myOrderModel.getOrders())) {
                    if (pageNum == 0) {
                        orderModels = myOrderModel.getOrders();
                    } else {
                        orderModels.addAll(myOrderModel.getOrders());
                    }
                }
                if (!JsonUtil.isEmpty(orderModels)) {
                    if (pageNum == 0) {
                        initRYView();
                    } else {
                        recycleAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else{
            ToastUtils.showShortToast(model.getCode());
        }
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    /**
     * 退款回调
     * @param model
     */
    @Override
    public void getOrderRefundSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            orderModels.get(select).setStatus(-10+"");
            orderModels.get(select).setStatus("申请退款");
            recycleAdapter.notifyDataSetChanged();
        }else{
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void CompleteSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            orderModels.get(select).setStatus(20+"");
            orderModels.get(select).setStatus("已完成");
            recycleAdapter.notifyDataSetChanged();
        }else{
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 催单回调
     * @param model
     */
    @Override
    public void getOrderRemindSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("催单成功");
        }else{
            ToastUtils.showShortToast(String.valueOf(model.getData()));
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

    private void initRYView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderActivity.this);
        ryOrder.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<MyOrderModel.OrderModel>(MyOrderActivity.this,R.layout.adapter_my_order,orderModels) {
            @Override
            protected void convert(ViewHolder holder, final MyOrderModel.OrderModel orderModel, final int position) {
                if(orderModel!=null){
                    if(!TextUtils.isEmpty(orderModel.getPhone())){
                        holder.setText(R.id.txt_phone,orderModel.getPhone());
                    }else{
                        holder.setText(R.id.txt_phone,"");
                    }
                    if(!TextUtils.isEmpty(orderModel.getSendPrice())){
                        holder.setText(R.id.txt_sendPrice,"配送费 £"+orderModel.getSendPrice());
                    }else{
                        holder.setText(R.id.txt_sendPrice,"");
                    }

                    if(!TextUtils.isEmpty(orderModel.getActualPrice())){
                        holder.setText(R.id.txt_totalPrice,"£"+orderModel.getActualPrice());
                    }else{
                        holder.setText(R.id.txt_totalPrice,"");
                    }
                    if(!TextUtils.isEmpty(orderModel.getStatusName())){
                        holder.setText(R.id.txt_orderStatus,orderModel.getStatusName());
                    }else{
                        holder.setText(R.id.txt_orderStatus,"");
                    }
                    if(!TextUtils.isEmpty(orderModel.getOrderTime())){
                        holder.setText(R.id.txt_orderTime,orderModel.getOrderTime()+" 下单");
                    }else{
                        holder.setText(R.id.txt_orderTime,"");
                    }
                    if(!JsonUtil.isEmpty(orderModel.getOrderWares())){
                        RecyclerView recyclerView = holder.getView(R.id.ry_wares);
                        initOrderWareRYView(recyclerView,orderModel);
                        holder.setText(R.id.txt_buyNum,  orderModel.getOrderWares().size()+"");
                    }
                    holder.setVisible(R.id.txt_refund,false);
                    holder.setVisible(R.id.txt_remind,false);
                    holder.setVisible(R.id.txt_complete,false);
                    if(!TextUtils.isEmpty(orderModel.getStatus())){
                        if("12".equals(orderModel.getStatus())){//11：退款，12：催单，退款
                            holder.setVisible(R.id.txt_refund,true);
                            holder.setVisible(R.id.txt_remind,true);
                            holder.setVisible(R.id.txt_complete,true);
                        }else if("11".equals(orderModel.getStatus())){//11：退款，12：催单，退款
                            holder.setVisible(R.id.txt_refund,true);
                            holder.setVisible(R.id.txt_remind,false);
                            holder.setVisible(R.id.txt_complete,false);
                        }
                    }
                    holder.setOnClickListener(R.id.txt_remind, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//催单
                            select =position;
                            mvpPresenter.loadOrderRemind(orderModel.getOrderId());
                        }
                    });
                    holder.setOnClickListener(R.id.txt_complete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//完成
                            mvpPresenter.loadOrderComplete(orderModel.getOrderId());
                            select =position;
                        }
                    });
                    holder.setOnClickListener(R.id.txt_refund, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//退款
                            select =position;
                            RefundDialog refundDialog = new RefundDialog(MyOrderActivity.this);
                            refundDialog.showAtLocation(MyOrderActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                            refundDialog.setRefundlistener(new RefundDialog.Refundlistener() {
                                @Override
                                public void Refund(String remark) {
                                    mvpPresenter.loadOrderRefund(orderModel.getOrderId(),remark);
                                }
                            });
                        }
                    });
                }
            }
        };
        ryOrder.setAdapter(recycleAdapter);
    }

    private void initOrderWareRYView(RecyclerView recyclerView, final MyOrderModel.OrderModel orderModel){
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        CommonRecycleAdapter commonRecycleAdapter = new CommonRecycleAdapter<MyOrderModel.OrderWareModel>(MyOrderActivity.this,R.layout.adapter_order_ware,orderModel.getOrderWares()) {
            @Override
            protected void convert(ViewHolder holder, final MyOrderModel.OrderWareModel orderWareModel, final int position) {
                if (orderWareModel != null) {
                    if (!TextUtils.isEmpty(orderWareModel.getImgUrl())) {
                        holder.setImageGlide(R.id.img_orderNWare, orderWareModel.getImgUrl());
                    } else {
                        holder.setBackgroundRes(R.id.img_orderNWare, R.mipmap.zhanweitu);
                    }

                    if (!TextUtils.isEmpty(orderWareModel.getWareName())) {
                        holder.setText(R.id.txt_wareName, orderWareModel.getWareName().replace("\\n", "\n"));
                    } else {
                        holder.setText(R.id.txt_wareName, "");
                    }
                    if (!TextUtils.isEmpty(orderWareModel.getActualPrice())) {
                        holder.setText(R.id.txt_warePrice, "£" + orderWareModel.getActualPrice());
                    } else {
                        holder.setText(R.id.txt_warePrice, "");
                    }
                    if (!TextUtils.isEmpty(orderWareModel.getWareNum())) {
                        holder.setText(R.id.txt_buyNum,  "X"+orderWareModel.getWareNum());
                    } else {
                        holder.setText(R.id.txt_buyNum, "");
                    }
                }
            }
        };
        recyclerView.setAdapter(commonRecycleAdapter);
    }
    /**
     * 初始化界面
     */
    public void initRFView() {
        SinaRefreshView headerView = new SinaRefreshView(MyOrderActivity.this);
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(MyOrderActivity.this);
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 0;
                mvpPresenter.loadMYOrderList(pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if(myOrderModel!=null) {
                    if (myOrderModel.getOrders().size() > 9) {
                        pageNum++;
                        mvpPresenter.loadMYOrderList(pageNum);
                    } else {
                        refreshLayout.finishLoadmore();
                        ToastUtils.showShortToast("暂无更多数据");
                    }
                }
            }
        });
    }
    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}

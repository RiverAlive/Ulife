package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.MyOrderModel;
import butao.ulife.com.mvp.model.shop.product.ProductModel;
import butao.ulife.com.mvp.view.shop.MyOrderView;
import butao.ulife.com.mvp.view.shop.ProductsView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyOrderPresenter extends BasePresenter<MyOrderView> {
    public MyOrderPresenter(MyOrderView view){
        attachView(view);
    }
    /**
     * 订单列表
     */
    public void loadMYOrderList(int pageNum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userId", CApplication.getIntstance().getLoginId());
        map.put("pageNum", pageNum+"");
        map.put("pageSize", "10");
        addSubscription(apiServices.loadMYOrderList(map),
                new ApiCallback<BaseModel<MyOrderModel>>() {
                    @Override
                    public void onSuccess(BaseModel<MyOrderModel> model) {
                        mvpView.getMyOrderListSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

    /**
     * 订单催单orderId,userId
     */
    public void loadOrderRemind(String orderId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userId", CApplication.getIntstance().getLoginId());
        map.put("orderId", orderId);
        addSubscription(apiServices.loadOrderRemind(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getOrderRemindSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }
    /**
     * 订单完成orderId,userId
     */
    public void loadOrderComplete(String orderId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userId", CApplication.getIntstance().getLoginId());
        map.put("orderId", orderId);
        addSubscription(apiServices.loadOrderComplete(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.CompleteSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }
    /**
     * 订单列表
     */
    public void loadOrderRefund(String orderId,String remark){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userId", CApplication.getIntstance().getLoginId());
        map.put("orderId", orderId);
        map.put("remark", remark);
        addSubscription(apiServices.loadOrderRefund(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getOrderRefundSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

}

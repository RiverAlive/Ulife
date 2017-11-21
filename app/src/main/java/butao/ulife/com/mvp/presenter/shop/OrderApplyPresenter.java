package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.view.shop.CheckCutView;
import butao.ulife.com.mvp.view.shop.OrderApplyView;
import butao.ulife.com.pay.alipay.Alipay;
import butao.ulife.com.pay.simcpux.WXAlipay;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class OrderApplyPresenter extends BasePresenter<OrderApplyView> {
    public OrderApplyPresenter(OrderApplyView view){
        attachView(view);
    }
    /**
     * 支付1.orderId:订单ID
     2.channel：渠道，WECHAT：微信，ALIPAY：支付宝
     3.price：价格，人民币

     */
    public void loadOrderApply(String orderId ,String channel,String price){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("channel", channel);
        map.put("price", price);
        addSubscription(apiServices.loadOrderApply(map),
                new ApiCallback<BaseModel<Alipay>>() {
                    @Override
                    public void onSuccess(BaseModel<Alipay> model) {
                        mvpView.getOrderApplySuccess(model);
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
    public void loadWXOrderApply(String orderId ,String channel,String price){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("channel", channel);
        map.put("price", price);
        addSubscription(apiServices.loadWXOrderApply(map),
                new ApiCallback<BaseModel<WXAlipay>>() {
                    @Override
                    public void onSuccess(BaseModel<WXAlipay> model) {
                        mvpView.getWXOrderApplySuccess(model);
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

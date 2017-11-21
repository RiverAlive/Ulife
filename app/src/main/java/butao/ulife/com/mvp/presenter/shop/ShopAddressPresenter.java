package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopAddressModel;
import butao.ulife.com.mvp.model.shop.ShopEvaModel;
import butao.ulife.com.mvp.model.shop.SubOrderModel;
import butao.ulife.com.mvp.view.shop.ShopAddressView;
import butao.ulife.com.mvp.view.shop.ShopEvaView;
import butao.ulife.com.retrofit.ApiCallback;
import butao.ulife.com.retrofit.ApiPost;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopAddressPresenter extends BasePresenter<ShopAddressView> {
    public ShopAddressPresenter(ShopAddressView view){
        attachView(view);
    }
    /**
     * 评论列表
     */
    public void loadShopAddresssList(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userId",CApplication.getIntstance().getLoginId());
        addSubscription(apiServices.loadShopAddresssList(map),
                new ApiCallback<BaseModel<ShopAddressModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopAddressModel> model) {
                        mvpView.getShopAddressSuccess(model);
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
     * 收货地址添加
     *1.userId:用户ID
     2.address:1：地址
     3.name:名称
     4.phone：手机号
     5.sex：性别：1男，0女
     6.id:不传为新增，否则为修改
     */
    public void loadShopAddresss(String address,String name,String phone,String sex,String zipcode){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("address",address);
        map.put("userId",CApplication.getIntstance().getLoginId());
        map.put("name",name);
        map.put("phone",phone);
        map.put("sex",sex);
        map.put("zipcode",zipcode);
        addSubscription(apiServices.loadShopAddresss(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getAddAddressSuccess(model);
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
     * 订单创建
     *  "RMBprice": 80,
     "orderId": 10004
     */
    public void loadCreateOrder(SubOrderModel subOrderModel){
        mvpView.showLoading();
        addSubscription(apiServices.loadCreateOrder(ApiPost.toPost(subOrderModel)),
                new ApiCallback<BaseModel<Map<String,String>>>() {
                    @Override
                    public void onSuccess(BaseModel<Map<String,String>> model) {
                        mvpView.getCreateOrderSuccess(model);
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

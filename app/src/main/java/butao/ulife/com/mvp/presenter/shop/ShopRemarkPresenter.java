package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopRemarkModel;
import butao.ulife.com.mvp.view.shop.CheckCutView;
import butao.ulife.com.mvp.view.shop.ShopRemarkView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopRemarkPresenter extends BasePresenter<ShopRemarkView> {
    public ShopRemarkPresenter(ShopRemarkView view){
        attachView(view);
    }
    /**
     * 备注信息
     */
    public void loadShopRemark(){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("userId", CApplication.getIntstance().getLoginId());
        addSubscription(apiServices.loadShopRemark(map),
                new ApiCallback<BaseModel<ShopRemarkModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopRemarkModel> model) {
                        mvpView.getShopRemarkSuccess(model);
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

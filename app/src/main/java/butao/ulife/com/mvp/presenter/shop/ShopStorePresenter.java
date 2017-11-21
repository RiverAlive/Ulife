package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.view.shop.ShopStoreView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopStorePresenter extends BasePresenter<ShopStoreView> {
    public ShopStorePresenter(ShopStoreView view){
        attachView(view);
    }
    /**
     * 门店信息
     */
    public void loadShopStore(String storeId ){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("storeId", storeId);
        addSubscription(apiServices.loadShopStore(map),
                new ApiCallback<BaseModel<ShopStoreModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopStoreModel> model) {
                        mvpView.getShopStoreSuccess(model);
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

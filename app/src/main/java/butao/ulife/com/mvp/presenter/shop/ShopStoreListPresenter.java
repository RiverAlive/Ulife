package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopListModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.view.shop.ShopStoreListView;
import butao.ulife.com.mvp.view.shop.ShopStoreView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopStoreListPresenter extends BasePresenter<ShopStoreListView> {
    public ShopStoreListPresenter(ShopStoreListView view){
        attachView(view);
    }
    /**
     * 门店列表
     */
    public void loadShopStoreList(String type,int pageNum ){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("type", type);
        map.put("pageNum", pageNum+"");
        map.put("pageize", "10");
        addSubscription(apiServices.loadShopList(map),
                new ApiCallback<BaseModel<ShopListModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopListModel> model) {
                        mvpView.getShopStoreListSuccess(model);
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

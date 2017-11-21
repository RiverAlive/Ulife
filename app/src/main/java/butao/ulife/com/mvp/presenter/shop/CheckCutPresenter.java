package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.view.shop.CheckCutView;
import butao.ulife.com.mvp.view.shop.ShopStoreView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class CheckCutPresenter extends BasePresenter<CheckCutView> {
    public CheckCutPresenter(CheckCutView view){
        attachView(view);
    }
    /**
     * 门店信息
     */
    public void loadCheckCut(String storeId ,String price){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("userId", CApplication.getIntstance().getLoginId());
        map.put("price", price);
        addSubscription(apiServices.loadCheckCut(map),
                new ApiCallback<BaseModel<Map<String,String>>>() {
                    @Override
                    public void onSuccess(BaseModel<Map<String,String>> model) {
                        mvpView.getCheckCutSuccess(model);
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
     * 门店信息
     */
    public void loadcheckFirstCut(String storeId){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("userId", CApplication.getIntstance().getLoginId());
        addSubscription(apiServices.loadcheckFirstCut(map),
                new ApiCallback<BaseModel<Map<String,String>>>() {
                    @Override
                    public void onSuccess(BaseModel<Map<String,String>> model) {
                        mvpView.getCheckFirstCutSuccess(model);
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

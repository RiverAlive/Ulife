package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.product.ProductModel;
import butao.ulife.com.mvp.view.shop.ProductsView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ProductPresenter extends BasePresenter<ProductsView> {
    public ProductPresenter(ProductsView view){
        attachView(view);
    }
    /**
     * 商品列表
     */
    public void loadPWare(String storeId){
//        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("storeId", storeId);
        addSubscription(apiServices.loadProduct(map),
                new ApiCallback<BaseModel<ProductModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ProductModel> model) {
                        mvpView.getProductListSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
//                        mvpView.hideLoading();
                    }
                });
    }

}

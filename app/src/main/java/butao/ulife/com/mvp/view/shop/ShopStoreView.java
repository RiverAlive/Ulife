package butao.ulife.com.mvp.view.shop;


import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface ShopStoreView extends BaseView {
    void getShopStoreSuccess(BaseModel<ShopStoreModel> model);
    void getFail(String model);
}

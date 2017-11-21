package butao.ulife.com.mvp.view.shop;


import java.util.Map;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopDetailModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface ShopDetailView extends BaseView {
    void getShopDetailSuccess(BaseModel<ShopDetailModel> model);
    void getFail(String model);
}

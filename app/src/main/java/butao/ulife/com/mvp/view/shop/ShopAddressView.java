package butao.ulife.com.mvp.view.shop;


import java.util.Map;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopAddressModel;
import butao.ulife.com.mvp.model.shop.ShopEvaModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface ShopAddressView extends BaseView {
    void getShopAddressSuccess(BaseModel<ShopAddressModel> model);
    void getAddAddressSuccess(BaseModel model);
    void getCreateOrderSuccess(BaseModel<Map<String,String>> model);
    void getFail(String model);
}

package butao.ulife.com.mvp.view.shop;


import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.MyOrderModel;
import butao.ulife.com.mvp.model.shop.product.ProductModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface MyOrderView extends BaseView {
    void getMyOrderListSuccess(BaseModel<MyOrderModel> model);
    void getOrderRefundSuccess(BaseModel model);
    void getOrderRemindSuccess(BaseModel model);
    void CompleteSuccess(BaseModel model);
    void getFail(String model);
}

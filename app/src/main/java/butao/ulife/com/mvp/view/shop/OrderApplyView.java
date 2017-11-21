package butao.ulife.com.mvp.view.shop;


import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.pay.alipay.Alipay;
import butao.ulife.com.pay.simcpux.WXAlipay;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface OrderApplyView extends BaseView {
    void getOrderApplySuccess(BaseModel<Alipay> model);
    void getWXOrderApplySuccess(BaseModel<WXAlipay> model);
    void getFail(String model);
}

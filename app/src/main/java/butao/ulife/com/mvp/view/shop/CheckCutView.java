package butao.ulife.com.mvp.view.shop;


import java.util.Map;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface CheckCutView extends BaseView {
    void getCheckCutSuccess(BaseModel<Map<String,String>> model);
    void getCheckFirstCutSuccess(BaseModel<Map<String,String>> model);
    void getFail(String model);
}

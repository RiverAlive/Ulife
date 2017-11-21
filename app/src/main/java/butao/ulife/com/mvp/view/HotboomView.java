package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.WareBaseModel;
import butao.ulife.com.mvp.model.WareDetailModel;
import butao.ulife.com.mvp.model.WareModel;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface HotboomView extends BaseView {
    void getHblistSuccess(BaseModel<WareModel> model);
    void getHbWareSuccess(BaseModel<WareBaseModel> model);
    void getHbDetailsuccess(BaseModel<WareDetailModel> model);
    void getFail(String msg);
}

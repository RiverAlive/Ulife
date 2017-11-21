package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface VisaExView extends BaseView{
    void getVisaExSuccess(BaseModel<VisaExModel> model);
    void getVisaExDetailSuccess(BaseModel<VisaExDetailModel> model);
    void getFail(String msg);
}

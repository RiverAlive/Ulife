package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MessgaeModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface HouseView extends BaseView{
    void getHouseSuccess(BaseModel<VisaExModel> model);
    void getHouseDetailSuccess(BaseModel<VisaExDetailModel> model);
    void getMessageSuccess(BaseModel<MessgaeModel> model);
    void getFail(String msg);
}

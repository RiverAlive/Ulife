package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.IdleWareModel;
import butao.ulife.com.mvp.model.WareInfoModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface WareView extends BaseView{
    void getWareSuccess(BaseModel<WareInfoModel> model);
    void getFail(String msg);
    void getLikeSuccess(BaseModel model);
    void getColletSuccess(BaseModel model);
}

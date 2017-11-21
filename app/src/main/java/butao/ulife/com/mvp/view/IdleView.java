package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.IdleWareModel;
import butao.ulife.com.mvp.model.MainModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface IdleView extends BaseView{
    void getClassSuccess(BaseModel<IdleClassModel> model);
    void getIdleSuccess(BaseModel<IdleWareModel> model);
    void getFail(String msg);
    void getLikeSuccess(BaseModel model);
}

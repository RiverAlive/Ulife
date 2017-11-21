package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.MainModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface FBView extends BaseView{
    void getClassSuccess(BaseModel<IdleClassModel> model);
    void getIMGSuccess(BaseModel<FBImgModel> model);
    void getWareuccess(BaseModel model);
    void getFail(String msg);
}

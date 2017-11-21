package butao.ulife.com.mvp.view;

import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;

/**
 * 创建时间 ：2017/7/19.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface HouseTravelView {
    void getClassSuccess(BaseModel<IdleClassModel> model);
    void getFail(String msg);
}

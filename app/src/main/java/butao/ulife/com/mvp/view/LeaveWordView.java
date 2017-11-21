package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface LeaveWordView extends BaseView{
    void getLWSuccess(BaseModel model);
    void getFail(String msg);
}

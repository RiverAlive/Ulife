package butao.ulife.com.mvp.view;

import java.util.List;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.LoginModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface CitiFxView extends BaseView{
    void getCitiFxSuccess(BaseModel<CitiFXModel> model);
    void getFail(String msg);
}

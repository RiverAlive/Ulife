package butao.ulife.com.mvp.view;

import java.util.List;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MainModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface MainView extends BaseView{
    void getMainSuccess(BaseModel<MainModel> model);
    void getFail(String msg);
}

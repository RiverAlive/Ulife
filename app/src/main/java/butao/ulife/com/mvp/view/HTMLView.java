package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.HTMLModel;
import butao.ulife.com.mvp.model.WareModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface HTMLView extends BaseView{
    void getHTMLListSuccess(BaseModel<HTMLModel> model);
    void getFail(String msg);
}

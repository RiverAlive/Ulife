package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.HTMLDetailModel;
import butao.ulife.com.mvp.model.HTMLModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface HTMLDetailView extends BaseView{
    void getHTMLSuccess(BaseModel<HTMLDetailModel> model);
    void getFail(String msg);
}

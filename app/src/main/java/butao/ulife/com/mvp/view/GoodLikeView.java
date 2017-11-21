package butao.ulife.com.mvp.view;

import butao.ulife.com.base.BaseView;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.GoodLikeModel;
import butao.ulife.com.mvp.model.MyIssueModel;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface GoodLikeView extends BaseView{
    void getGoodSuccess(BaseModel<GoodLikeModel> model);
    void getLikeSuccess(BaseModel<GoodLikeModel> model);
    void getFail(String msg);
}

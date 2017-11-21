package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.MyIssueModel;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.MyIssueView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyIssuePresenter extends BasePresenter<MyIssueView> {
    public MyIssuePresenter(MyIssueView view){
        attachView(view);
    }
    /**
     * 我的发布
     */
    public void loadMyIssue(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadMyIssue(map),
                new ApiCallback<BaseModel<MyIssueModel>>() {
                    @Override
                    public void onSuccess(BaseModel<MyIssueModel> model) {
                        mvpView.getMyIssueSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getFail(msg);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }


}

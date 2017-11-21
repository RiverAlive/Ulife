package butao.ulife.com.mvp.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.LoginModel;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.LoginView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class CitiFxPresenter extends BasePresenter<CitiFxView> {
    public CitiFxPresenter(CitiFxView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadCitiFx(){
        mvpView.showLoading();
        addSubscription(apiServices.loadCitiFx(),
                new ApiCallback<BaseModel<CitiFXModel>>() {
                    @Override
                    public void onSuccess(BaseModel<CitiFXModel> model) {
                        mvpView.getCitiFxSuccess(model);
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

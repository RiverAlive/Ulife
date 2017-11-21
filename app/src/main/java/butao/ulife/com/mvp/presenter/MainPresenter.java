package butao.ulife.com.mvp.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.view.MainView;
import butao.ulife.com.retrofit.ApiCallback;
import okhttp3.RequestBody;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MainPresenter extends BasePresenter<MainView > {
    public MainPresenter(MainView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadMainData(){
        mvpView.showLoading();
        addSubscription(apiServices.loadMainData(),
                new ApiCallback<BaseModel<MainModel>>() {
                    @Override
                    public void onSuccess(BaseModel<MainModel> model) {
                        mvpView.getMainSuccess(model);
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

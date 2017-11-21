package butao.ulife.com.mvp.presenter;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.Introduce;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.IntroduceView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class IntroducePresenter extends BasePresenter<IntroduceView> {
    public IntroducePresenter(IntroduceView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadIntroduce(String id){
        mvpView.showLoading();
        Map<String,String> map =new HashMap<>();
        map.put("id",id);
        addSubscription(apiServices.loadIntroduce(map),
                new ApiCallback<BaseModel<Introduce>>() {
                    @Override
                    public void onSuccess(BaseModel<Introduce> model) {
                        mvpView.getIntroduceSuccess(model);
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

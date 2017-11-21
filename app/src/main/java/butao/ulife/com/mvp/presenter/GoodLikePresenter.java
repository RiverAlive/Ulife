package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.GoodLikeModel;
import butao.ulife.com.mvp.model.MyIssueModel;
import butao.ulife.com.mvp.view.GoodLikeView;
import butao.ulife.com.mvp.view.MyIssueView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class GoodLikePresenter extends BasePresenter<GoodLikeView> {
    public GoodLikePresenter(GoodLikeView view){
        attachView(view);
    }
    /**
     * 我的点赞
     */
    public void loadMYGood(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadMYGood(map),
                new ApiCallback<BaseModel<GoodLikeModel>>() {
                    @Override
                    public void onSuccess(BaseModel<GoodLikeModel> model) {
                        mvpView.getGoodSuccess(model);
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
    /**
     * 我的收藏
     */
    public void loadMyLike(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadMyLike(map),
                new ApiCallback<BaseModel<GoodLikeModel>>() {
                    @Override
                    public void onSuccess(BaseModel<GoodLikeModel> model) {
                        mvpView.getLikeSuccess(model);
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

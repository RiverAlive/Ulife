package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.HTMLDetailModel;
import butao.ulife.com.mvp.model.HTMLModel;
import butao.ulife.com.mvp.view.HTMLDetailView;
import butao.ulife.com.mvp.view.HTMLView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HTMLDetailPresenter extends BasePresenter<HTMLDetailView> {
    public HTMLDetailPresenter(HTMLDetailView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadHTMLDetail(String wareId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("wareId",wareId);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadHTMLDetail(map),
                new ApiCallback<BaseModel<HTMLDetailModel>>() {
                    @Override
                    public void onSuccess(BaseModel<HTMLDetailModel> model) {
                        mvpView.getHTMLSuccess(model);
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

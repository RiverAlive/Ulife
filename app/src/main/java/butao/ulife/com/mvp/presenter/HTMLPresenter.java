package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.HTMLModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.view.EXCitiFxView;
import butao.ulife.com.mvp.view.HTMLView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HTMLPresenter extends BasePresenter<HTMLView> {
    public HTMLPresenter(HTMLView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadHTMLList(String cid){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadHTMLList(map),
                new ApiCallback<BaseModel<HTMLModel>>() {
                    @Override
                    public void onSuccess(BaseModel<HTMLModel> model) {
                        mvpView.getHTMLListSuccess(model);
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

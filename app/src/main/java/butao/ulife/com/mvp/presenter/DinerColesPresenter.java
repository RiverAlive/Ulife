package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.DinerColesModel;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.DinerColesView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DinerColesPresenter extends BasePresenter<DinerColesView> {
    public DinerColesPresenter(DinerColesView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadDinerColes(String cid,int pagenum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        map.put("pageNum",pagenum+"");
        map.put("pageSize","10");
        addSubscription(apiServices.loadDinerColes(map),
                new ApiCallback<BaseModel<DinerColesModel>>() {
                    @Override
                    public void onSuccess(BaseModel<DinerColesModel> model) {
                        mvpView.getDinerColesSuccess(model);
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

package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.EXCitiFxView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class EXCitiFxPresenter extends BasePresenter<EXCitiFxView> {
    public EXCitiFxPresenter(EXCitiFxView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadEXCitiFx(String cid){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadEXCitiFx(map),
                new ApiCallback<BaseModel<WareModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareModel> model) {
                        mvpView.getEXCitiFxSuccess(model);
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

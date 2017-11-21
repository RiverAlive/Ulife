package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.view.CitiFxView;
import butao.ulife.com.mvp.view.VisaExView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class VisaExPresenter extends BasePresenter<VisaExView> {
    public VisaExPresenter(VisaExView view){
        attachView(view);
    }
    /**
     * 升学、快递列表
     */
    public void loadVisaEx(String cid){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadVisaEx(map),
                new ApiCallback<BaseModel<VisaExModel>>() {
                    @Override
                    public void onSuccess(BaseModel<VisaExModel> model) {
                        mvpView.getVisaExSuccess(model);
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
     * 升学、快递列表item详情
     */
    public void loadVisaExDetail(String wareId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("wareId",wareId);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadVisaExDetail(map),
                new ApiCallback<BaseModel<VisaExDetailModel>>() {
                    @Override
                    public void onSuccess(BaseModel<VisaExDetailModel> model) {
                        mvpView.getVisaExDetailSuccess(model);
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

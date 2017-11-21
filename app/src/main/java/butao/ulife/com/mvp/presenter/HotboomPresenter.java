package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.WareBaseModel;
import butao.ulife.com.mvp.model.WareDetailModel;
import butao.ulife.com.mvp.model.WareInfoModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.view.HotboomView;
import butao.ulife.com.mvp.view.WareView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HotboomPresenter extends BasePresenter<HotboomView> {
    public HotboomPresenter(HotboomView view){
        attachView(view);
    }
    /**
     * 代购商家
     */
    public void loadHotboom(String cid){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadHotboom(map),
                new ApiCallback<BaseModel<WareModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareModel> model) {
                        mvpView.getHblistSuccess(model);
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
     * 代购商品
     */
    public void loadHBWareList(String objId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("objId",objId);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadHBWareList(map),
                new ApiCallback<BaseModel<WareBaseModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareBaseModel> model) {
                        mvpView.getHbWareSuccess(model);
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
     * 代购商品详情
     */
    public void loadHBWareDetail(String id){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("id",id);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadHBWareDetail(map),
                new ApiCallback<BaseModel<WareDetailModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareDetailModel> model) {
                        mvpView.getHbDetailsuccess(model);
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

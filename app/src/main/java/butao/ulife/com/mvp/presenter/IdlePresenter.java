package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.IdleWareModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.view.IdleView;
import butao.ulife.com.mvp.view.MainView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class IdlePresenter extends BasePresenter<IdleView > {
    public IdlePresenter(IdleView view){
        attachView(view);
    }
    /**
     * 分类
     */
    public void loadClassData(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid","1013");
        addSubscription(apiServices.loadIdleClass(map),
                new ApiCallback<BaseModel<IdleClassModel>>() {
                    @Override
                    public void onSuccess(BaseModel<IdleClassModel> model) {
                        mvpView.getClassSuccess(model);
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
     * 商品
     */
    public void loadWareData(String cid,int pagenum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        map.put("pageNum",pagenum+"");
        map.put("pageSize","10");
        addSubscription(apiServices.loadIdleWare(map),
                new ApiCallback<BaseModel<IdleWareModel>>() {
                    @Override
                    public void onSuccess(BaseModel<IdleWareModel> model) {
                        mvpView.getIdleSuccess(model);
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
     * 分类
     */
    public void loadWareLIke(String id,String wareId,String Cid){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("cid",Cid);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        if(!TextUtils.isEmpty(id)){
            map.put("id",id);
        }
        map.put("wareId",wareId);
        addSubscription(apiServices.loadWareLike(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
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

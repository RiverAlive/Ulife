package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.IdleWareModel;
import butao.ulife.com.mvp.model.WareInfoModel;
import butao.ulife.com.mvp.view.IdleView;
import butao.ulife.com.mvp.view.WareView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WarePresenter extends BasePresenter<WareView> {
    public WarePresenter(WareView view){
        attachView(view);
    }
    /**
     * 商品
     */
    public void loadWareData(String wareId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("wareId",wareId);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadWareData(map),
                new ApiCallback<BaseModel<WareInfoModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareInfoModel> model) {
                        mvpView.getWareSuccess(model);
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
     * 喜欢
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
    /**
     * 收藏
     */
    public void loadWareCollect(String id,String wareId,String Cid){
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
        addSubscription(apiServices.loadWareCollect(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getColletSuccess(model);
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

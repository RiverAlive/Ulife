package butao.ulife.com.mvp.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MessgaeModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.model.WareInfoModel;
import butao.ulife.com.mvp.view.DinerDetailView;
import butao.ulife.com.mvp.view.HouseView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DinerDetailPresenter extends BasePresenter<DinerDetailView> {
    public DinerDetailPresenter(DinerDetailView view){
        attachView(view);
    }
    /**
     * 超市餐馆详情
     */
    public void loadDinerColesDetail(String wareId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("wareId",wareId);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        addSubscription(apiServices.loadDinerColesDetail(map),
                new ApiCallback<BaseModel<WareInfoModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareInfoModel> model) {
                        mvpView.getDinerDetailSuccess(model);
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
     * 评价
     */
    public void loadMessageList(String wareId,int pagenum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("wareId",wareId);
        if(!TextUtils.isEmpty(CApplication.getIntstance().getLoginId())){
            map.put("userId",CApplication.getIntstance().getLoginId());
        }
        map.put("pageNum",pagenum+"");
        map.put("pageSize","10");
        addSubscription(apiServices.loadMessageList(map),
                new ApiCallback<BaseModel<MessgaeModel>>() {
                    @Override
                    public void onSuccess(BaseModel<MessgaeModel> model) {
                        mvpView.getMessageSuccess(model);
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

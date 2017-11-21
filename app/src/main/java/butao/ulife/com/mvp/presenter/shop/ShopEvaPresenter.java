package butao.ulife.com.mvp.presenter.shop;


import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopEvaModel;
import butao.ulife.com.mvp.view.shop.ShopEvaView;
import butao.ulife.com.retrofit.ApiCallback;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopEvaPresenter extends BasePresenter<ShopEvaView> {
    public ShopEvaPresenter(ShopEvaView view){
        attachView(view);
    }
    /**
     * 评论列表
     * 1.storeId:门店ID
     */
    public void loadShopEvas(int pageNum,String storeId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("pageNum",pageNum+"");
        map.put("pageSize","10");
        map.put("storeId", storeId);
        addSubscription(apiServices.loadShopEvas(map),
                new ApiCallback<BaseModel<ShopEvaModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopEvaModel> model) {
                        mvpView.getShopEvasSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

    /**
     * 点赞
     * 1.userId:用户ID
     2.reviewerId：评论ID
     */
    public void loadDianzan(String reviewerId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userId",CApplication.getIntstance().getLoginId());
        map.put("reviewerId",reviewerId);
        addSubscription(apiServices.loadDianzan(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getDianzanSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

    /**
     * 点赞
     *1.userId:用户ID
     2.userName：用户名称
     3.storeId：门店ID
     4.score：打分
     5.remark：内容
     6.imgUrl：头像
     */
    public void loadReviewerEva(String storeId,String remark,String score ){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("imgUrl",CApplication.getIntstance().getLoginIMG());
        map.put("userId",CApplication.getIntstance().getLoginId());
        map.put("storeId",storeId);
        map.put("remark",remark);
        map.put("userName",CApplication.getIntstance().getLoginName());
        map.put("score",score);
        addSubscription(apiServices.loadReviewerEva(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getReviewerEvaSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }
}

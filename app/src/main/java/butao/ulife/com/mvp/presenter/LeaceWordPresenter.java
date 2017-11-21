package butao.ulife.com.mvp.presenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.view.FBView;
import butao.ulife.com.mvp.view.LeaveWordView;
import butao.ulife.com.retrofit.ApiCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class LeaceWordPresenter extends BasePresenter<LeaveWordView> {
    public LeaceWordPresenter(LeaveWordView view){
        attachView(view);
    }
    /**
     * 留言
     */
    public void loadPushLW(String remark,String userId,String wareId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("remark",remark);
        map.put("userId",userId);
        map.put("wareId",wareId);
        addSubscription(apiServices.loadPushLW(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getLWSuccess(model);
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

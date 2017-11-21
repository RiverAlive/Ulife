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
import butao.ulife.com.mvp.view.HouseTravelView;
import butao.ulife.com.retrofit.ApiCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HouseTravelPresenter extends BasePresenter<HouseTravelView> {
    public HouseTravelPresenter(HouseTravelView view){
        attachView(view);
    }
    /**
     * 分类
     */
    public void loadClassData(String cid){
        Map<String,String > map = new HashMap<>();
        map.put("cid",cid);
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
                    }
                });
    }
}

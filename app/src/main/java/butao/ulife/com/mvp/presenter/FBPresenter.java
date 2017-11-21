package butao.ulife.com.mvp.presenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.view.FBView;
import butao.ulife.com.mvp.view.MainView;
import butao.ulife.com.retrofit.ApiCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class FBPresenter extends BasePresenter<FBView> {
    public FBPresenter(FBView view){
        attachView(view);
    }
    /**
     * 上传图片
     */
    public void loadIMG(File file){
        mvpView.showLoading();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile",file.getName(),requestBody);
        addSubscription(apiServices.loadPushImg(body),
                new ApiCallback<BaseModel<FBImgModel>>() {
                    @Override
                    public void onSuccess(BaseModel<FBImgModel> model) {
                        mvpView.getIMGSuccess(model);
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
    public void loadClassData(){
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
                    }
                });
    }

    /**
     * 分类
     */
    public void loadWareInfo(String title,String content,String num,String price,String phone,String cid,String imgIds,String imgId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("title",title);
        map.put("address","");
        map.put("zipcode","");
        map.put("phone",phone);
        map.put("wxcode","");
        map.put("remark",content);
        map.put("price",price);
        map.put("wareNum",num);
        map.put("cid",cid);
        map.put("mainImg",imgId);
        map.put("imgIds",imgIds);
        map.put("userId", CApplication.getIntstance().getLoginId());
        addSubscription(apiServices.loadWareInfo(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getWareuccess(model);
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

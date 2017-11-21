package butao.ulife.com.mvp.presenter;

import java.util.HashMap;
import java.util.Map;

import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.LoginModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.view.LoginView;
import butao.ulife.com.mvp.view.MainView;
import butao.ulife.com.retrofit.ApiCallback;
import butao.ulife.com.retrofit.ApiPost;
import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView view){
        attachView(view);
    }
    /**
     * 活动列表
     */
    public void loadLogin(String type,String uId,String accesstoken,String imgPath,String name){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("location",type);
        map.put("uid",uId);
        map.put("accessToken",accesstoken);
        map.put("imgPath",imgPath);
        map.put("name",name);
        addSubscription(apiServices.loadLogin(map),
                new ApiCallback<BaseModel<LoginModel>>() {
                    @Override
                    public void onSuccess(BaseModel<LoginModel> model) {
                        mvpView.getLoginSuccess(model);
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

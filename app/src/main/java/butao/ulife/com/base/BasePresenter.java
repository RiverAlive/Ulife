package butao.ulife.com.base;


import butao.ulife.com.mvp.api.ApiHeaderServices;
import butao.ulife.com.mvp.api.ApiServices;
import butao.ulife.com.retrofit.AppClient;
import butao.ulife.com.retrofit.AppHeadClient;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 *业务基础类
 */
public class BasePresenter<V> {
    public V mvpView;
    protected ApiServices apiServices;
    protected ApiHeaderServices apiHeadServices;
    private CompositeSubscription mCompositeSubscription;

    /**
     * 不带头请求
     * @param mvpView
     */
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiServices = AppClient.retrofit().create(ApiServices.class);
    }
    /**
     * 带头请求
     * @param mvpView
     */
    public void attachHearderView(V mvpView) {
        this.mvpView = mvpView;
        apiHeadServices = AppHeadClient.Hearder().create(ApiHeaderServices.class);
    }

    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}

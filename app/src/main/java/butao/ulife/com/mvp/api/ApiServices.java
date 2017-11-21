package butao.ulife.com.mvp.api;


import java.util.List;
import java.util.Map;

import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.CitiFXModel;
import butao.ulife.com.mvp.model.DinerColesModel;
import butao.ulife.com.mvp.model.DirverModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.GoodLikeModel;
import butao.ulife.com.mvp.model.HTMLDetailModel;
import butao.ulife.com.mvp.model.HTMLModel;
import butao.ulife.com.mvp.model.HomeImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.IdleWareModel;
import butao.ulife.com.mvp.model.Introduce;
import butao.ulife.com.mvp.model.LoginModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.model.MessgaeModel;
import butao.ulife.com.mvp.model.MyIssueModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.model.WareBaseModel;
import butao.ulife.com.mvp.model.WareDetailModel;
import butao.ulife.com.mvp.model.WareInfoModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.model.shop.MyOrderModel;
import butao.ulife.com.mvp.model.shop.ShopAddressModel;
import butao.ulife.com.mvp.model.shop.ShopDetailModel;
import butao.ulife.com.mvp.model.shop.ShopEvaModel;
import butao.ulife.com.mvp.model.shop.ShopListModel;
import butao.ulife.com.mvp.model.shop.ShopRemarkModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.model.shop.product.ProductModel;
import butao.ulife.com.pay.alipay.Alipay;
import butao.ulife.com.pay.simcpux.WXAlipay;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 *不带头请求的请求
 */
public interface ApiServices {



    @GET("homeData.do")//首页数据
    Observable<BaseModel<MainModel>> loadMainData();

    @GET("social_login.do")//登录
    Observable<BaseModel<LoginModel>> loadLogin(@QueryMap Map<String, String> map);

    @GET("getCategoryByCid.do")//闲置分类
    Observable<BaseModel<IdleClassModel>> loadIdleClass(@QueryMap Map<String, String> map);

    @GET("homeNextData.do")//闲置分类数据
    Observable<BaseModel<IdleWareModel>> loadIdleWare(@QueryMap Map<String, String> map);
    @Multipart
    @POST("upload_file.do")//闲置分类数据
    Observable<BaseModel<FBImgModel>> loadPushImg(@Part  MultipartBody.Part imgValue);

    @POST("add_ware.do")//闲置分类数据
    Observable<BaseModel> loadWareInfo(@QueryMap Map<String, String> map);


    @POST("add_my_like.do")//喜欢
    Observable<BaseModel> loadWareCollect(@QueryMap Map<String, String> map);

    @POST("add_good.do")//收藏
    Observable<BaseModel> loadWareLike(@QueryMap Map<String, String> map);

    @GET("ware_info.do")//详情
    Observable<BaseModel<WareInfoModel>> loadWareData(@QueryMap Map<String, String> map);

    @POST("add_message.do")//
    Observable<BaseModel> loadPushLW(@QueryMap Map<String, String> map);

    @GET("currency.do")//外汇
    Observable<BaseModel<CitiFXModel>> loadCitiFx();

    @GET("homeNextData.do")//外汇
    Observable<BaseModel<WareModel>> loadEXCitiFx(@QueryMap Map<String, String> map);

    @GET("homeNextData.do")//外汇
    Observable<BaseModel<HTMLModel>> loadHTMLList(@QueryMap Map<String, String> map);

    @GET("ware_info.do")//外汇详情
    Observable<BaseModel<HTMLDetailModel>> loadHTMLDetail(@QueryMap Map<String, String> map);

    @GET("homeNextData.do")//代购
    Observable<BaseModel<WareModel>> loadHotboom(@QueryMap Map<String, String> map);

    @GET("ware_list.do")//代购商品
    Observable<BaseModel<WareBaseModel>> loadHBWareList(@QueryMap Map<String, String> map);

    @GET("purchase_info.do")//代购商品详情
    Observable<BaseModel<WareDetailModel>> loadHBWareDetail(@QueryMap Map<String, String> map);

    @GET("homeNextData.do")//升学。快递、住房
    Observable<BaseModel<VisaExModel>> loadVisaEx(@QueryMap Map<String, String> map);


    @GET("ware_info.do")//升学。快递详情、住房
    Observable<BaseModel<VisaExDetailModel>> loadVisaExDetail(@QueryMap Map<String, String> map);

    @POST("visaInfo.do")//升学。快递介绍
    Observable<BaseModel<Introduce>> loadIntroduce(@QueryMap Map<String, String> map);

    @POST("message_list.do")//评价
    Observable<BaseModel<MessgaeModel>> loadMessageList(@QueryMap Map<String, String> map);

    @GET("drivers.do")//住房
    Observable<BaseModel<DirverModel>> loadDirvers(@QueryMap Map<String, String> map);

    @GET("get_my_wares.do")//我的发布
    Observable<BaseModel<MyIssueModel>> loadMyIssue(@QueryMap Map<String, String> map);

    @GET("my_good.do")//我的点赞
    Observable<BaseModel<GoodLikeModel>> loadMYGood(@QueryMap Map<String, String> map);

    @GET("my_like.do")//我的收藏
    Observable<BaseModel<GoodLikeModel>> loadMyLike(@QueryMap Map<String, String> map);

    @GET("homeNextData.do")//超市餐馆
    Observable<BaseModel<DinerColesModel>> loadDinerColes(@QueryMap Map<String, String> map);

    @GET("ware_info.do")//超市餐馆详情
    Observable<BaseModel<WareInfoModel>> loadDinerColesDetail(@QueryMap Map<String, String> map);

    @GET("homeImgInfo.do")//轮播图详情
    Observable<BaseModel<HomeImgModel>> loadHomeImgInfo(@QueryMap Map<String, String> map);

    @POST("storeWare.do")//商品列表
    Observable<BaseModel<ProductModel>> loadProduct(@QueryMap Map<String, String> map);

    @POST("storeInfo.do")//门店信息
    Observable<BaseModel<ShopStoreModel>> loadShopStore(@QueryMap Map<String, String> map);

     @POST("storeList.do")//门店信息
    Observable<BaseModel<ShopListModel>> loadShopList(@QueryMap Map<String, String> map);

    @POST("reviewerList.do")//用户评论列表
    Observable<BaseModel<ShopEvaModel>> loadShopEvas(@QueryMap Map<String, String> map);


    @POST("userReviewerLike.do")//用户點贊
    Observable<BaseModel> loadDianzan(@QueryMap Map<String, String> map);

    @POST("userReviewer.do")//35.用户评价
    Observable<BaseModel> loadReviewerEva(@QueryMap Map<String, String> map);

    @POST("checkCut.do")//优惠
    Observable<BaseModel<Map<String,String>>> loadCheckCut(@QueryMap Map<String, String> map);

    @POST("checkFirstCut.do")//优惠
    Observable<BaseModel<Map<String,String>>> loadcheckFirstCut(@QueryMap Map<String, String> map);

    @POST("storeBaseInfo.do")//门店信息详情
    Observable<BaseModel<ShopDetailModel>> loadShopDetail(@QueryMap Map<String, String> map);

    @POST("addressList.do")//41.收货地址列表
    Observable<BaseModel<ShopAddressModel>> loadShopAddresssList(@QueryMap Map<String, String> map);

    @POST("addressInsert.do")//收货地址添加修改
    Observable<BaseModel> loadShopAddresss(@QueryMap Map<String, String> map);

    @POST("json/createOrder.do")//订单创建
    Observable<BaseModel<Map<String,String>>> loadCreateOrder(@Body RequestBody map);

     @POST("order_apply.do")//支付接口
    Observable<BaseModel<Alipay>> loadOrderApply(@QueryMap Map<String, String> map);

    @POST("order_apply.do")//微信支付接口
    Observable<BaseModel<WXAlipay>> loadWXOrderApply(@QueryMap Map<String, String> map);

    @POST("userOrders.do")//27.用户版订单列表
    Observable<BaseModel<MyOrderModel>> loadMYOrderList(@QueryMap Map<String, String> map);

    @POST("orderUrge.do")//27.用户版订单催单/orderUrge.do   参数，orderId,userId
    Observable<BaseModel> loadOrderRemind (@QueryMap Map<String, String> map);

    @POST("refundApply.do")//27.用户版订单退款
    Observable<BaseModel> loadOrderRefund(@QueryMap Map<String, String> map);

    @POST("upOrderStatus.do")//27.用户版 订单完成
    Observable<BaseModel> loadOrderComplete(@QueryMap Map<String, String> map);

    @POST("userHistoryNotes.do")//顾客要求备注
    Observable<BaseModel<ShopRemarkModel>> loadShopRemark(@QueryMap Map<String, String> map);
}

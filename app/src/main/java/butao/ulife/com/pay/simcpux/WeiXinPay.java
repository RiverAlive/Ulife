package butao.ulife.com.pay.simcpux;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付
 * Created by bodong on 2016/10/20.
 */
public class WeiXinPay {
    Activity mActivity;
    public WXAlipay alipay;
    IWXAPI msgApi;
    public  WeiXinPay(Activity activity){
        mActivity = activity;
        alipay = new WXAlipay();
    }



    public void WeiXinpay(WXAlipay alipay) {
        this.alipay = alipay;
        msgApi = WXAPIFactory.createWXAPI(mActivity, null);
        if (!msgApi.isWXAppInstalled()) {
            Toast.makeText(mActivity, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
//        Toast.makeText(mActivity, "获取订单中...", Toast.LENGTH_SHORT).show();
        try{
            if (alipay != null) {
                if(null != alipay.getPayInfo() ){
                    if(!TextUtils.isEmpty(alipay.getPayInfo().getPrepayid())){
                        PayReq req = new PayReq();
                        req.appId			= alipay.getPayInfo().getAppid();
                        req.partnerId		= alipay.getPayInfo().getPartnerid();
                        req.prepayId		= alipay.getPayInfo().getPrepayid();
                        req.nonceStr		= alipay.getPayInfo().getNoncestr();
                        req.timeStamp		= alipay.getPayInfo().getTimestamp();
                        req.packageValue	= alipay.getPayInfo().getPackageValue();
                        req.sign			= alipay.getPayInfo().getSign();
                        req.extData			= "app data"; // optional
//                        Toast.makeText(mActivity, "正常调起支付,请稍等", Toast.LENGTH_SHORT).show();
                        msgApi.registerApp(req.appId);
                        msgApi.sendReq(req);
                    }else{
                        Log.d("PAY_GET", "返回错误"+alipay.getPayInfo().getRetmsg());
                        Toast.makeText(mActivity, "返回错误"+"预支付Id为空", Toast.LENGTH_SHORT).show();
                    }
//                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                }else{
                    Log.d("PAY_GET", "返回错误"+alipay.getPayInfo().getRetmsg());
                    Toast.makeText(mActivity, "返回错误"+alipay.getPayInfo().getRetmsg(), Toast.LENGTH_SHORT).show();
                }
            }else{
                Log.d("PAY_GET", "服务器请求错误");
                Toast.makeText(mActivity, "服务器请求错误", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Log.e("PAY_GET", "异常："+e.getMessage());
            Toast.makeText(mActivity, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

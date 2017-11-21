package butao.ulife.com.pay.alipay;

import android.app.Activity;
import butao.ulife.com.base.ActivityManager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import butao.ulife.com.mvp.activity.My.MyOrderActivity;
import butao.ulife.com.mvp.activity.shop.ConfirmOrderActivity;
import butao.ulife.com.mvp.activity.shop.ShopAddressActivity;
import butao.ulife.com.mvp.activity.shop.ShopDetailActivity;
import butao.ulife.com.mvp.activity.shop.ShopStoreActivity;
import butao.ulife.com.mvp.activity.shop.ShopStoreListActivity;


/**
 * 支付宝支付
 * Created by bodong on 2016/10/19.
 */
public class AlipayConstant {
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private Activity context;
    private Alipay alipay;

    public AlipayConstant(Activity context) {
        this.context = context;
        alipay = new Alipay();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Result payResult = new Result((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.result;

                    String resultStatus = payResult.resultStatus;
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                      ActivityManager.getInstance().finishActivity();
                        ActivityManager.getInstance().finishActivity(ShopAddressActivity.class);
                        ActivityManager.getInstance().finishActivity(ShopStoreActivity.class);
                       ActivityManager.getInstance().finishActivity(ShopStoreListActivity.class);
                       ActivityManager.getInstance().finishActivity(ConfirmOrderActivity.class);
                        ActivityManager.getInstance().finishActivity(ShopDetailActivity.class);
                        context.startActivity(new Intent(context, MyOrderActivity.class));
                        Toast.makeText(context, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(context, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(context, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void alipypay(final Alipay alipays) {
        alipay = alipays;
        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = alipay.getPayInfo();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}

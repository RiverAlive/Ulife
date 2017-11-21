package butao.ulife.com.wxapi;







import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butao.ulife.com.R;
import butao.ulife.com.base.ActivityManager;
import butao.ulife.com.mvp.activity.My.MyOrderActivity;
import butao.ulife.com.mvp.activity.shop.ConfirmOrderActivity;
import butao.ulife.com.mvp.activity.shop.ShopAddressActivity;
import butao.ulife.com.mvp.activity.shop.ShopDetailActivity;
import butao.ulife.com.mvp.activity.shop.ShopStoreActivity;
import butao.ulife.com.mvp.activity.shop.ShopStoreListActivity;
import butao.ulife.com.pay.simcpux.Constants;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				finish();
				ActivityManager.getInstance().finishActivity();
				ActivityManager.getInstance().finishActivity(ShopDetailActivity.class);
				ActivityManager.getInstance().finishActivity(ShopAddressActivity.class);
				ActivityManager.getInstance().finishActivity(ShopStoreActivity.class);
				ActivityManager.getInstance().finishActivity(ShopStoreListActivity.class);
				ActivityManager.getInstance().finishActivity(ConfirmOrderActivity.class);
				startActivity(new Intent(WXPayEntryActivity.this, MyOrderActivity.class));
				Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				finish();
				Toast.makeText(this, "用户取消支付", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				finish();
				Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
				break;
			case -1:
				finish();
				Toast.makeText(this, "可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。", Toast.LENGTH_LONG).show();
				break;
			default:
				break;

		}
	}
}
package butao.ulife.com.wxapi;

import android.content.Intent;
import android.widget.Toast;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

public class WXEntryActivity extends WechatHandlerActivity {

//	private static final String APP_SECRET = "a21f91c679b61aeb2c1d2feb039aab56";
//	private IWXAPI mWeixinAPI;
//	public static final String WEIXIN_APP_ID = "wxf1659725a0b924da";
//	private static String uuid;
//	LoginModel loginModel = new LoginModel();
//	@Override
//	protected BasePresenter createPresenter() {
//		return null;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		mWeixinAPI = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
//		mWeixinAPI.handleIntent(this.getIntent(), this);
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		setIntent(intent);
//		mWeixinAPI.handleIntent(intent, this);//必须调用此句话
//	}
//
//	//微信发送的请求将回调到onReq方法
//	@Override
//	public void onReq(BaseReq req) {
//	}
//	//发送到微信请求的响应结果
//	@Override
//	public void onResp(BaseResp resp) {
//		LogUtils.d("onResp");
//		switch (resp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				LogUtils.d("ERR_OK");
//				//发送成功
//				SendAuth.Resp sendResp = (SendAuth.Resp) resp;
//				if (sendResp != null) {
//					String code = sendResp.code;
//					getAccess_token(code);
//				}
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				LogUtils.d("ERR_USER_CANCEL");
//				//发送取消
//				break;
//			case BaseResp.ErrCode.ERR_AUTH_DENIED:
//				LogUtils.d("ERR_AUTH_DENIED");
//				//发送被拒绝
//				break;
//			default:
//				//发送返回
//				break;
//		}
//
//	}
//
//	/**
//	 * 获取openid accessToken值用于后期操作
//	 * @param code 请求码
//	 */
//	private void getAccess_token(final String code) {
//		String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
//				+ WEIXIN_APP_ID
//				+ "&secret="
//				+ APP_SECRET
//				+ "&code="
//				+ code
//				+ "&grant_type=authorization_code";
//		LogUtils.d("getAccess_token：" + path);
//		OkHttpUtils.get()
//				.url(path)
//				.build()
//				.execute(new StringCallback() {
//					@Override
//					public void onError(com.squareup.okhttp.Request request, Exception e) {
//
//					}
//
//					@Override
//					public void onResponse(String response) {
//						WeiXinModel weiXinModel = 	 JsonUtil.fromJson(response,WeiXinModel.class);
//						String openid = weiXinModel.getOpenid();
//						String access_token =  weiXinModel.getAccess_token();
//						getUserMesg(access_token, openid);
//					}
//				});
//	}
//
//
//	/**
//	 * 获取微信的个人信息
//	 * @param access_token
//	 * @param openid
//	 */
//	private void getUserMesg(final String access_token, final String openid) {
//		String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
//				+ access_token
//				+ "&openid="
//				+ openid;
//		LogUtils.d("getUserMesg：" + path);
//		OkHttpUtils.get()
//				.url(path)
//				.build()
//				.execute(new StringCallback() {
//					@Override
//					public void onError(com.squareup.okhttp.Request request, Exception e) {
//
//					}
//
//					@Override
//					public void onResponse(String response) {
//						WXInfo wxInfo = 	 JsonUtil.fromJson(response,WXInfo.class);
//						String nickname = wxInfo.getNickname();
//						String headimgurl = wxInfo.getHeadimgurl();
//						LoginActivity.loginActivity.WXLogin(nickname,openid,access_token,headimgurl);
//						finish();
//					}
//				});
//	}
	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
		startActivity(iLaunchMyself);
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	 * 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}
}
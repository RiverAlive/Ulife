package butao.ulife.com.mvp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.CApplication;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.jpush.chatting.ChatActivity;
import butao.ulife.com.jpush.chatting.utils.DialogCreator;
import butao.ulife.com.jpush.chatting.utils.HandleResponseCode;
import butao.ulife.com.jpush.chatting.utils.SharePreferenceManager;
import butao.ulife.com.jpush.database.UserEntry;
import butao.ulife.com.jpush.entity.Event;
import butao.ulife.com.jpush.entity.EventType;
import butao.ulife.com.jpush.utils.BitMap;
import butao.ulife.com.mvp.dialog.PhoneDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.WareInfoModel;
import butao.ulife.com.mvp.presenter.WarePresenter;
import butao.ulife.com.mvp.view.WareView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.RecyclerViewBanner;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;

/**
 * 创建时间 ：2017/7/14.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WareDetailActivity extends MvpActivity<WarePresenter> implements WareView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.rv_banner)
    RecyclerViewBanner rvBanner;//轮播图
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.txt_price)
    TextView txtPrice;
    @Bind(R.id.img_like)
    ImageView imgLike;
    @Bind(R.id.img_collet)
    ImageView imgCollet;
    @Bind(R.id.txt_num)
    TextView txtNum;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    WareInfoModel wareInfoModel;
    @Bind(R.id.txt_remark)
    TextView txtRemark;
    private String mUsername;
    private String mAppKey;
    private UserInfo mToUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        String wareId = bundle.getString("wareId");
        mvpPresenter.loadWareData(wareId);
    }

    @Override
    protected WarePresenter createPresenter() {
        return new WarePresenter(this);
    }

    @Override
    public void getWareSuccess(BaseModel<WareInfoModel> model) {
        if ("200".equals(model.getCode())) {
            wareInfoModel = model.getData();
            initViewData();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }


    @Override
    public void getLikeSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            if (wareInfoModel != null) {
                if (wareInfoModel.getWareInfo() != null) {
                    if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getLikeCheck())) {
                        if ("-1".equals(wareInfoModel.getWareInfo().getLikeCheck())) {
                            wareInfoModel.getWareInfo().setLikeCheck("1");
                            imgLike.setImageResource(R.mipmap.like2);
                        } else {
                            wareInfoModel.getWareInfo().setLikeCheck("-1");
                            imgLike.setImageResource(R.mipmap.like1);
                        }
                    }
                }}
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getColletSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            if (wareInfoModel != null) {
                if (wareInfoModel.getWareInfo() != null) {
                    if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getGoodCheck())) {
                        if ("-1".equals(wareInfoModel.getWareInfo().getGoodCheck())) {
                            wareInfoModel.getWareInfo().setGoodCheck("1");
                            imgCollet.setImageResource(R.mipmap.shoucang2);
                        } else {
                            wareInfoModel.getWareInfo().setGoodCheck("-1");
                            imgCollet.setImageResource(R.mipmap.shoucang1);
                        }
                    }
                }}
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }


    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    public void initViewData() {
        if (wareInfoModel != null) {
            if (wareInfoModel.getWareInfo() != null) {
                rvBanner.setIndicatorInterval(3000);
                if(!JsonUtil.isEmpty(wareInfoModel.getWareInfo().getPaths())) {
                    LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - 80, (ScreenUtils.getScreenWidth() - 80));
                   rvBannerparams.setMargins(0,10,0,0);
                    rvBanner.setLayoutParams(rvBannerparams);
                    rvBanner.setRvBannerData(wareInfoModel.getWareInfo().getPaths());
                    rvBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                        @Override
                        public void switchBanner(int position, AppCompatImageView bannerView) {
                            bannerView.setScaleType(ImageView.ScaleType.FIT_XY);
                            if(wareInfoModel.getWareInfo().getPaths().size()>0)
                                if(!TextUtils.isEmpty(wareInfoModel.getWareInfo().getPaths().get(position).getPath())){
                                    Glide.with(bannerView.getContext())
                                            .load(wareInfoModel.getWareInfo().getPaths().get(position).getPath())
                                            .placeholder(R.mipmap.zhanweitu)
                                            .into(bannerView);
                                }else{
                                    rvBanner.setVisibility(View.GONE);
                                }
                        }
                    });
                }else{
                    rvBanner.setVisibility(View.GONE);
                }
//                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getPath())) {
//                    Glide.with(WareDetailActivity.this).load(wareInfoModel.getWareInfo().getPath()).placeholder(R.mipmap.zhanweitu).into(imgWare);
//                }else{
//                    imgWare.setBackgroundResource(R.mipmap.zhanweitu);
//                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getTitle())) {
                    txtName.setText("产品:" + wareInfoModel.getWareInfo().getTitle());
                } else {
                    txtName.setText("");
                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getPrice())) {
                    txtPrice.setText("价格:" + wareInfoModel.getWareInfo().getPrice());
                } else {
                    txtPrice.setText("");
                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getPhone())) {
                    txtPhone.setText(wareInfoModel.getWareInfo().getPhone());
                } else {
                    txtPhone.setText("");

                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getWareNum())) {
                    txtNum.setText("数量:" +wareInfoModel.getWareInfo().getWareNum());
                } else {
                    txtNum.setText("");

                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getRemark())) {
                    txtRemark.setText("" + wareInfoModel.getWareInfo().getRemark());
                } else {
                    txtRemark.setText("");
                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getGoodCheck())) {
                    if ("-1".equals(wareInfoModel.getWareInfo().getGoodCheck())) {
                        imgCollet.setImageResource(R.mipmap.shoucang1);
                    } else {
                        imgCollet.setImageResource(R.mipmap.shoucang2);
                    }
                }
                if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getLikeCheck())) {
                    if ("-1".equals(wareInfoModel.getWareInfo().getLikeCheck())) {
                        imgLike.setImageResource(R.mipmap.like1);
                    } else {
                        imgLike.setImageResource(R.mipmap.like2);
                    }
                }
            }
        }

    }

    @OnClick({R.id.ll_back, R.id.img_like, R.id.img_collet, R.id.txt_phone, R.id.txt_liuyan, R.id.img_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.img_like:
                if (wareInfoModel != null) {
                    if (wareInfoModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getLikeCheck())) {
                            if ("-1".equals(wareInfoModel.getWareInfo().getLikeCheck())) {
                                mvpPresenter.loadWareLIke("", wareInfoModel.getWareInfo().getId(), wareInfoModel.getWareInfo().getCid());
                            } else {
                                mvpPresenter.loadWareLIke("1",wareInfoModel.getWareInfo().getId(), wareInfoModel.getWareInfo().getCid());
                            }
                        }
                    }}
                break;
            case R.id.img_collet:
                if (wareInfoModel != null) {
                    if (wareInfoModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getGoodCheck())) {
                            if ("-1".equals(wareInfoModel.getWareInfo().getGoodCheck())) {
                                mvpPresenter.loadWareCollect("", wareInfoModel.getWareInfo().getId(), wareInfoModel.getWareInfo().getCid());
                            } else {
                                mvpPresenter.loadWareCollect("1",wareInfoModel.getWareInfo().getId(), wareInfoModel.getWareInfo().getCid());
                            }
                        }
                    }}
                break;
            case R.id.txt_phone:
                if (wareInfoModel != null) {
                    if (wareInfoModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getPhone())) {
                            PhoneDialog phoneDialog = new PhoneDialog(WareDetailActivity.this, wareInfoModel.getWareInfo().getPhone());
                            phoneDialog.dialogShow();
                        }
                    }}
                break;
            case R.id.txt_liuyan:
                if (wareInfoModel != null) {
                    if (wareInfoModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getId())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("wareId",wareInfoModel.getWareInfo().getId());
                            gotoActivity(WareDetailActivity.this,LeaveWordActivity.class,bundle);
                        }
                    }}
                break;
            case R.id.img_message:
                if(cApplication.isLogin()){
                    if (!TextUtils.isEmpty(wareInfoModel.getWareInfo().getUid())) {
                        inModule();
                    }else{
                        ToastUtils.showShortToast("本店暂时不可私信");
                    }
                }else{
                    gotoActivity(WareDetailActivity.this,LoginActivity.class);
                }
                break;
        }
    }
    private void inModule() {
        JMessageClient.getUserInfo(wareInfoModel.getWareInfo().getUid(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String desc, UserInfo userInfo) {
                if (status == 0) {
                    JMessageClient.getUserInfo(userInfo.getUserName(), userInfo.getAppKey(), new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int status, String desc, final UserInfo userInfo) {
                            if (status == 0) {
                                mToUserInfo = userInfo;
                                Intent intentChat = new Intent(WareDetailActivity.this, ChatActivity.class);
                                String title = mToUserInfo.getNotename();
                                if (TextUtils.isEmpty(title)) {
                                    title = mToUserInfo.getNickname();
                                    if (TextUtils.isEmpty(title)) {
                                        title = mToUserInfo.getUserName();
                                    }
                                }
                                Conversation conv = JMessageClient.getSingleConversation(mToUserInfo.getUserName(), mToUserInfo.getAppKey());
                                //如果会话为空，使用EventBus通知会话列表添加新会话
                                if (conv == null) {
                                    conv = Conversation.createSingleConversation(mToUserInfo.getUserName(), mToUserInfo.getAppKey());
                                    EventBus.getDefault().post(new Event.Builder()
                                            .setType(EventType.createConversation)
                                            .setConversation(conv)
                                            .build());
                                }
                                intentChat.putExtra(CApplication.CONV_TITLE, title);
                                intentChat.putExtra(CApplication.TARGET_ID, mToUserInfo.getUserName());
                                intentChat.putExtra(CApplication.TARGET_APP_KEY, mToUserInfo.getAppKey());
                                startActivity(intentChat);
                            } else {
                                HandleResponseCode.onHandle(WareDetailActivity.this, status, false);
                            }
                        }
                    });
                } else {
                    if(status==871300){
                        juphLogin(cApplication.getLoginUID(),cApplication.getLoginUID());
                    }else{
                        HandleResponseCode.onHandle(WareDetailActivity.this, status, false);
                    }
                }
            }
        });
    }
    public void juphLogin(String userId, String password) {
        JMessageClient.login(userId, password, new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                if (status == 0) {
                    String username = JMessageClient.getMyInfo().getUserName();
                    String appKey = JMessageClient.getMyInfo().getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    inModule();
                } else {
                    hideLoading();
                }
            }
        });
    }


}

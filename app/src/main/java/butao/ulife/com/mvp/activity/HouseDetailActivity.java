package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.ClassUtils.MessageChat;
import butao.ulife.com.mvp.dialog.PhoneDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MessgaeModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.presenter.HousePresenter;
import butao.ulife.com.mvp.presenter.VisaExPresenter;
import butao.ulife.com.mvp.view.HouseView;
import butao.ulife.com.mvp.view.VisaExView;
import butao.ulife.com.util.LocationUtils;
import butao.ulife.com.view.LoadMoreView;
import butao.ulife.com.view.recyclerview.RecyclerViewBanner;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HouseDetailActivity extends MvpActivity<HousePresenter> implements HouseView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.rv_banner)
    RecyclerViewBanner rvBanner;
    @Bind(R.id.txt_address)
    TextView txtAddress;
    @Bind(R.id.txt_code)
    TextView txtCode;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    @Bind(R.id.txt_wx)
    TextView txtWx;
    @Bind(R.id.txt_remark)
    TextView txtRemark;
    VisaExDetailModel visaExDetailModel = new VisaExDetailModel();
    @Bind(R.id.ry_message)
    RecyclerView ryMessage;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    int pagenum=0;
    String wareId="";
    MessgaeModel messgaeModel = new MessgaeModel();
    CommonRecycleAdapter commonRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadVisaExDetail(bundle.getString("wareId"));
        wareId =bundle.getString("wareId");
        mvpPresenter.loadMessageList(bundle.getString("wareId"),pagenum);
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        txtRightTitle.setText("更多信息");
        LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), (ScreenUtils.getScreenWidth() * 40) / 75 + 40);
        rvBanner.setLayoutParams(rvBannerparams);
        rvBanner.setIndicatorInterval(3000);
        initView();
    }

    @Override
    protected HousePresenter createPresenter() {
        return new HousePresenter(this);
    }

    /**
     * 初始化界面
     */
    public void initView() {
        SinaRefreshView headerView = new SinaRefreshView(HouseDetailActivity.this);
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(HouseDetailActivity.this);
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pagenum=0;
                mvpPresenter.loadMessageList(wareId,pagenum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if(messgaeModel.getList().size()>9){
                    pagenum ++;
                    mvpPresenter.loadMessageList(wareId,pagenum);
                }else{
                    refreshLayout.finishLoadmore();
                    ToastUtils.showShortToast("暂无更多数据");
                }
            }
        });
    }
    /**
     * 此界面用不到
     *
     * @param model
     */
    @Override
    public void getHouseSuccess(BaseModel<VisaExModel> model) {

    }

    @Override
    public void getMessageSuccess(BaseModel<MessgaeModel> model) {
        if ("200".equals(model.getCode())) {
            messgaeModel= model.getData();
            initRYView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getHouseDetailSuccess(BaseModel<VisaExDetailModel> model) {
        if ("200".equals(model.getCode())) {
            visaExDetailModel = model.getData();
            if (visaExDetailModel != null) {
                if (visaExDetailModel.getWareInfo() != null) {
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getAddress())) {
                        txtAddress.setText("地址:" + visaExDetailModel.getWareInfo().getAddress());
                    } else {
                        txtAddress.setText("地址:");
                    }
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getZipcode())) {
                        txtCode.setText("邮编:" + visaExDetailModel.getWareInfo().getZipcode());
                    } else {
                        txtCode.setText("邮编:");
                    }
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getPhone())) {
                        txtPhone.setText(visaExDetailModel.getWareInfo().getPhone());
                    } else {
                        txtPhone.setText("");
                    }
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getWxcode())) {
                        txtWx.setText("微信:" + visaExDetailModel.getWareInfo().getWxcode());
                    } else {
                        txtWx.setText("微信:");
                    }
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getRemark())) {
                        txtRemark.setText(visaExDetailModel.getWareInfo().getRemark());
                    } else {
                        txtRemark.setText("");
                    }
                    if (visaExDetailModel.getWareInfo().getPaths()!=null&&visaExDetailModel.getWareInfo().getPaths().size()>0) {
                        rvBanner.setRvBannerData(visaExDetailModel.getWareInfo().getPaths());
                        rvBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                            @Override
                            public void switchBanner(int position, AppCompatImageView bannerView) {
                                bannerView.setScaleType(ImageView.ScaleType.FIT_XY);
                                if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getPaths().get(position).getPath())) {
                                    Glide.with(bannerView.getContext())
                                            .load(visaExDetailModel.getWareInfo().getPaths().get(position).getPath())
                                            .placeholder(R.mipmap.shiyu_zhanweitu)
                                            .into(bannerView);
                                } else {
                                    Glide.with(bannerView.getContext())
                                            .load(R.mipmap.shiyu_zhanweitu)
                                            .placeholder(R.mipmap.shiyu_zhanweitu)
                                            .into(bannerView);
                                }
                            }
                        });
                    }
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }


    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
        if (pagenum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    private void initRYView() {
        if (pagenum == 0) {
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(HouseDetailActivity.this);
            ryMessage.setLayoutManager(gridLayoutManager);
            commonRecycleAdapter = new CommonRecycleAdapter<MessgaeModel.MessageItem>(HouseDetailActivity.this, R.layout.adapter_message, messgaeModel.getList()) {
                @Override
                protected void convert(ViewHolder holder, final MessgaeModel.MessageItem messageItem, int position) {
                    if (messageItem != null) {
                        if(messageItem.getSocialLogin()!=null) {
                            if (!TextUtils.isEmpty(messageItem.getSocialLogin().getImgPath())) {
                                holder.setImageGlide(R.id.img_head, messageItem.getSocialLogin().getImgPath());
                            } else {
                                holder.setImageResource(R.id.img_head, R.mipmap.zhanweitu);
                            }
                        }
                        if (!TextUtils.isEmpty(messageItem.getRemark())) {
                            holder.setText(R.id.txt_remark,messageItem.getRemark());
                        } else {
                            holder.setText(R.id.txt_remark, "");
                        }
                    }
                }
            };
            ryMessage.setAdapter(commonRecycleAdapter);
        } else {
            commonRecycleAdapter.setupdateDatas(messgaeModel.getList());
        }
        if (pagenum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }


    @OnClick({R.id.ll_back, R.id.ll_right, R.id.txt_map, R.id.txt_phone,R.id.txt_liuyan, R.id.img_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                if (visaExDetailModel != null) {
                    if (visaExDetailModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getId())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", visaExDetailModel.getWareInfo().getPhone());
                            bundle.putString("remark", visaExDetailModel.getWareInfo().getRemark());
                            gotoActivity(HouseDetailActivity.this, MoreInfoActivity.class, bundle);
                        }
                    }
                }

                break;
            case R.id.txt_map:
                if (visaExDetailModel != null) {
                    if (visaExDetailModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getAddress())) {
                            LocationUtils.getLocation(HouseDetailActivity.this, visaExDetailModel.getWareInfo().getAddress());
                        }
                    }
                }

                break;
            case R.id.txt_phone:
                if (visaExDetailModel != null) {
                    if (visaExDetailModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getPhone())) {
                            PhoneDialog phoneDialog = new PhoneDialog(HouseDetailActivity.this, visaExDetailModel.getWareInfo().getPhone());
                            phoneDialog.dialogShow();
                        }
                    }
                }
                break;
            case R.id.txt_liuyan:
                if (visaExDetailModel != null) {
                    if (visaExDetailModel.getWareInfo() != null) {
                        if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getId())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("wareId",visaExDetailModel.getWareInfo().getId());
                            gotoActivity(HouseDetailActivity.this,LeaveWordActivity.class,bundle);
                        }
                    }
                }
                break;
            case R.id.img_message:
                if (cApplication.isLogin()) {
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getUid())) {
                        MessageChat messageChat = new MessageChat(HouseDetailActivity.this, visaExDetailModel.getWareInfo().getUid());
                        messageChat.inModule();
                    } else {
                        ToastUtils.showShortToast("本店暂时不可私信");
                    }
                } else {
                    gotoActivity(HouseDetailActivity.this, LoginActivity.class);
                }
                break;
        }
    }

}

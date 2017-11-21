package butao.ulife.com.mvp.activity;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.ClassUtils.MessageChat;
import butao.ulife.com.mvp.dialog.PhoneDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.presenter.VisaExPresenter;
import butao.ulife.com.mvp.view.VisaExView;
import butao.ulife.com.util.LocationUtils;
import butao.ulife.com.util.MapUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class VisaExDetailActivity extends MvpActivity<VisaExPresenter> implements VisaExView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.img_path)
    ImageView imgPath;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visaex_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadVisaExDetail(bundle.getString("wareId"));
        txtRightTitle.setText("介绍");
        LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), (ScreenUtils.getScreenWidth() * 40) / 75 + 40);
        imgPath.setLayoutParams(rvBannerparams);
    }

    @Override
    protected VisaExPresenter createPresenter() {
        return new VisaExPresenter(this);
    }

    /**
     * 此界面用不到
     *
     * @param model
     */
    @Override
    public void getVisaExSuccess(BaseModel<VisaExModel> model) {

    }


    @Override
    public void getVisaExDetailSuccess(BaseModel<VisaExDetailModel> model) {
        if ("200".equals(model.getCode())) {
            visaExDetailModel= model.getData();
            if(visaExDetailModel!=null){
                if(visaExDetailModel.getWareInfo()!=null){
                    if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getAddress())){
                        txtAddress.setText("地址:"+visaExDetailModel.getWareInfo().getAddress());
                    }else{
                        txtAddress.setText("地址:");
                    }
                    if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getZipcode())){
                        txtCode.setText("邮编:"+visaExDetailModel.getWareInfo().getZipcode());
                    }else{
                        txtCode.setText("邮编:");
                    }
                    if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getPhone())){
                        txtPhone.setText(visaExDetailModel.getWareInfo().getPhone());
                    }else{
                        txtPhone.setText("");
                    }
                    if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getWxcode())){
                        txtWx.setText("微信:"+visaExDetailModel.getWareInfo().getWxcode());
                    }else{
                        txtWx.setText("微信:");
                    }
                    if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getRemark())){
                        txtRemark.setText(visaExDetailModel.getWareInfo().getRemark());
                    }else{
                        txtRemark.setText("");
                    }
                    if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getPath())){
                        Glide.with(VisaExDetailActivity.this).load(visaExDetailModel.getWareInfo().getPath()).into(imgPath);
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
    }

    @OnClick({R.id.ll_back, R.id.ll_right, R.id.txt_map, R.id.txt_phone, R.id.txt_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                if(visaExDetailModel!=null){
                    if(visaExDetailModel.getWareInfo()!=null) {
                        if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getId())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", visaExDetailModel.getWareInfo().getId());
                            gotoActivity(VisaExDetailActivity.this, IntroduceActivity.class, bundle);
                        }
                    }}

                break;
            case R.id.txt_map:
                if(visaExDetailModel!=null){
                    if(visaExDetailModel.getWareInfo()!=null) {
                        if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getAddress())) {
                            LocationUtils.getLocation(VisaExDetailActivity.this,visaExDetailModel.getWareInfo().getAddress());
                        }
                    }}

                break;
            case R.id.txt_phone:
                if(visaExDetailModel!=null){
                    if(visaExDetailModel.getWareInfo()!=null){
                        if(!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getPhone())) {
                            PhoneDialog phoneDialog = new PhoneDialog(VisaExDetailActivity.this, visaExDetailModel.getWareInfo().getPhone());
                            phoneDialog.dialogShow();
                        }}
                }
                break;
            case R.id.txt_message:
                if(cApplication.isLogin()){
                    if (!TextUtils.isEmpty(visaExDetailModel.getWareInfo().getUid())) {
                        MessageChat messageChat = new MessageChat(VisaExDetailActivity.this,visaExDetailModel.getWareInfo().getUid());
                        messageChat.inModule();
                    }else{
                        ToastUtils.showShortToast("本店暂时不可私信");
                    }
                }else{
                    gotoActivity(VisaExDetailActivity.this,LoginActivity.class);
                }
                break;
        }
    }
}

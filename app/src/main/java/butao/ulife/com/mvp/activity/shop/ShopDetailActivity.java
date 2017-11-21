package butao.ulife.com.mvp.activity.shop;

import android.os.Bundle;
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
import butao.ulife.com.mvp.activity.LeaveWordActivity;
import butao.ulife.com.mvp.activity.LoginActivity;
import butao.ulife.com.mvp.dialog.PhoneDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.MessgaeModel;
import butao.ulife.com.mvp.model.WareInfoModel;
import butao.ulife.com.mvp.model.shop.ShopDetailModel;
import butao.ulife.com.mvp.presenter.DinerDetailPresenter;
import butao.ulife.com.mvp.presenter.shop.ShopDetailPresenter;
import butao.ulife.com.mvp.view.DinerDetailView;
import butao.ulife.com.mvp.view.shop.ShopDetailView;
import butao.ulife.com.util.LocationUtils;
import butao.ulife.com.view.LoadMoreView;
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
public class ShopDetailActivity extends MvpActivity<ShopDetailPresenter> implements ShopDetailView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.txt_address)
    TextView txtAddress;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    @Bind(R.id.txt_remark)
    TextView txtRemark;
    String StoreId = "";
    @Bind(R.id.img_main)
    ImageView imgMain;
    @Bind(R.id.txt_storeName)
    TextView txtStoreName;
    Bundle bundle;
    ShopDetailModel shopDetailModel = new ShopDetailModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        bundle = getIntent().getExtras();
        StoreId = bundle.getString("StoreId");
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), (ScreenUtils.getScreenWidth() * 40) / 75 + 40);
        imgMain.setLayoutParams(rvBannerparams);
        mvpPresenter.loadShopDetail(StoreId);
    }

    @Override
    protected ShopDetailPresenter createPresenter() {
        return new ShopDetailPresenter(this);
    }


    @Override
    public void getShopDetailSuccess(BaseModel<ShopDetailModel> model) {
        if ("200".equals(model.getCode())) {
            shopDetailModel = model.getData();
            if (shopDetailModel != null) {
                if (!TextUtils.isEmpty(shopDetailModel.getAddress())) {
                    txtAddress.setText( shopDetailModel.getAddress());
                } else {
                    txtAddress.setText("");
                }
                if (!TextUtils.isEmpty(shopDetailModel.getStorePhone())) {
                    txtPhone.setText(shopDetailModel.getStorePhone());
                } else {
                    txtPhone.setText("");
                }
                if (!TextUtils.isEmpty(shopDetailModel.getStoreName())) {
                    txtStoreName.setText(shopDetailModel.getStoreName());
                } else {
                    txtStoreName.setText("");
                }
                if (!TextUtils.isEmpty(shopDetailModel.getRemark())) {
                    txtRemark.setText(shopDetailModel.getRemark());
                } else {
                    txtRemark.setText("");
                }
                if (!TextUtils.isEmpty(shopDetailModel.getImgUrl())) {
                    Glide.with(ShopDetailActivity.this).load(shopDetailModel.getImgUrl()).into(imgMain);
                }
            }
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }



    @OnClick({R.id.ll_back, R.id.txt_storeInto,  R.id.txt_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_storeInto:
                gotoActivity(ShopDetailActivity.this,ShopStoreActivity.class,bundle);
                break;
            case R.id.txt_phone:
                if (shopDetailModel != null) {
                    if (!TextUtils.isEmpty(shopDetailModel.getStorePhone())) {
                        PhoneDialog phoneDialog = new PhoneDialog(ShopDetailActivity.this,shopDetailModel.getStorePhone());
                        phoneDialog.dialogShow();
                    }
                }
                break;
        }
    }

}

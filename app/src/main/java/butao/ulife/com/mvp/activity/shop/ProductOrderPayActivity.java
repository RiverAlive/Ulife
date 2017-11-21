package butao.ulife.com.mvp.activity.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.dialog.PayTrueDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.SubOrderModel;
import butao.ulife.com.mvp.presenter.shop.OrderApplyPresenter;
import butao.ulife.com.mvp.view.shop.OrderApplyView;
import butao.ulife.com.pay.alipay.Alipay;
import butao.ulife.com.pay.alipay.AlipayConstant;
import butao.ulife.com.pay.simcpux.WXAlipay;
import butao.ulife.com.pay.simcpux.WeiXinPay;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/22.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ProductOrderPayActivity extends MvpActivity<OrderApplyPresenter> implements OrderApplyView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    @Bind(R.id.img_alipay)
    ImageView imgAlipay;
    @Bind(R.id.img_weixin)
    ImageView imgWeixin;
    String channel = "", RMBprice = "", orderId = "";
    SubOrderModel subOrderModel = new SubOrderModel();
    AlipayConstant alipayConstant;
    WeiXinPay weiXinPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order_pay);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("支付订单");
        channel = "ALIPAY";
        alipayConstant = new AlipayConstant(ProductOrderPayActivity.this);
        weiXinPay = new WeiXinPay(ProductOrderPayActivity.this);
        Bundle bundle = getIntent().getExtras();
        RMBprice = bundle.getString("RMBprice");
        orderId = bundle.getString("orderId");
        subOrderModel = JsonUtil.fromJson(bundle.getString("Json"), SubOrderModel.class);
        initRyView();
    }

    @Override
    protected OrderApplyPresenter createPresenter() {
        return new OrderApplyPresenter(this);
    }

    @Override
    public void getOrderApplySuccess(BaseModel<Alipay> model) {
        if ("200".equals(model.getCode())) {
            Alipay alipay = model.getData();
            if (alipay != null) {
                if (!TextUtils.isEmpty(alipay.getPayInfo())) {
                        alipayConstant.alipypay(alipay);
                } else {
                    ToastUtils.showShortToast("支付信息为空");
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    /**
     * 微信支付
     * @param model
     */
    @Override
    public void getWXOrderApplySuccess(BaseModel<WXAlipay> model) {
        if ("200".equals(model.getCode())) {
            WXAlipay wxAlipay = model.getData();
            if (wxAlipay != null) {
                if (wxAlipay.getPayInfo()!=null) {
                        weiXinPay.WeiXinpay(wxAlipay);
                } else {
                    ToastUtils.showShortToast("支付信息为空");
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    private void initRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductOrderPayActivity.this);
        ryWares.setLayoutManager(layoutManager);
        CommonRecycleAdapter recycleAdapter = new CommonRecycleAdapter<SubOrderModel.OrderWare>(ProductOrderPayActivity.this, R.layout.adapter_product_order, subOrderModel.getOrderWareForms()) {
            @Override
            protected void convert(ViewHolder holder, SubOrderModel.OrderWare orderWare, int position) {
                if (orderWare != null) {
                    if (!TextUtils.isEmpty(orderWare.getImgUrl())) {
                        holder.setImageRoundGlide(R.id.img_ware, orderWare.getImgUrl());
                    } else {
                        holder.setBackgroundRes(R.id.img_ware, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(orderWare.getWareName())) {
                        holder.setText(R.id.txt_warename, orderWare.getWareName());
                    } else {
                        holder.setText(R.id.txt_warename, "");
                    }
                    if (!TextUtils.isEmpty(orderWare.getWareNum())) {
                        holder.setText(R.id.txt_sell, "数量 Number: "+orderWare.getWareNum());
                    } else {
                        holder.setText(R.id.txt_sell, "");
                    }
                }
            }
        };
        ryWares.setAdapter(recycleAdapter);
    }

    @OnClick({R.id.ll_back, R.id.ll_alipay, R.id.ll_weixin,R.id.txt_affirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_alipay:
                imgAlipay.setVisibility(View.VISIBLE);
                imgWeixin.setVisibility(View.GONE);
                channel = "ALIPAY";
                break;
            case R.id.ll_weixin:
                imgAlipay.setVisibility(View.GONE);
                imgWeixin.setVisibility(View.VISIBLE);
                channel = "WECHAT";
                break;
            case R.id.txt_affirm:
                PayTrueDialog payTrueDialog = new PayTrueDialog(ProductOrderPayActivity.this);
                payTrueDialog.showDialog();
                payTrueDialog.setPayTruelistener(new PayTrueDialog.PayTruelistener() {
                    @Override
                    public void PayTrue() {
                        if ("ALIPAY".equals(channel)) {
                            mvpPresenter.loadOrderApply(orderId,channel,RMBprice);
                        } else {
                            mvpPresenter.loadWXOrderApply(orderId,channel,RMBprice);
                        }
                    }
                });
                break;
        }
    }

}

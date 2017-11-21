package butao.ulife.com.mvp.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Double4;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.dialog.DeliveryDialog;
import butao.ulife.com.mvp.dialog.OnPhotoDialog;
import butao.ulife.com.mvp.dialog.PayTrueDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.model.shop.SubOrderModel;
import butao.ulife.com.mvp.model.shop.product.ProductModel;
import butao.ulife.com.mvp.presenter.shop.CheckCutPresenter;
import butao.ulife.com.mvp.view.shop.CheckCutView;
import butao.ulife.com.util.DoubleUtil;
import butao.ulife.com.util.InputMethodUtil;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ConfirmOrderActivity extends MvpActivity<CheckCutPresenter> implements CheckCutView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    @Bind(R.id.txt_select)
    TextView txtSelect;
    @Bind(R.id.txt_orderPrice)
    TextView txtOrderPrice;
    @Bind(R.id.txt_sendPrice)
    TextView txtSendPrice;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.txt_confirm)
    TextView txtConfirm;
    @Bind(R.id.txt_remark)
    TextView txtRemark;
    List<ProductModel.WareModel> wareModels = new ArrayList<>();
    CommonRecycleAdapter recycleAdapter;
    List<ShopStoreModel.DayCutModel> dayCutModels = new ArrayList<>();
    boolean clude = false;
    String storeId = "", discountNote = "",StartPrice="0";
    String orderPrice = "", disOrderPrice = "", RMBPrice = "",sendPrice= "0",PackPrice="0",firstPrice="0";
    String orderpayprice="0",orderpayChain="0";
    final SubOrderModel subOrderModel = new SubOrderModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        Bundle bundle = getIntent().getExtras();
        wareModels.addAll(JsonUtil.getList(bundle.getString("Json"), ProductModel.WareModel.class));
        dayCutModels = JsonUtil.getList(bundle.getString("DayJson"), ShopStoreModel.DayCutModel.class);
        txtTitle.setText(bundle.getString("storename"));
        sendPrice=bundle.getString("sendPrice");
        storeId = bundle.getString("storeId");
        PackPrice = bundle.getString("PackPrice");
        txtSendPrice.setText("实付金额包含£："+sendPrice+"配送费和£"+PackPrice+"打包费");
        MathPrice();
        initRyView();
    }

    @Override
    protected CheckCutPresenter createPresenter() {
        return new CheckCutPresenter(this);
    }

    /**
     * 优惠金额 ---- 如果选择了特价商品，则不能调用此接口
     *
     * @param model
     */
    @Override
    public void getCheckCutSuccess(BaseModel<Map<String, String>> model) {
        if ("200".equals(model.getCode())) {
            Map<String, String> map = model.getData();
            if (map != null) {
                txtSelect.setText(map.get("cutStr"));
                discountNote = map.get("cutStr");
                disOrderPrice = map.get("actualPrice");
                double chinaprice = 0;
                if (cApplication.getExchangeRate() > 0) {
                    chinaprice = Double.parseDouble(disOrderPrice) * cApplication.getExchangeRate();
                }
                double ConfirmChina=0;
                if (cApplication.getExchangeRate() > 0) {
                    ConfirmChina = (Double.parseDouble(disOrderPrice) + Double.parseDouble(sendPrice) + Double.parseDouble(PackPrice)) * cApplication.getExchangeRate();
                }
                double conprice =Double.parseDouble(disOrderPrice)+Double.parseDouble(sendPrice)+Double.parseDouble(PackPrice);
                orderpayprice = String.valueOf(conprice);
                orderpayChain =DoubleUtil.floatTwo(String.valueOf(ConfirmChina>0?ConfirmChina:0));
                txtConfirm.setText("£ " + DoubleUtil.floatTwo(String.valueOf(conprice>0?conprice:0)) +" ￥"+DoubleUtil.floatTwo(String.valueOf(ConfirmChina>0?ConfirmChina:0))+ "确认订单");
                RMBPrice = DoubleUtil.floatTwo(String.valueOf(chinaprice));
                double ordprice =Double.parseDouble(disOrderPrice);
                txtOrderPrice.setText("£ " + DoubleUtil.floatTwo(String.valueOf(ordprice>0?ordprice:0)) + " = " + "￥ " + DoubleUtil.floatTwo(String.valueOf( chinaprice>0?chinaprice:0)));
            }
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 首次减免
     * @param model
     */
    @Override
    public void getCheckFirstCutSuccess(BaseModel<Map<String, String>> model) {
        if ("200".equals(model.getCode())) {
            Map<String, String> map = model.getData();
            if (map != null) {
                if(!TextUtils.isEmpty(map.get("cutStr"))) {
                    txtSelect.setText(map.get("cutStr"));
                }
                discountNote = map.get("cutStr");
                firstPrice = map.get("cutPrice");
                double chinaprice = 0;
                if (cApplication.getExchangeRate() > 0) {
                    chinaprice = (Double.parseDouble(disOrderPrice) - Double.parseDouble(firstPrice))* cApplication.getExchangeRate();
                }
                double ConfirmChina=0;
                if (cApplication.getExchangeRate() > 0) {
                    ConfirmChina = (Double.parseDouble(disOrderPrice) + Double.parseDouble(sendPrice) + Double.parseDouble(PackPrice) - Double.parseDouble(firstPrice)) * cApplication.getExchangeRate();
                }
                double conprice =(Double.parseDouble(disOrderPrice)+Double.parseDouble(sendPrice)+Double.parseDouble(PackPrice)-Double.parseDouble(firstPrice));
                orderpayprice = String.valueOf(conprice);
                orderpayChain =DoubleUtil.floatTwo(String.valueOf(ConfirmChina>0?ConfirmChina:0));
                txtConfirm.setText("£ " + DoubleUtil.floatTwo(String.valueOf(conprice>0?conprice:0)) +" ￥"+DoubleUtil.floatTwo(String.valueOf(ConfirmChina>0?ConfirmChina:0))+ "确认订单");
                RMBPrice = DoubleUtil.floatTwo(String.valueOf(chinaprice));
                double ordprice =(Double.parseDouble(disOrderPrice) - Double.parseDouble(firstPrice));
                txtOrderPrice.setText("£ " + DoubleUtil.floatTwo(String.valueOf(ordprice>0?ordprice:0)) + " = " + "￥ " + DoubleUtil.floatTwo(String.valueOf( chinaprice>0?chinaprice:0)));
            }
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    private void initRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ConfirmOrderActivity.this);
        ryWares.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<ProductModel.WareModel>(ConfirmOrderActivity.this, R.layout.adapter_confirm_product, wareModels) {
            @Override
            protected void convert(ViewHolder holder, ProductModel.WareModel wareModel, int position) {
                if (wareModel != null) {
                    if (!TextUtils.isEmpty(wareModel.getImgUrl())) {
                        holder.setImageRoundGlide(R.id.img_ware, wareModel.getImgUrl());
                    } else {
                        holder.setBackgroundRes(R.id.img_ware, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(wareModel.getName())) {
                        holder.setText(R.id.txt_wareName, wareModel.getName().replace("\\n", "\n"));
                    } else {
                        holder.setText(R.id.txt_wareName, "");
                    }
                    if (!TextUtils.isEmpty(wareModel.getDisprice())) {
                        holder.setText(R.id.txt_disPrice, "£" + wareModel.getDisprice());
                    } else {
                        holder.setText(R.id.txt_disPrice, "");
                    }
                    if (!TextUtils.isEmpty(wareModel.getPrice())) {
                        holder.setText(R.id.txt_wareprice, "£" + wareModel.getPrice());
                    } else {
                        holder.setText(R.id.txt_wareprice, "");
                    }
                    holder.setText(R.id.txt_buyNum, wareModel.getBuyNum() + "");
                    if (wareModel.isFlagTe()) {
                        double teprice = 0;
                        double price = 0;
                        if (wareModel.getBuyNum() > 1) {
                            teprice = Double.parseDouble(wareModel.getDisprice());
                            price = Double.parseDouble(wareModel.getPrice()) * (wareModel.getBuyNum() - 1);
                        } else {
                            teprice = Double.parseDouble(wareModel.getDisprice());
                        }
                        double txtprice = price + teprice;
                        double chinaprice = 0;
                        if (cApplication.getExchangeRate() > 0) {
                            chinaprice = txtprice * cApplication.getExchangeRate();
                        }
                        holder.setText(R.id.txt_totalPrice, "£ " + DoubleUtil.floatTwo(String.valueOf(txtprice)) + " = " + "￥ " + DoubleUtil.floatTwo(String.valueOf(chinaprice)));
                    } else {
                        if (!TextUtils.isEmpty(wareModel.getPrice())) {
                            double price = Double.parseDouble(wareModel.getPrice()) * wareModel.getBuyNum();
                            double chinaprice = 0;
                            if (cApplication.getExchangeRate() > 0) {
                                chinaprice = price * cApplication.getExchangeRate();
                            }
                            holder.setText(R.id.txt_totalPrice, "£ " + DoubleUtil.floatTwo(String.valueOf(price)) + " = " + "￥ " + DoubleUtil.floatTwo(String.valueOf(chinaprice)));
                        } else {
                            holder.setText(R.id.txt_totalPrice, "");
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(wareModel.getPrice())) {
                        double price = Double.parseDouble(wareModel.getPrice()) * wareModel.getBuyNum();
                        double chinaprice = 0;
                        if (cApplication.getExchangeRate() > 0) {
                            chinaprice = price * cApplication.getExchangeRate();
                        }
                        holder.setText(R.id.txt_totalPrice, "£ " + DoubleUtil.floatTwo(String.valueOf(price)) + " = " + "￥ " + DoubleUtil.floatTwo(String.valueOf(chinaprice)));
                    } else {
                        holder.setText(R.id.txt_totalPrice, "");
                    }
                }
            }
        };
        ryWares.setAdapter(recycleAdapter);
    }

    /**
     * 计算金额
     */
    private void MathPrice() {
        double totalPrice = 0;
        for (int i = 0; i < wareModels.size(); i++) {
            double teprice = 0;
            double price = 0;
            if(wareModels.get(i).isFlagTe()){
                clude = true;
                if (wareModels.get(i).getBuyNum() > 1) {
                    teprice = Double.parseDouble(wareModels.get(i).getDisprice());
                    price = Double.parseDouble(wareModels.get(i).getPrice()) * (wareModels.get(i).getBuyNum() - 1);
                } else {
                    teprice = Double.parseDouble(wareModels.get(i).getDisprice());
                }
                totalPrice = totalPrice + teprice + price;
            }else{
                if (!TextUtils.isEmpty(wareModels.get(i).getPrice())) {
                    totalPrice = totalPrice + Double.parseDouble(wareModels.get(i).getPrice()) * (wareModels.get(i).getBuyNum());
                }
            }
        }
        double chinaprice = 0;
        if (cApplication.getExchangeRate() > 0) {
            chinaprice = totalPrice * cApplication.getExchangeRate();
        }
        RMBPrice = DoubleUtil.floatTwo(String.valueOf(chinaprice));
//        txtOrderPrice.setText("£ " + DoubleUtil.floatTwo(String.valueOf(totalPrice)) + " = " + "￥ " + DoubleUtil.floatTwo(String.valueOf(chinaprice)));
        orderPrice = String.valueOf(totalPrice);
        String ConfirmChina =" ￥"+DoubleUtil.floatTwo(String.valueOf((totalPrice+Double.parseDouble(sendPrice)+Double.parseDouble(PackPrice)) * cApplication.getExchangeRate()));
//        txtConfirm.setText("£ " + DoubleUtil.floatTwo(String.valueOf(totalPrice+Double.parseDouble(sendPrice)+Double.parseDouble(PackPrice)))+ConfirmChina + "确认订单");
        if (!clude) {
            mvpPresenter.loadCheckCut(storeId, orderPrice);
        }else{
            disOrderPrice = orderPrice;
            txtSelect.setText("您已选择特价商品");
            mvpPresenter.loadcheckFirstCut(storeId);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_remark, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_remark:
                startActivityForResult(new Intent(ConfirmOrderActivity.this,ShopRemarkActivity.class),10);
                break;
            case R.id.txt_confirm:
                InputMethodUtil.hideInput(ConfirmOrderActivity.this);

//                String actualprice = String.valueOf((Double.parseDouble(disOrderPrice)+Double.parseDouble(sendPrice)+Double.parseDouble(PackPrice)));
                subOrderModel.setActualPrice(DoubleUtil.floatTwo(orderpayprice));
                subOrderModel.setTotalPriceInit(String.valueOf(Double.parseDouble(orderPrice)+Double.parseDouble(sendPrice)+Double.parseDouble(PackPrice)));
                subOrderModel.setDiscountNote(discountNote); //.discountNote：优惠描述
                double discountprice = 0;
                if (!TextUtils.isEmpty(disOrderPrice)) {
                    discountprice = Double.parseDouble(orderPrice) - Double.parseDouble(disOrderPrice);
                } else {
                    discountprice = Double.parseDouble(orderPrice);
                }
                subOrderModel.setDiscountPrice(discountprice + "");// 6.discountPrice：优惠金额
                subOrderModel.setRate(cApplication.getExchangeRate() + "");//8.rate：汇率
                subOrderModel.setRMBPrice(DoubleUtil.floatTwo(orderpayChain));//9.RMBPrice：人民币支付金额
                subOrderModel.setStoreId(storeId);// 10.storeId：门店ID
                subOrderModel.setUserId(cApplication.getLoginId());//11.userId：用户ID
                List<SubOrderModel.OrderWare> orderWares = new ArrayList<>();
                for (int i = 0; i < wareModels.size(); i++) {
                    SubOrderModel.OrderWare orderWare = new SubOrderModel.OrderWare();
                    if (wareModels.get(i).isFlagTe()) {
                        if (wareModels.get(i).getBuyNum() > 1) {
                            //特价
                            SubOrderModel.OrderWare orderWareTe = new SubOrderModel.OrderWare();
                            orderWareTe.setActualPrice(wareModels.get(i).getDisprice());
                            orderWareTe.setWareId(wareModels.get(i).getId());
                            orderWareTe.setWareName(wareModels.get(i).getName());
                            orderWareTe.setWarePrice(wareModels.get(i).getPrice());
                            orderWareTe.setImgUrl(wareModels.get(i).getImgUrl());
                            orderWareTe.setWareNum(1 + "");
                            orderWares.add(orderWareTe);
                            //无优惠
                            orderWare.setActualPrice(wareModels.get(i).getDisprice());
                            orderWare.setWareId(wareModels.get(i).getId());
                            orderWare.setWareName(wareModels.get(i).getName());
                            orderWare.setWarePrice(wareModels.get(i).getPrice());
                            orderWare.setWareNum(wareModels.get(i).getBuyNum() - 1 + "");
                            orderWare.setImgUrl(wareModels.get(i).getImgUrl());
                        } else {
                            orderWare.setActualPrice(wareModels.get(i).getDisprice());
                            orderWare.setWareId(wareModels.get(i).getId());
                            orderWare.setWareName(wareModels.get(i).getName());
                            orderWare.setWarePrice(wareModels.get(i).getPrice());
                            orderWare.setWareNum(wareModels.get(i).getBuyNum() + "");
                            orderWare.setImgUrl(wareModels.get(i).getImgUrl());
                        }
                    } else {
                        orderWare.setActualPrice(wareModels.get(i).getDisprice());
                        orderWare.setWareId(wareModels.get(i).getId());
                        orderWare.setWareName(wareModels.get(i).getName());
                        orderWare.setWarePrice(wareModels.get(i).getPrice());
                        orderWare.setWareNum(wareModels.get(i).getBuyNum() + "");
                        orderWare.setImgUrl(wareModels.get(i).getImgUrl());
                    }
                    orderWares.add(orderWare);
                }
                subOrderModel.setOrderWareForms(orderWares);
//                PayTrueDialog payTrueDialog = new PayTrueDialog(ConfirmOrderActivity.this);
//                payTrueDialog.showDialog();
//                payTrueDialog.setPayTruelistener(new PayTrueDialog.PayTruelistener() {
//                    @Override
//                    public void PayTrue() {
//
//                    }
//                });
                String phone = edtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShortToast("请输入手机号");
                    edtPhone.requestFocus();
                    return;
                }
                DeliveryDialog deliveryDialog = new DeliveryDialog(this);
                deliveryDialog.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                deliveryDialog.setDeliverylistener(new DeliveryDialog.Deliverylistener() {
                    @Override
                    public void Delivery(String sendNow) {
                        Bundle bundle = new Bundle();
                        subOrderModel.setSendNote(sendNow);
                        bundle.putString("Json", JsonUtil.toJson(subOrderModel));
                        gotoActivity(ConfirmOrderActivity.this, ShopAddressActivity.class, bundle);
                    }
                });
                break;
        }
    }
    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 10:
                if (data!=null){
                    txtRemark.setText(data.getStringExtra("remark"));
                    subOrderModel.setNote(data.getStringExtra("remark"));
                }
                break;
            default:
                break;
        }
    }
}

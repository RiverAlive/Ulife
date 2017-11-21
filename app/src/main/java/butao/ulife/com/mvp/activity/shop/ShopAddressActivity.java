package butao.ulife.com.mvp.activity.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.dialog.AddressDialog;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopAddressModel;
import butao.ulife.com.mvp.model.shop.SubOrderModel;
import butao.ulife.com.mvp.presenter.shop.ShopAddressPresenter;
import butao.ulife.com.mvp.view.shop.ShopAddressView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/26.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ShopAddressActivity extends MvpActivity<ShopAddressPresenter> implements ShopAddressView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.rb_male)
    RadioButton rbMale;
    @Bind(R.id.rb_fmale)
    RadioButton rbFmale;
    @Bind(R.id.edt_address)
    EditText edtAddress;
    @Bind(R.id.edt_postcode)
    EditText edtPostcode;
    @Bind(R.id.ry_address)
    RecyclerView ryAddress;
    ShopAddressModel shopAddressModel = new ShopAddressModel();
    int select = -1;
    SubOrderModel subOrderModel = new SubOrderModel();
    SubOrderModel subOrderModel1 = new SubOrderModel();
    String newaddress = "",phone="",zipcode="";
    List<SubOrderModel.OrderWare> orderWares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_address);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("地址选择");
        Bundle bundle = getIntent().getExtras();
        subOrderModel = JsonUtil.fromJson(bundle.getString("Json"), SubOrderModel.class);
        subOrderModel1= JsonUtil.fromJson(bundle.getString("Json"), SubOrderModel.class);
        mvpPresenter.loadShopAddresssList();
    }

    @Override
    protected ShopAddressPresenter createPresenter() {
        return new ShopAddressPresenter(this);
    }

    /**
     * 地址列表
     *
     * @param model
     */
    @Override
    public void getShopAddressSuccess(BaseModel<ShopAddressModel> model) {
        if ("200".equals(model.getCode())) {
            shopAddressModel = model.getData();
            if (shopAddressModel != null) {
                if (!JsonUtil.isEmpty(shopAddressModel.getList())) {
                    initRyView();
                }
            }
        } else {
            ToastUtils.showLongToast(model.getData().toString());
        }
    }

    /**
     * 添加地址
     *
     * @param model
     */
    @Override
    public void getAddAddressSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            subOrderModel.setAddress(newaddress);
            subOrderModel.setUserPhone(phone);
            subOrderModel.setZipcode(zipcode);
            orderWares = subOrderModel1.getOrderWareForms();
            for (int i = 0; i < subOrderModel.getOrderWareForms().size(); i++) {
                subOrderModel.getOrderWareForms().get(i).setImgUrl(null);
            }
            mvpPresenter.loadCreateOrder(subOrderModel);
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 订单创建"RMBprice": 80,
     * "orderId": 10004
     *
     * @param model
     */
    @Override
    public void getCreateOrderSuccess(BaseModel<Map<String, String>> model) {
        if ("200".equals(model.getCode())) {
            Bundle bundle = new Bundle();
            subOrderModel.setOrderWareForms(orderWares);
            bundle.putString("Json", JsonUtil.toJson(subOrderModel));
            bundle.putString("RMBprice", model.getData().get("RMBprice"));
            bundle.putString("orderId", model.getData().get("orderId"));
            gotoActivity(ShopAddressActivity.this, ProductOrderPayActivity.class, bundle);
        } else {
            ToastUtils.showLongToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }

    private void initRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopAddressActivity.this);
        ryAddress.setLayoutManager(layoutManager);
        CommonRecycleAdapter recycleAdapter = new CommonRecycleAdapter<ShopAddressModel.AddressModel>(ShopAddressActivity.this, R.layout.adapter_address, shopAddressModel.getList()) {
            @Override
            protected void convert(ViewHolder holder, ShopAddressModel.AddressModel addressModel, final int position) {
                if (addressModel != null) {
                    if (!TextUtils.isEmpty(addressModel.getAddress())) {
                        holder.setText(R.id.txt_address, addressModel.getAddress());
                    } else {
                        holder.setText(R.id.txt_address, "");
                    }
                    if (!TextUtils.isEmpty(addressModel.getName())) {
                        holder.setText(R.id.txt_name, addressModel.getName());
                    } else {
                        holder.setText(R.id.txt_name, "");
                    }
                    if (!TextUtils.isEmpty(addressModel.getPhone())) {
                        holder.setText(R.id.txt_phone, addressModel.getPhone());
                    } else {
                        holder.setText(R.id.txt_phone, "");
                    }
                    if (select == position) {
                        holder.setVisible(R.id.img_select, true);
                    } else {
                        holder.setINVisible(R.id.img_select);
                    }
                    holder.setOnClickListener(R.id.ll_address, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            select = position;
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        };
        ryAddress.setAdapter(recycleAdapter);
    }

    @OnClick({R.id.ll_back, R.id.edt_name, R.id.edt_phone, R.id.rb_male, R.id.rb_fmale, R.id.edt_address, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.edt_name:
                break;
            case R.id.edt_phone:
                break;
            case R.id.rb_male:
                break;
            case R.id.rb_fmale:
                break;
            case R.id.edt_address:
                break;
            case R.id.txt_confirm:
                if (select != -1) {
                    if (!TextUtils.isEmpty(edtName.getText().toString()) || !TextUtils.isEmpty(edtPhone.getText().toString())
                            || !TextUtils.isEmpty(edtAddress.getText().toString())||!TextUtils.isEmpty(edtPostcode.getText().toString())) {
                        AddressDialog addressDialog = new AddressDialog(ShopAddressActivity.this);
                        addressDialog.dialogShow();
                        addressDialog.setAddresslistener(new AddressDialog.Addresslistener() {
                            @Override
                            public void AddressTrue(boolean flag) {
                                if (flag) {
                                    NewAddress();
                                } else {
                                    if (shopAddressModel != null && !JsonUtil.isEmpty(shopAddressModel.getList())) {
                                        String address = shopAddressModel.getList().get(select).getAddress();
                                        subOrderModel.setAddress(address);
                                        subOrderModel.setUserPhone(shopAddressModel.getList().get(select).getPhone());
                                        subOrderModel.setZipcode(shopAddressModel.getList().get(select).getZipcode());
                                        orderWares = subOrderModel1.getOrderWareForms();
                                        for (int i = 0; i < subOrderModel.getOrderWareForms().size(); i++) {
                                            subOrderModel.getOrderWareForms().get(i).setImgUrl(null);
                                        }
                                        mvpPresenter.loadCreateOrder(subOrderModel);
                                    }
                                }
                            }
                        });
                    } else {
                        if (shopAddressModel != null && !JsonUtil.isEmpty(shopAddressModel.getList())) {
                            String address = shopAddressModel.getList().get(select).getAddress();
                            subOrderModel.setAddress(address);
                            subOrderModel.setUserPhone(shopAddressModel.getList().get(select).getPhone());
                            subOrderModel.setZipcode(shopAddressModel.getList().get(select).getZipcode());
                            orderWares = subOrderModel1.getOrderWareForms();
                            for (int i = 0; i < subOrderModel.getOrderWareForms().size(); i++) {
                                subOrderModel.getOrderWareForms().get(i).setImgUrl(null);
                            }
                            mvpPresenter.loadCreateOrder(subOrderModel);
                        }
                    }
                } else {
                    NewAddress();
                }
                break;
        }
    }

    private void NewAddress() {
        String name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        newaddress = edtAddress.getText().toString();
        zipcode = edtPostcode.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToast("请输入姓名");
            edtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShortToast("请输入手机号");
            edtPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(newaddress)) {
            ToastUtils.showShortToast("请输入新地址");
            edtAddress.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(zipcode)) {
            ToastUtils.showShortToast("请输入邮编");
            edtPostcode.requestFocus();
            return;
        }
        String sex = "";
        if (rbMale.isChecked()) {
            sex = "1";
        } else {
            sex = "0";
        }
        mvpPresenter.loadShopAddresss(newaddress, name, phone, sex,zipcode);
    }
}

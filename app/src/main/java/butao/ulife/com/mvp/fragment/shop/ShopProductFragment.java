package butao.ulife.com.mvp.fragment.shop;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpLoadFragment;
import butao.ulife.com.mvp.activity.shop.ConfirmOrderActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.model.shop.product.ProductModel;
import butao.ulife.com.mvp.presenter.shop.ProductPresenter;
import butao.ulife.com.mvp.view.shop.ProductsView;
import butao.ulife.com.util.DoubleUtil;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/4.
 * 编写人 ：bodong
 * 功能描述 ：商品
 */
public class ShopProductFragment extends MvpLoadFragment<ProductPresenter> implements ProductsView {
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    @Bind(R.id.ry_pclass)
    RecyclerView ryPclass;
    @Bind(R.id.txt_delieryfee)
    TextView txtDelieryfee;
    @Bind(R.id.txt_fooddeliery)
    TextView txtFooddeliery;
    @Bind(R.id.img_cart)
    ImageView imgCart;
    @Bind(R.id.ll_shut_up)
    LinearLayout llShutup;
    @Bind(R.id.ll_open)
    RelativeLayout llOpen;
    private String status = "";
    ProductModel productModel = new ProductModel();
    CommonRecycleAdapter classAdapter;
    CommonRecycleAdapter wareAdapter;
    List<ProductModel.PWareModel> wareModelList = new ArrayList<>();
    int classselect = 0;
    String storeId = "", sendPrice = "", startPrice = "0",Storename="",PackPrice="";
    List<ShopStoreModel.DayCutModel> dayCutModels = new ArrayList<>();
    List<ProductModel.WareModel> wareModels = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        storeId = bundle.getString("StoreId");
        sendPrice = bundle.getString("SendPrice");
        startPrice = bundle.getString("StartPrice");
        Storename = bundle.getString("Storename");
        PackPrice = bundle.getString("PackPrice");
        status= bundle.getString("status");
        dayCutModels=JsonUtil.getList(bundle.getString("Json"), ShopStoreModel.DayCutModel.class);
        txtDelieryfee.setText(sendPrice + "£");
        txtFooddeliery.setText(startPrice + "£");
        if (!TextUtils.isEmpty(status)) {
            if ("1".equals(status)) {
                imgCart.setBackgroundResource(R.mipmap.cart_yellow);
                llOpen.setBackgroundColor(getResources().getColor(R.color.login_mian));
                llShutup.setVisibility(View.GONE);
            } else {
//                imgCart.setBackgroundResource(R.mipmap.cart_hui);
//                llOpen.setBackgroundColor(getResources().getColor(R.color.bg_app));
                llOpen.setVisibility(View.GONE);
                llShutup.setVisibility(View.VISIBLE);
            }
        }else{
            llOpen.setVisibility(View.GONE);
            llShutup.setVisibility(View.VISIBLE);
        }
        initDayCut();
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_product;
    }

    @Override
    protected void lazyLoad() {
        mvpPresenter.loadPWare(storeId);
    }

    @Override
    protected ProductPresenter createPresenter() {
        return new ProductPresenter(this);
    }

    /**
     * 商品列表
     *
     * @param model
     */
    @Override
    public void getProductListSuccess(BaseModel<ProductModel> model) {
        if ("200".equals(model.getCode())) {
            productModel = model.getData();
            if(!JsonUtil.isEmpty(wareModelList)){
                wareModelList.clear();
            }
            wareModelList.addAll(productModel.getWareList());
            initClassRyView();
            initproductRyview();
        } else {
            ToastUtils.showLongToast(model.getData().getError());
        }
    }


    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }

    private void initDayCut(){
        if(!JsonUtil.isEmpty(dayCutModels)){
            ProductModel.PWareModel pWareModel = new ProductModel.PWareModel();
            pWareModel.setCid("-100");
            pWareModel.setCname("特价商品");
            List<ProductModel.WareModel> wareModels = new ArrayList<>();
            for(int i = 0;i<dayCutModels.size();i++){
                for(int j=0;j<dayCutModels.get(i).getWares().size();j++){
                    ProductModel.WareModel wareModel = new ProductModel.WareModel();
                    wareModel.setFlagTe(true);
                    wareModel.setDisprice(dayCutModels.get(i).getWares().get(j).getDiscountPrice());
                    wareModel.setName(dayCutModels.get(i).getWares().get(j).getWareName());
                    wareModel.setPrice(dayCutModels.get(i).getWares().get(j).getWarePrice());
                    wareModel.setId(dayCutModels.get(i).getWares().get(j).getWareId());
                    wareModel.setImgUrl(dayCutModels.get(i).getWares().get(j).getImgUrl());
                    wareModels.add(wareModel);
                }
            }
            pWareModel.setWares(wareModels);
            wareModelList.add(pWareModel);
        }
    }
    /**
     * 分类适配器
     */
    private void initClassRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ryPclass.setLayoutManager(layoutManager);
        classAdapter = new CommonRecycleAdapter<ProductModel.PWareModel>(getActivity(), R.layout.adapter_product_class, wareModelList) {
            @Override
            protected void convert(ViewHolder holder, ProductModel.PWareModel pWareModel, final int position) {
                if (pWareModel != null) {
                    if (!TextUtils.isEmpty(pWareModel.getCname())) {
                        holder.setText(R.id.txt_classname, pWareModel.getCname().replaceAll(" ", "").replace("\\n", "\n"));
                    } else {
                        holder.setText(R.id.txt_classname, "");
                    }
                    if (classselect == position) {
                        holder.setTextColorRes(R.id.txt_classname, R.color.login_mian);
                        holder.setVisible(R.id.view_line, true);
                    } else {
                        holder.setTextColorRes(R.id.txt_classname, R.color.content_font);
                        holder.setINVisible(R.id.view_line);
                    }
                    holder.setOnClickListener(R.id.txt_classname, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            classselect = position;
                            notifyDataSetChanged();
                            initproductRyview();
                        }
                    });
                }
            }
        };
        ryPclass.setAdapter(classAdapter);
    }

    /**
     * 商品适配器
     */
    private void initproductRyview() {
        if (!JsonUtil.isEmpty(wareModelList)) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            ryWares.setLayoutManager(linearLayoutManager);
            wareAdapter = new CommonRecycleAdapter<ProductModel.WareModel>(getActivity(), R.layout.adapter_shop_product, wareModelList.get(classselect).getWares()) {
                @Override
                protected void convert(final ViewHolder holder, final ProductModel.WareModel wareModel, final int position) {
                    if (wareModel != null) {
                        if (!TextUtils.isEmpty(wareModel.getImgUrl())) {
                            holder.setImageRoundGlide(R.id.img_ware, wareModel.getImgUrl());
                        } else {
                            holder.setBackgroundRes(R.id.img_ware, R.mipmap.zhanweitu);
                        }
                        if (!TextUtils.isEmpty(wareModel.getName())) {
                            holder.setText(R.id.txt_warename, wareModel.getName().replace("\\n", "\n"));
                        } else {
                            holder.setText(R.id.txt_warename, "");
                        }
                        if (!TextUtils.isEmpty(wareModel.getTotalSum())) {
                            holder.setText(R.id.txt_sell, "月销售" + wareModel.getTotalSum());
                        } else {
                            holder.setText(R.id.txt_sell, "");
                        }
                        if(wareModel.isFlagTe()){
                            if (!TextUtils.isEmpty(wareModel.getDisprice())) {
                                holder.setText(R.id.txt_disprice, "£" + wareModel.getDisprice());
                            } else {
                                holder.setText(R.id.txt_disprice, "");
                            }
                            if (!TextUtils.isEmpty(wareModel.getPrice())) {
                                holder.setText(R.id.txt_price, "£" + wareModel.getPrice());
                                TextView textView = holder.getView(R.id.txt_price);
                                textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//                                setFlags(Paint. STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
                            } else {
                                holder.setText(R.id.txt_price, "");
                            }
                        }else{
                            holder.setVisible(R.id.txt_disprice, false);
                            if (!TextUtils.isEmpty(wareModel.getPrice())) {
                                holder.setText(R.id.txt_price, "£" + wareModel.getPrice());
                            } else {
                                holder.setText(R.id.txt_price, "");
                            }
                        }
                        if(!TextUtils.isEmpty(status)){
                            if("1".equals(status)){
                                holder.setVisible(R.id.ll_status,true);
                            }else{
                                holder.setVisible(R.id.ll_status,false);
                            }
                        }
                        holder.setText(R.id.txt_num, wareModelList.get(classselect).getWares().get(position).getBuyNum() + "");
                        holder.setOnClickListener(R.id.txt_jia, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(wareModel.isFlagTe()){
                                    int num = wareModelList.get(classselect).getWares().get(position).getBuyNum();
                                    int buynum = num + 1;
                                    if(buynum>1) {
                                        ToastUtils.showShortToast("特价商品只能购买一个");
                                    }else{
                                        wareModelList.get(classselect).getWares().get(position).setBuyNum(1);
                                        holder.setText(R.id.txt_num, 1 + "");
                                        wareAdapter.notifyDataSetChanged();
                                    }

                                }else {
                                    int num = wareModelList.get(classselect).getWares().get(position).getBuyNum();
                                    int buynum = num + 1;
                                    wareModelList.get(classselect).getWares().get(position).setBuyNum(buynum);
                                    holder.setText(R.id.txt_num, buynum + "");
                                    wareAdapter.notifyDataSetChanged();
                                }

                            }
                        });
                        holder.setOnClickListener(R.id.txt_jian, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (wareModelList.get(classselect).getWares().get(position).getBuyNum() > 0) {
                                    int num = wareModelList.get(classselect).getWares().get(position).getBuyNum();
                                    int buynum = num - 1;
                                    wareModelList.get(classselect).getWares().get(position).setBuyNum(buynum);
                                    holder.setText(R.id.txt_num, buynum + "");
                                    wareAdapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.showShortToast("商品数量大于0");
                                }
                            }
                        });
                    }
                }
            };
            ryWares.setAdapter(wareAdapter);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.img_cart)
    public void onViewClicked() {
        if(!TextUtils.isEmpty(cApplication.getLoginId())){
            if(!TextUtils.isEmpty(status)&&"1".equals(status)) {
                wareModels = new ArrayList<>();
                if (productModel != null) {
                    if (!JsonUtil.isEmpty(wareModelList)) {
                        for (int i = 0; i < wareModelList.size(); i++) {
                            if (!JsonUtil.isEmpty(wareModelList.get(i).getWares())) {
                                for (int j = 0; j < wareModelList.get(i).getWares().size(); j++) {
                                    if (wareModelList.get(i).getWares().get(j).getBuyNum() > 0) {
                                        if(!wareModelList.get(i).getWares().get(j).isFlagTe()){
                                            wareModelList.get(i).getWares().get(j).setDisprice(wareModelList.get(i).getWares().get(j).getPrice());
                                        }
                                        wareModels.add(wareModelList.get(i).getWares().get(j));
                                    }
                                }
                            }
                        }
                    }
                }
                if (!JsonUtil.isEmpty(wareModels)) {
                    if(MAthPrice()>Double.parseDouble(startPrice)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("storename", Storename);
                        bundle.putString("storeId", storeId);
                        bundle.putString("sendPrice", sendPrice);
                        bundle.putString("PackPrice", PackPrice);
                        bundle.putString("StartPrice", startPrice);
                        bundle.putString("Json", JsonUtil.toJson(wareModels));
                        bundle.putString("DayJson", JsonUtil.toJson(dayCutModels));
                        startActivity(getActivity(), ConfirmOrderActivity.class, bundle);
                    }else{
                        ToastUtils.showShortToast("订单金额需大于起送价，请继续购买商品");
                    }
                } else {
                    ToastUtils.showShortToast("请先添加商品");
                }

            }else{
                ToastUtils.showShortToast("门店休息中");
            }
        }else{
            ToastUtils.showShortToast("请先登录");
        }
    }
    private double MAthPrice(){
        double totalPrice = 0;
        for (int i = 0; i < wareModels.size(); i++) {
            double teprice = 0;
            double price = 0;
            if(wareModels.get(i).isFlagTe()){
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
        return totalPrice;
    }
}

package butao.ulife.com.mvp.activity.shop;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.fragment.shop.ShopEvaluateFragment;
import butao.ulife.com.mvp.fragment.shop.ShopProductFragment;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopStoreModel;
import butao.ulife.com.mvp.presenter.shop.ShopStorePresenter;
import butao.ulife.com.mvp.view.shop.ShopStoreView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.NoScrollViewPager;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/21.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ShopStoreActivity extends MvpActivity<ShopStorePresenter> implements ShopStoreView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.img_store)
    ImageView imgStore;
    @Bind(R.id.txt_storename)
    TextView txtStorename;
    @Bind(R.id.txt_price_time)
    TextView txtPriceTime;
    @Bind(R.id.img_collet)
    ImageView imgCollet;
    @Bind(R.id.txt_jian)
    TextView txtJian;
    @Bind(R.id.txt_new)
    TextView txtNew;
    @Bind(R.id.txt_te)
    TextView txtTe;
    @Bind(R.id.txt_eventnum)
    TextView txtEventnum;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.txt_firstcut)
    TextView txtFirstcut;
    @Bind(R.id.txt_fulldown)
    TextView txtFulldown;
    private String[] TAB_Title = new String[]{"商品\ncommodity", "评价\nevaluate"};
    //Fragment 数组
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private MyViewPagerAdapter mAdapter;
    ShopStoreModel shopStoreModel = new ShopStoreModel();
    String timeprice = "", StoreId = "", status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_store);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("选择商品");
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        Bundle bundle = getIntent().getExtras();
        StoreId = bundle.getString("StoreId");
        status = bundle.getString("status");
        mvpPresenter.loadShopStore(bundle.getString("StoreId"));
//        initViews();
    }

    @Override
    protected ShopStorePresenter createPresenter() {
        return new ShopStorePresenter(this);
    }

    @Override
    public void getShopStoreSuccess(BaseModel<ShopStoreModel> model) {
        if ("200".equals(model.getCode())) {
            shopStoreModel = model.getData();
            if (shopStoreModel != null) {
                cApplication.setExchangeRate((float) shopStoreModel.getExchangeRate());
                if (!TextUtils.isEmpty(shopStoreModel.getImgUrl()))
                    Glide.with(ShopStoreActivity.this).load(shopStoreModel.getImgUrl()).into(imgStore);
                if (!TextUtils.isEmpty(shopStoreModel.getStoreName()))
                    txtStorename.setText(shopStoreModel.getStoreName());
                if (!TextUtils.isEmpty(shopStoreModel.getStartPrice()))
                    timeprice = "起送价£" + shopStoreModel.getStartPrice();
                if (!TextUtils.isEmpty(shopStoreModel.getSendPrice()))
                    timeprice = timeprice + "  配送费£" + shopStoreModel.getSendPrice();
                if (!TextUtils.isEmpty(shopStoreModel.getLongTime()))
                    timeprice = timeprice + "  送达时间" + shopStoreModel.getLongTime() + "min";
                txtPriceTime.setText(timeprice);
                if (shopStoreModel.getDayCuts() != null) {
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.te);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtTe.setCompoundDrawables(drawable_n, null, null, null);
                    txtTe.setCompoundDrawablePadding(5);
                } else {
                    txtTe.setVisibility(View.GONE);
                }
                if (shopStoreModel.getFullCuts() != null) {
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.jian);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtJian.setCompoundDrawables(drawable_n, null, null, null);
                    txtJian.setCompoundDrawablePadding(5);
                    String fulldown="";
                    if(!JsonUtil.isEmpty(shopStoreModel.getFullCuts().getFullCutPrices())){
                        for(int i = 0;i<shopStoreModel.getFullCuts().getFullCutPrices().size();i++){
                            if (TextUtils.isEmpty(fulldown)) {
                                fulldown  = "满£"+shopStoreModel.getFullCuts().getFullCutPrices().get(i).getFullPrice()+"减£"+shopStoreModel.getFullCuts().getFullCutPrices().get(i).getCutPrice();
                            }else{
                                fulldown  =fulldown+","+ "满£"+shopStoreModel.getFullCuts().getFullCutPrices().get(i).getFullPrice()+"减£"+shopStoreModel.getFullCuts().getFullCutPrices().get(i).getCutPrice();
                            }
                        }
                        txtFulldown.setText(fulldown);
                    }else{
                        txtFulldown.setVisibility(View.GONE);
                    }

                } else {
                    txtJian.setVisibility(View.GONE);
                    txtFulldown.setVisibility(View.GONE);
                }
                if (shopStoreModel.getFirstCutPrice() != null) {
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.img_newevent);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtNew.setCompoundDrawables(drawable_n, null, null, null);
                    txtNew.setCompoundDrawablePadding(5);
                    txtFirstcut.setText("首次购买减免£"+shopStoreModel.getFirstCutPrice());
                } else {
                    txtNew.setVisibility(View.GONE);
                    txtFirstcut.setVisibility(View.GONE);
                }
            }
        } else {
            ToastUtils.showLongToast(model.getData().toString());
        }
        initViews();
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
        initViews();
    }

    private void initViews() {
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_Title);
        initFragmentList();
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mAdapter.setLists(fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void initFragmentList() {
        if (fragments.size() > 0) {
            fragments.clear();
        }
        Bundle bundle = new Bundle();
        ShopProductFragment shopProductFragment = new ShopProductFragment();
        bundle.putString("StoreId", StoreId);
        bundle.putString("status", status);
        if (shopStoreModel != null) {
            bundle.putString("SendPrice", shopStoreModel.getSendPrice());
            bundle.putString("PackPrice", shopStoreModel.getPackPrice());
            bundle.putString("StartPrice", shopStoreModel.getStartPrice());
            bundle.putString("Storename", shopStoreModel.getStoreName());
            bundle.putString("Json", JsonUtil.toJson(shopStoreModel.getDayCuts()));
        }
        shopProductFragment.setArguments(bundle);
        ShopEvaluateFragment evaluateFragment = new ShopEvaluateFragment();//
        evaluateFragment.setArguments(bundle);
        fragments.add(shopProductFragment);
        fragments.add(evaluateFragment);
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, String[] TAB_Title) {
        for (int i = 0; i < TAB_Title.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_shop_store, null);
            tab.setCustomView(view);
            TextView txtTab = (TextView) view.findViewById(R.id.txt_title);
            txtTab.setText(TAB_Title[i]);
            tabLayout.addTab(tab);
            setIndicator(tabLayout, 40, 40);
        }
    }

    @OnClick({R.id.ll_back, R.id.img_collet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.img_collet:
                break;
        }
    }


    /**
     * @description: ViewPager 适配器
     */
    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> mList = new ArrayList<>();

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setLists(ArrayList<Fragment> lists) {
            this.mList.addAll(lists);
        }

        public void UpdateList(ArrayList<Fragment> arrayList) {
            this.mList.clear();
            this.mList.addAll(arrayList);

            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }
}

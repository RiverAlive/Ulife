package butao.ulife.com.mvp.activity;

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
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.WareBaseModel;
import butao.ulife.com.mvp.model.WareDetailModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.presenter.HotboomPresenter;
import butao.ulife.com.mvp.view.HotboomView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：代沟专栏(商品列表)
 */
public class HBWareActivity extends MvpActivity<HotboomPresenter> implements HotboomView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ry_hotboom)
    RecyclerView ryHotboom;
    CommonRecycleAdapter commonRecycleAdapter;
    WareBaseModel wareBaseModel = new WareBaseModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotboom);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadHBWareList(bundle.getString("objId"));
    }


    @Override
    protected HotboomPresenter createPresenter() {
        return new HotboomPresenter(this);
    }
    /**
     * 此界面用不到
     *
     * @param model
     */
    @Override
    public void getHblistSuccess(BaseModel<WareModel> model) {

    }




    @Override
    public void getHbWareSuccess(BaseModel<WareBaseModel> model) {
        if ("200".equals(model.getCode())) {
            wareBaseModel = model.getData();
            initView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    /**
     * 此界面用不到
     *
     * @param model
     */
    @Override
    public void getHbDetailsuccess(BaseModel<WareDetailModel> model) {

    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }


    private void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(HBWareActivity.this);
        ryHotboom.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<WareBaseModel.WareBase>(HBWareActivity.this, R.layout.adapter_hb_ware, wareBaseModel.getWareList()) {
            @Override
            protected void convert(ViewHolder holder, final WareBaseModel.WareBase ware, int position) {
                if (ware != null) {
                    if (!TextUtils.isEmpty(ware.getPath())) {
                        holder.setImageGlide(R.id.img_head, ware.getPath());
                    } else {
                        holder.setImageResource(R.id.img_head, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(ware.getName())) {
                        holder.setText(R.id.txt_name, ware.getName());
                    } else {
                        holder.setText(R.id.txt_name, "");
                    }
                    if (!TextUtils.isEmpty(ware.getPrice())) {
                        holder.setText(R.id.txt_price, "£"+ware.getPrice());
                    } else {
                        holder.setText(R.id.txt_price, "");
                    }

                }
            }
        };
        ryHotboom.setAdapter(commonRecycleAdapter);
        commonRecycleAdapter.setOnItemClickListener(ItemListener);
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    MultiItemTypeAdapter.OnItemClickListener ItemListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            if (wareBaseModel != null) {
                if (wareBaseModel.getWareList() != null && wareBaseModel.getWareList().size() > 0) {
                    if (!TextUtils.isEmpty(wareBaseModel.getWareList().get(position).getId())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", wareBaseModel.getWareList().get(position).getId());
                        gotoActivity(HBWareActivity.this, HBWareDeatilActivity.class, bundle);
                    }
                }
            }
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };
}

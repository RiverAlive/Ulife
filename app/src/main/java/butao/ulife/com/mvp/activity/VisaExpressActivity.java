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
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.presenter.VisaExPresenter;
import butao.ulife.com.mvp.view.VisaExView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：升学。快递
 */
public class VisaExpressActivity extends MvpActivity<VisaExPresenter> implements VisaExView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_hotboom)
    RecyclerView ryHotboom;
VisaExModel visaExModel = new VisaExModel();
    CommonRecycleAdapter commonRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotboom);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadVisaEx(bundle.getString("cid"));
        txtTitle.setText(bundle.getString("title"));
    }

    @Override
    protected VisaExPresenter createPresenter() {
        return new VisaExPresenter(this);
    }

    @Override
    public void getVisaExSuccess(BaseModel<VisaExModel> model) {
        if ("200".equals(model.getCode())) {
            visaExModel = model.getData();
            initView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    /**
     * 此界面用不到
     * @param model
     */
    @Override
    public void getVisaExDetailSuccess(BaseModel<VisaExDetailModel> model) {

    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    private void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(VisaExpressActivity.this);
        ryHotboom.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<VisaExModel.VisaEx>(VisaExpressActivity.this, R.layout.adapter_visaex, visaExModel.getWares()) {
            @Override
            protected void convert(ViewHolder holder, final VisaExModel.VisaEx ware, int position) {
                if (ware != null) {
                    if (!TextUtils.isEmpty(ware.getPath())) {
                        holder.setImageGlide(R.id.img_head, ware.getPath());
                    } else {
                        holder.setImageResource(R.id.img_head, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(ware.getTitle())) {
                        holder.setText(R.id.txt_name, ware.getTitle());
                    } else {
                        holder.setText(R.id.txt_name, "");
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
            if (visaExModel != null) {
                if (visaExModel.getWares() != null && visaExModel.getWares().size() > 0) {
                    if (!TextUtils.isEmpty(visaExModel.getWares().get(position).getId())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("wareId", visaExModel.getWares().get(position).getId());
                        gotoActivity(VisaExpressActivity.this, VisaExDetailActivity.class, bundle);
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

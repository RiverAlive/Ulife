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
import butao.ulife.com.mvp.model.HTMLModel;
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
 * 功能描述 ：代沟专栏
 */
public class HotboomActivity extends MvpActivity<HotboomPresenter> implements HotboomView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_hotboom)
    RecyclerView ryHotboom;
    CommonRecycleAdapter commonRecycleAdapter;
    WareModel wareModel = new WareModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotboom);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadHotboom(bundle.getString("cid"));
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
    }


    @Override
    protected HotboomPresenter createPresenter() {
        return new HotboomPresenter(this);
    }

    @Override
    public void getHblistSuccess(BaseModel<WareModel> model) {
        if ("200".equals(model.getCode())) {
            wareModel = model.getData();
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
    public void getHbWareSuccess(BaseModel<WareBaseModel> model) {

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
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(HotboomActivity.this);
        ryHotboom.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<WareModel.Ware>(HotboomActivity.this, R.layout.adapter_hotboom, wareModel.getWares()) {
            @Override
            protected void convert(ViewHolder holder, final WareModel.Ware ware, int position) {
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
            if (wareModel != null) {
                if (wareModel.getWares() != null && wareModel.getWares().size() > 0) {
                    if (!TextUtils.isEmpty(wareModel.getWares().get(position).getId())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("objId", wareModel.getWares().get(position).getId());
                        gotoActivity(HotboomActivity.this, HBWareActivity.class, bundle);
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

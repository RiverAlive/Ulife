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
import butao.ulife.com.mvp.model.DirverModel;
import butao.ulife.com.mvp.model.MessgaeModel;
import butao.ulife.com.mvp.model.VisaExDetailModel;
import butao.ulife.com.mvp.model.VisaExModel;
import butao.ulife.com.mvp.presenter.DriverPresenter;
import butao.ulife.com.mvp.presenter.HousePresenter;
import butao.ulife.com.mvp.view.DirverView;
import butao.ulife.com.mvp.view.HouseView;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/19.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DirverActivity extends MvpActivity<DriverPresenter> implements DirverView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_house)
    RecyclerView ryHouse;
    DirverModel dirverModel = new DirverModel();
    CommonRecycleAdapter commonRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirver);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadVisaEx(bundle.getString("cid"));
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
    }


    @Override
    protected DriverPresenter createPresenter() {
        return new DriverPresenter(this);
    }

    @Override
    public void getDirverSuccess(BaseModel<DirverModel> model) {
        if ("200".equals(model.getCode())) {
            dirverModel = model.getData();

            initView();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    private void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(DirverActivity.this);
        ryHouse.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<DirverModel.Dirver>(DirverActivity.this, R.layout.adapter_dirver, dirverModel.getDrivers()) {
            @Override
            protected void convert(ViewHolder holder, final DirverModel.Dirver dirver, int position) {
                if (dirver != null) {
                    if (!TextUtils.isEmpty(dirver.getPath())) {
                        holder.setImageCircleGlide(R.id.img_head, dirver.getPath());
                    } else {
                        holder.setICGlide(R.id.img_head, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(dirver.getName())) {
                        holder.setText(R.id.txt_name, dirver.getName());
                    } else {
                        holder.setText(R.id.txt_name, "");
                    }
                    if (!TextUtils.isEmpty(dirver.getSex())) {
                        holder.setText(R.id.txt_sex, "性别："+dirver.getSex());
                    } else {
                        holder.setText(R.id.txt_sex,"性别：");
                    }
                    if (!TextUtils.isEmpty(dirver.getAge())) {
                        holder.setText(R.id.txt_age, dirver.getAge());
                    } else {
                        holder.setText(R.id.txt_age, "");
                    }
                }
            }
        };
        ryHouse.setAdapter(commonRecycleAdapter);
        commonRecycleAdapter.setOnItemClickListener(ItemListener);
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    MultiItemTypeAdapter.OnItemClickListener ItemListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            if (dirverModel != null) {
                if (dirverModel.getDrivers() != null && dirverModel.getDrivers().size() > 0) {
                    if (!TextUtils.isEmpty(dirverModel.getDrivers().get(position).getId())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", JsonUtil.toJson(dirverModel.getDrivers().get(position)));
                        gotoActivity(DirverActivity.this, DirverDetailActivity.class, bundle);
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

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
import butao.ulife.com.mvp.presenter.HTMLPresenter;
import butao.ulife.com.mvp.view.HTMLView;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HTMLActivity extends MvpActivity<HTMLPresenter> implements HTMLView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ry_citifx)
    RecyclerView ryCitifx;
    CommonRecycleAdapter commonRecycleAdapter;
    HTMLModel htmlModel = new HTMLModel();
    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excitifx);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadHTMLList(bundle.getString("cid"));
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected HTMLPresenter createPresenter() {
        return new HTMLPresenter(this);
    }

    @Override
    public void getHTMLListSuccess(BaseModel<HTMLModel> model) {
        if ("200".equals(model.getCode())) {
            htmlModel = model.getData();
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
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(HTMLActivity.this);
        ryCitifx.setLayoutManager(gridLayoutManager);
        commonRecycleAdapter = new CommonRecycleAdapter<HTMLModel.HTML>(HTMLActivity.this, R.layout.adapter_html, htmlModel.getWares()) {
            @Override
            protected void convert(ViewHolder holder, final HTMLModel.HTML html, int position) {
                if (html != null) {
                    if (!TextUtils.isEmpty(html.getPath())) {
                        holder.setImageGlide(R.id.img_head, html.getPath());
                    } else {
                        holder.setImageResource(R.id.img_head, R.mipmap.zhanweitu);
                    }
                    if (!TextUtils.isEmpty(html.getTitle())) {
                        holder.setText(R.id.txt_title, html.getTitle());
                    } else {
                        holder.setText(R.id.txt_title, "");
                    }
                    if (!TextUtils.isEmpty(html.getMiaoshu())) {
                        holder.setText(R.id.txt_content, html.getMiaoshu());
                    } else {
                        holder.setText(R.id.txt_content, "");
                    }
                }
            }
        };
        ryCitifx.setAdapter(commonRecycleAdapter);
        commonRecycleAdapter.setOnItemClickListener(ItemListener);
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    MultiItemTypeAdapter.OnItemClickListener ItemListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            if (htmlModel != null) {
                if (htmlModel.getWares() != null && htmlModel.getWares().size() > 0) {
                    if (!TextUtils.isEmpty(htmlModel.getWares().get(position).getId())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("wareId", htmlModel.getWares().get(position).getId());
                        gotoActivity(HTMLActivity.this, HTMLDetailActivity.class, bundle);
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

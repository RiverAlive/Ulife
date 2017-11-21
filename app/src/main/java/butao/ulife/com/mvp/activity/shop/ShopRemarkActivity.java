package butao.ulife.com.mvp.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.shop.ShopRemarkModel;
import butao.ulife.com.mvp.presenter.shop.ShopRemarkPresenter;
import butao.ulife.com.mvp.view.shop.ShopRemarkView;
import butao.ulife.com.util.InputMethodUtil;
import butao.ulife.com.util.JsonUtil;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/10/16.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ShopRemarkActivity extends MvpActivity<ShopRemarkPresenter> implements ShopRemarkView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.edt_remark)
    EditText edtRemark;
    @Bind(R.id.ry_historynote)
    RecyclerView ryHistorynote;
    @Bind(R.id.ry_basenote)
    RecyclerView ryBasenote;
    ShopRemarkModel remarkModel = new ShopRemarkModel();
    @Bind(R.id.ll_history)
    LinearLayout llHistory;
    @Bind(R.id.ll_base)
    LinearLayout llBase;
String remark="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_remark);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("添加备注");
        txtRightTitle.setText("完成");
        mvpPresenter.loadShopRemark();
    }

    @Override
    protected ShopRemarkPresenter createPresenter() {
        return new ShopRemarkPresenter(this);
    }

    @Override
    public void getShopRemarkSuccess(BaseModel<ShopRemarkModel> model) {
        if ("200".equals(model.getCode())) {
            remarkModel = model.getData();
            if (!JsonUtil.isEmpty(remarkModel.getBaseNotes())) {
                initBaseNote();
            } else {
                llBase.setVisibility(View.GONE);
            }
            if (!JsonUtil.isEmpty(remarkModel.getHistoryNotes())) {
                initHistory();
            } else {
                llHistory.setVisibility(View.GONE);
            }
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    private void initHistory() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShopRemarkActivity.this, 3);
        ryHistorynote.setLayoutManager(gridLayoutManager);
        CommonRecycleAdapter commonRecycleAdapter = new CommonRecycleAdapter<String>(ShopRemarkActivity.this, R.layout.adapter_shop_remark, remarkModel.getHistoryNotes()) {
            @Override
            protected void convert(ViewHolder holder,final String s, int position) {
                if (!TextUtils.isEmpty(s)) {
                    holder.setText(R.id.txt_note, s);
                } else {
                    holder.setText(R.id.txt_note, "");
                }
                holder.setOnClickListener(R.id.txt_note, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(remark)){
                            remark = s;
                        }else{
                            remark=remark+","+s;
                        }
                        edtRemark.setText(remark);
                    }
                });
            }
        };
        ryHistorynote.setAdapter(commonRecycleAdapter);

    }

    private void initBaseNote() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShopRemarkActivity.this, 3);
        ryBasenote.setLayoutManager(gridLayoutManager);
        CommonRecycleAdapter commonRecycleAdapter = new CommonRecycleAdapter<String>(ShopRemarkActivity.this, R.layout.adapter_shop_remark, remarkModel.getBaseNotes()) {
            @Override
            protected void convert(ViewHolder holder,final String s, int position) {
                if (!TextUtils.isEmpty(s)) {
                    holder.setText(R.id.txt_note, s);
                } else {
                    holder.setText(R.id.txt_note, "");
                }
                holder.setOnClickListener(R.id.txt_note, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(remark)){
                            remark = s;
                        }else{
                            remark=remark+","+s;
                        }
                        edtRemark.setText(remark);
                    }
                });
            }
        };
        ryBasenote.setAdapter(commonRecycleAdapter);
    }

    @OnClick({R.id.ll_back, R.id.ll_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                setResult(0);
                finish();
                break;
            case R.id.ll_right:
                remark = edtRemark.getText().toString();
                if(!TextUtils.isEmpty(remark)){
                    Intent intent = new Intent();
                    intent.putExtra("remark",remark);
                    setResult(10,intent);
                    finish();
                    InputMethodUtil.hideInput(ShopRemarkActivity.this);
                }else{
                    edtRemark.requestFocus();
                    ToastUtils.showShortToast("请输入备注要求");
                    return;
                }
                break;
        }
    }
}

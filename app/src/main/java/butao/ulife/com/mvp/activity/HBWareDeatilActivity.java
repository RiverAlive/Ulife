package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.zzhoujay.richtext.RichText;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.WareBaseModel;
import butao.ulife.com.mvp.model.WareDetailModel;
import butao.ulife.com.mvp.model.WareModel;
import butao.ulife.com.mvp.presenter.HotboomPresenter;
import butao.ulife.com.mvp.view.HotboomView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：代沟专栏(商品列表)
 */
public class HBWareDeatilActivity extends MvpActivity<HotboomPresenter> implements HotboomView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    WareDetailModel wareDetailModel = new WareDetailModel();
    @Bind(R.id.txt_content)
    TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadHBWareDetail(bundle.getString("id"));
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


    /**
     * 此界面用不到
     *
     * @param model
     */
    @Override
    public void getHbWareSuccess(BaseModel<WareBaseModel> model) {

    }

    @Override
    public void getHbDetailsuccess(BaseModel<WareDetailModel> model) {
        if ("200".equals(model.getCode())) {
            wareDetailModel = model.getData();
            if (wareDetailModel != null) {
                if (wareDetailModel.getPurchaseInfo() != null) {
                    if (!TextUtils.isEmpty(wareDetailModel.getPurchaseInfo().getRemark())) {
                        RichText.fromHtml(wareDetailModel.getPurchaseInfo().getRemark()).into(txtContent);
                    }
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }


    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

}

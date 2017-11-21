package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.HTMLDetailModel;
import butao.ulife.com.mvp.presenter.HTMLDetailPresenter;
import butao.ulife.com.mvp.view.HTMLDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HTMLDetailActivity extends MvpActivity<HTMLDetailPresenter> implements HTMLDetailView {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_content)
    TextView txtContent;
    HTMLDetailModel htmlModel = new HTMLDetailModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadHTMLDetail(bundle.getString("wareId"));
    }

    @Override
    protected HTMLDetailPresenter createPresenter() {
        return new HTMLDetailPresenter(this);
    }

    @Override
    public void getHTMLSuccess(BaseModel<HTMLDetailModel> model) {
        if ("200".equals(model.getCode())) {
            htmlModel = model.getData();
            initView();
        } else{
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    private void initView(){
        if(htmlModel!=null){
            if(htmlModel.getWareInfo()!=null){
                txtContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                if(!TextUtils.isEmpty(htmlModel.getWareInfo().getRemark())){
                    RichText.from(htmlModel.getWareInfo().getRemark()).scaleType(ImageHolder.ScaleType.FIT_AUTO).autoFix(true).showBorder(false).autoPlay(true).into(txtContent);
                }
            }
        }

    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }
}

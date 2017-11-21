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
import butao.ulife.com.mvp.model.HTMLDetailModel;
import butao.ulife.com.mvp.model.HomeImgModel;
import butao.ulife.com.mvp.presenter.HTMLDetailPresenter;
import butao.ulife.com.mvp.presenter.HomeImgDetailPresenter;
import butao.ulife.com.mvp.view.HTMLDetailView;
import butao.ulife.com.mvp.view.HomeImgDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HomeImgDetailActivity extends MvpActivity<HomeImgDetailPresenter> implements HomeImgDetailView {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_content)
    TextView txtContent;
    HomeImgModel homeImgModel = new HomeImgModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadHomeImgInfo(bundle.getString("Id"));
    }

    @Override
    protected HomeImgDetailPresenter createPresenter() {
        return new HomeImgDetailPresenter(this);
    }


    @Override
    public void getHTMLSuccess(BaseModel<HomeImgModel> model) {
        if ("200".equals(model.getCode())) {
            homeImgModel = model.getData();
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
        if(homeImgModel!=null){
            if(homeImgModel.getInfo()!=null){
                if(!TextUtils.isEmpty(homeImgModel.getInfo().getContent())){
                    RichText.fromHtml(homeImgModel.getInfo().getContent()).into(txtContent);
                }
            }
        }

    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }
}

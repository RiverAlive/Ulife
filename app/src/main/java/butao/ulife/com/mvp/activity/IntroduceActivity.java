package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.zzhoujay.richtext.RichText;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.Introduce;
import butao.ulife.com.mvp.presenter.IntroducePresenter;
import butao.ulife.com.mvp.view.IntroduceView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/19.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class IntroduceActivity extends MvpActivity<IntroducePresenter> implements IntroduceView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_content)
    TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mvpPresenter.loadIntroduce(bundle.getString("id"));
    }

    @Override
    public void getIntroduceSuccess(BaseModel<Introduce> model) {
        if ("200".equals(model.getCode())) {
            RichText.fromHtml(model.getData().getNote()).into(txtContent);
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    @Override
    protected IntroducePresenter createPresenter() {
        return new IntroducePresenter(this);
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }
}

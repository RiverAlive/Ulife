package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.presenter.LeaceWordPresenter;
import butao.ulife.com.mvp.view.LeaveWordView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/15.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class LeaveWordActivity extends MvpActivity<LeaceWordPresenter>  implements LeaveWordView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.edt_message)
    EditText edtMessage;
String wareId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_word);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        txtRightTitle.setText("完成");
        txtRightTitle.setVisibility(View.VISIBLE);
        Bundle bundle= getIntent().getExtras();
        wareId = bundle.getString("wareId");
    }

    @Override
    protected LeaceWordPresenter createPresenter() {
        return new LeaceWordPresenter(this);
    }

    @Override
    public void getLWSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            finish();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String msg) {
        ToastUtils.showShortToast(msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                String remark = edtMessage.getText().toString();
                if(TextUtils.isEmpty(cApplication.getLoginId())){
                    gotoActivity(LeaveWordActivity.this,LoginActivity.class);
                    return;
                }
                if(TextUtils.isEmpty(remark)){
                    edtMessage.requestFocus();
                    ToastUtils.showShortToast("请输入评论");
                    return;
                }else{
                    mvpPresenter.loadPushLW(remark,cApplication.getLoginId(),wareId);
                }
                break;
        }
    }
}

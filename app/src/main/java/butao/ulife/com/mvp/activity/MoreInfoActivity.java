package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.dialog.PhoneDialog;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/19.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MoreInfoActivity extends MvpActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_remark)
    TextView txtRemark;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        bundle = getIntent().getExtras();
        txtRemark.setText( bundle.getString("remark"));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.ll_back, R.id.txt_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_phone:
                if (bundle != null) {
                        if (!TextUtils.isEmpty(bundle.getString("phone"))) {
                            PhoneDialog phoneDialog = new PhoneDialog(MoreInfoActivity.this, bundle.getString("phone"));
                            phoneDialog.dialogShow();
                        }
                }
                break;
        }
    }
}

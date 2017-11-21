package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.ClassUtils.MessageChat;
import butao.ulife.com.mvp.dialog.PhoneDialog;
import butao.ulife.com.mvp.model.DirverModel;
import butao.ulife.com.util.JsonUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/21.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DirverDetailActivity extends MvpActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_namecode)
    TextView txtNamecode;
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.txt_guoji)
    TextView txtGuoji;
    @Bind(R.id.txt_age)
    TextView txtAge;
    @Bind(R.id.txt_huzhao)
    TextView txtHuzhao;
    @Bind(R.id.txt_jiazhao)
    TextView txtJiazhao;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    DirverModel.Dirver dirver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirver_detail);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
       dirver= JsonUtil.fromJson(bundle.getString("json"),DirverModel.Dirver.class);
        initView();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initView(){
        if(dirver!=null){
            if(!TextUtils.isEmpty(dirver.getName())){
             txtName.setText("姓名:"+dirver.getName());
            }
            if(!TextUtils.isEmpty(dirver.getNameCode())){
                txtNamecode.setText("代号:"+dirver.getNameCode());
            }
            if(!TextUtils.isEmpty(dirver.getNationality())){
                txtGuoji.setText("国籍:"+dirver.getNationality());
            }
            if(!TextUtils.isEmpty(dirver.getAge())){
                txtAge.setText("年龄:"+dirver.getAge());
            }
            if(!TextUtils.isEmpty(dirver.getPassportNo())){
                txtHuzhao.setText("护照号:"+dirver.getPassportNo());
            }
            if(!TextUtils.isEmpty(dirver.getDrivingNo())){
                txtJiazhao.setText("驾照号:"+dirver.getDrivingNo());
            }
            if(!TextUtils.isEmpty(dirver.getPhone())){
                txtPhone.setText("联系方式:"+dirver.getPhone());
            }
            if(!TextUtils.isEmpty(dirver.getPath())){
                Glide.with(DirverDetailActivity.this).load(dirver.getPath()).into(imgHead);
            }
        }
    }

    @OnClick({R.id.ll_back, R.id.txt_phone, R.id.txt_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_phone:
                if (dirver != null) {
                        if (!TextUtils.isEmpty(dirver.getPhone())) {
                            PhoneDialog phoneDialog = new PhoneDialog(DirverDetailActivity.this, dirver.getPhone());
                            phoneDialog.dialogShow();
                        }
                }
                break;
            case R.id.txt_message:
                if (cApplication.isLogin()) {
                    if (dirver != null) {
                        if (!TextUtils.isEmpty(dirver.getUid())) {
                            MessageChat messageChat = new MessageChat(DirverDetailActivity.this, dirver.getUid());
                            messageChat.inModule();
                        } else {
                            ToastUtils.showShortToast("本店暂时不可私信");
                        }
                    }
                } else {
                    gotoActivity(DirverDetailActivity.this, LoginActivity.class);
                }
                break;
        }
    }
}

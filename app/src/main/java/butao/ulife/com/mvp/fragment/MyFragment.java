package butao.ulife.com.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpFragment;
import butao.ulife.com.mvp.activity.GoodLikeActivity;
import butao.ulife.com.mvp.activity.My.MyOrderActivity;
import butao.ulife.com.mvp.activity.MyIssueActivity;
import butao.ulife.com.mvp.activity.SettingActivity;
import butao.ulife.com.mvp.model.shop.MyOrderModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyFragment extends MvpFragment {

    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.txt_name)
    TextView txtName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewData();
    }

    public  void initViewData(){
        if(!TextUtils.isEmpty(cApplication.getLoginIMG())){
            Glide.with(getActivity()).load(cApplication.getLoginIMG()).placeholder(R.mipmap.zhanweitu).into(imgHead);
        }
        if (!TextUtils.isEmpty(cApplication.getLoginName())){
            txtName.setText(cApplication.getLoginName());
        }else{
            txtName.setText("");
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_fabu, R.id.ll_jiaoyi, R.id.ll_collect, R.id.ll_zan, R.id.ll_setting,R.id.ll_order})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_fabu:
                startActivity(getActivity(), MyIssueActivity.class);
                break;
            case R.id.ll_jiaoyi:
                break;
            case R.id.ll_order:
                startActivity(getActivity(), MyOrderActivity.class);
                break;
            case R.id.ll_collect:
                bundle.putString("type","like");
                startActivity(getActivity(), GoodLikeActivity.class,bundle);
                break;
            case R.id.ll_zan:
                bundle.putString("type","good");
                startActivity(getActivity(), GoodLikeActivity.class,bundle);
                break;
            case R.id.ll_setting:
                startActivity(getActivity(), SettingActivity.class);
                break;
        }
    }
}

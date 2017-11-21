package butao.ulife.com.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class FabuFragment extends MvpFragment {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_img)
    RecyclerView ryImg;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.edt_content)
    EditText edtContent;
    @Bind(R.id.edt_num)
    EditText edtNum;
    @Bind(R.id.txt_class)
    TextView txtClass;
    @Bind(R.id.edt_price)
    EditText edtPrice;
    @Bind(R.id.edt_phone)
    EditText edtPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fabu, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public void initView(){
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("发布商品");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_back, R.id.txt_class})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:

                break;
            case R.id.txt_class:
                break;
        }
    }
}

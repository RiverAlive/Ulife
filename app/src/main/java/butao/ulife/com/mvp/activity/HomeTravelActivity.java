package butao.ulife.com.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import butao.ulife.com.R;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.presenter.HouseTravelPresenter;
import butao.ulife.com.mvp.view.HouseTravelView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/7/19.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HomeTravelActivity extends MvpActivity<HouseTravelPresenter> implements HouseTravelView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    Bundle bundle;
    IdleClassModel classModel = new IdleClassModel();
    @Bind(R.id.img_house)
    ImageView imgHouse;
    @Bind(R.id.img_travel)
    ImageView imgTravel;
String houseId = "",travelId="",houseName ="",travelName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_travel);
        ButterKnife.bind(this);
        imgBack.setVisibility(View.VISIBLE);
        bundle = getIntent().getExtras();
        txtTitle.setText(bundle.getString("title"));
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        mvpPresenter.loadClassData(bundle.getString("cid"));
    }

    @Override
    protected HouseTravelPresenter createPresenter() {
        return new HouseTravelPresenter(this);
    }

    @Override
    public void getClassSuccess(BaseModel<IdleClassModel> model) {
        if ("200".equals(model.getCode())) {
            classModel = model.getData();
            if(classModel!=null){
                if(classModel.getCategorys()!=null&&classModel.getCategorys().size()>0){
                    if(classModel.getCategorys().get(0)!=null){
                        if(!TextUtils.isEmpty(classModel.getCategorys().get(0).getName())&&"房屋出租".equals(classModel.getCategorys().get(0).getName())){
                            if(!TextUtils.isEmpty(classModel.getCategorys().get(0).getMainImg())){
                                Glide.with(HomeTravelActivity.this).load(classModel.getCategorys().get(0).getMainImg()).into(imgHouse);
                            }else{
                                imgHouse.setImageResource(R.mipmap.zhanweitu);
                            }
                            houseId = classModel.getCategorys().get(0).getCid();
                            houseName = classModel.getCategorys().get(0).getName();
                        }
                    }
                }
                if(classModel.getCategorys()!=null&&classModel.getCategorys().size()>1){
                    if(classModel.getCategorys().get(1)!=null){
                        if(!TextUtils.isEmpty(classModel.getCategorys().get(1).getName())&&"出行接送".equals(classModel.getCategorys().get(1).getName())){
                            if(!TextUtils.isEmpty(classModel.getCategorys().get(1).getMainImg())){
                                Glide.with(HomeTravelActivity.this).load(classModel.getCategorys().get(1).getMainImg()).into(imgTravel);
                            }
                            travelId = classModel.getCategorys().get(1).getCid();
                            travelName = classModel.getCategorys().get(1).getName();
                        }
                    }
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String msg) {

    }

    @OnClick({R.id.ll_back, R.id.ll_house, R.id.ll_travel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_house:
                if(!TextUtils.isEmpty(houseId)) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("cid", houseId);
                    bundle1.putString("title", houseName);
                    gotoActivity(HomeTravelActivity.this, HouseActivity.class, bundle1);
                }
                break;
            case R.id.ll_travel:
                if(!TextUtils.isEmpty(travelId)) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("cid", travelId);
                    bundle1.putString("title", travelName);
                    gotoActivity(HomeTravelActivity.this, DirverActivity.class, bundle1);
                }
                break;
        }
    }
}

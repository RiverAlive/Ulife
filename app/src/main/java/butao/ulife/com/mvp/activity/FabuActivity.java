package butao.ulife.com.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butao.ulife.com.R;
import butao.ulife.com.base.BasePresenter;
import butao.ulife.com.base.MvpActivity;
import butao.ulife.com.mvp.dialog.FBClassDialog;
import butao.ulife.com.mvp.dialog.ImgMesDialog;
import butao.ulife.com.mvp.dialog.OnPhotoDialog;
import butao.ulife.com.mvp.dialog.OnphotoListener;
import butao.ulife.com.mvp.model.BaseModel;
import butao.ulife.com.mvp.model.FBImgModel;
import butao.ulife.com.mvp.model.IdleClassModel;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.mvp.presenter.FBPresenter;
import butao.ulife.com.mvp.view.FBView;
import butao.ulife.com.util.Camera;
import butao.ulife.com.view.recyclerview.adapter.CommonRecycleAdapter;
import butao.ulife.com.view.recyclerview.adapter.MultiItemTypeAdapter;
import butao.ulife.com.view.recyclerview.base.ViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.utils.ToastUtils.showShortToast;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class FabuActivity extends MvpActivity<FBPresenter> implements FBView {
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
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private String urlpath; // 图片本地路径
    private List<FBImgModel.ImgModel> imgModels = new ArrayList<>();
    FBImgModel ImgModel =new FBImgModel();
    CommonRecycleAdapter recycleAdapter;
    IdleClassModel classModel = new IdleClassModel();
    String imgIds ="";
    String imgId ="",cid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fabu);
        ButterKnife.bind(this);
        initView();
        mvpPresenter.loadClassData();
    }

    @Override
    protected FBPresenter createPresenter() {
        return new FBPresenter(this);
    }
    public void initView(){
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setText("发布商品");
        txtTitle.setTextColor(getResources().getColor(R.color.white));
        ImgModel.getUploadFile().setPath("-100");
        imgModels.add(ImgModel.getUploadFile());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        ryImg.setLayoutManager(gridLayoutManager);
        recycleAdapter = new CommonRecycleAdapter<FBImgModel.ImgModel>(FabuActivity.this, R.layout.adapter_image, imgModels) {
            @Override
            protected void convert(ViewHolder holder, final FBImgModel.ImgModel fbImgModel, int position) {
                holder.setVisible(R.id.txt_add,false);
                holder.setVisible(R.id.img_fabu,false);
                if(fbImgModel!=null) {
                    if (!TextUtils.isEmpty(fbImgModel.getPath())) {
                        if("-100".equals(fbImgModel.getPath())) {
                            holder.setVisible(R.id.txt_add, true);
                        }else{
                            holder.setVisible(R.id.img_fabu,true);
                            if(!TextUtils.isEmpty(fbImgModel.getPath())) {
                                holder.setImageGlide(R.id.img_fabu, fbImgModel.getPath());
                            }else{
                                holder.setImageResource(R.id.img_fabu, R.mipmap.zhanweitu);
                            }
                        }
                    }
                }else{
                    holder.setVisible(R.id.txt_add, true);
                }
            }
        };
        ryImg.setAdapter(recycleAdapter);
        initClick();
    }

    @Override
    public void getIMGSuccess(BaseModel<FBImgModel> model) {
        if ("200".equals(model.getCode())) {
            imgModels.remove(ImgModel.getUploadFile());
            imgModels.add(model.getData().getUploadFile());
            for(int i = 0;i<imgModels.size();i++){
                if(imgModels.get(i)!=null){
                    if(i==0){
                        if(!TextUtils.isEmpty(imgModels.get(i).getUploadFileId())){
                            imgId = imgModels.get(i).getUploadFileId();
                            imgIds = imgModels.get(i).getUploadFileId();
                        }
                    }else{
                        if(!TextUtils.isEmpty(imgModels.get(i).getUploadFileId())){
                                imgIds = imgIds+","+imgModels.get(i).getUploadFileId();
                        }
                    }
                }
            }
            imgModels.add(ImgModel.getUploadFile());
            recycleAdapter.setDatas(imgModels);
            recycleAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    /**
     * 分类列表
     *
     * @param model
     */
    @Override
    public void getClassSuccess(BaseModel<IdleClassModel> model) {
        if ("200".equals(model.getCode())) {
            classModel= model.getData();
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getWareuccess(BaseModel model) {
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

    @OnClick({R.id.ll_back, R.id.txt_class,R.id.txt_true})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_class:
                if(classModel!=null) {
                    FBClassDialog fbClassDialog = new FBClassDialog(FabuActivity.this,classModel);
                    fbClassDialog.dialogShow();
                    fbClassDialog.setFbClasslistener(new FBClassDialog.FBClasslistener() {
                        @Override
                        public void comfit(IdleClassModel.IdleClass idleClass) {
                            txtClass.setText(idleClass.getName());
                            cid = idleClass.getCid();
                        }
                    });
                }else{
                    mvpPresenter.loadClassData();
                }
                break;
            case R.id.txt_true:
                String name = edtName.getText().toString();
                String content = edtContent.getText().toString();
                String num = edtNum.getText().toString();
                String price = edtPrice.getText().toString();
                String phone = edtPhone.getText().toString();
                if(TextUtils.isEmpty(name)){
                    edtName.requestFocus();
                    ToastUtils.showShortToast("请输入商品名称");
                    return;
                }
                if(TextUtils.isEmpty(content)){
                    edtContent.requestFocus();
                    ToastUtils.showShortToast("请输入商品描述");
                    return;
                }
                if(TextUtils.isEmpty(num)){
                    edtNum.requestFocus();
                    ToastUtils.showShortToast("请输入商品数量");
                    return;
                }
                if(TextUtils.isEmpty(price)){
                    edtPrice.requestFocus();
                    ToastUtils.showShortToast("请输入商品价格");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    edtPhone.requestFocus();
                    ToastUtils.showShortToast("请输入手机号");
                    return;
                }
                if(!TextUtils.isEmpty(imgIds)){
                    mvpPresenter.loadWareInfo(name,content,num,price,phone,cid,imgIds,imgId);
                }else{
                    ImgMesDialog imgMesDialog = new ImgMesDialog(FabuActivity.this);
                    imgMesDialog.dialogShow();
                    imgMesDialog.setIMGlistener(new ImgMesDialog.IMGlistener() {
                        @Override
                        public void comfit() {
                            AddImg();
                        }
                    });
                    return;
                }
                break;
        }
    }
    public void initClick(){
        recycleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                List<FBImgModel.ImgModel>  datas= recycleAdapter.getDatas();
                if(datas!=null){
                    if(datas.get(position)!=null) {
                        if (datas.get(position).getPath()!= null) {
                            if ("-100".equals(datas.get(position).getPath())) {
                                AddImg();
                            }
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    public void AddImg(){
        OnPhotoDialog onPhotoDialog = new OnPhotoDialog(this);
        onPhotoDialog.SetOnphotolistener(onphotoListener);
        onPhotoDialog.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
    OnphotoListener onphotoListener = new OnphotoListener() {
        @Override
        public void confirm(int tag) {
            if (String.valueOf(tag) != null) {
                if (tag == PIC_FROM_CAMERA) {
                    //拍照
                    Camera.getInstance(FabuActivity.this).startCamera(true);
                } else if (tag == PIC_FROM＿LOCALPHOTO) {
                    //相册
                    Camera.getInstance(FabuActivity.this).startCamera(false);
                }
            }
        }
    };
    @Override
    public void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        switch (requestCode) {
            case PIC_FROM_CAMERA:
                //相册
                if (data != null) {
                    Camera.getInstance(FabuActivity.this).setPhotoZoom(data.getData(), false);
                }
                break;
            case 2:
                //拍照
                Camera.getInstance(FabuActivity.this).setPhotoZoom(null,true);
                break;
            case 3:
                //剪切完成
                if (data != null) {                    // 获取原图
                    urlpath = Camera.getInstance(FabuActivity.this).getPath();
                    if (!TextUtils.isEmpty(urlpath)) {
                        mvpPresenter.loadIMG(new File(urlpath));
                    } else {
                        showShortToast("剪切失败");
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode,
                data);
    }
}

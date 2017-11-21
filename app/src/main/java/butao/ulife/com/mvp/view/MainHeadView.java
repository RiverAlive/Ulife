package butao.ulife.com.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.ScreenUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.R;
import butao.ulife.com.mvp.model.MainModel;
import butao.ulife.com.view.recyclerview.RecyclerViewBanner;

/**
 * 创建时间 ：2017/6/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MainHeadView extends LinearLayout {
    public RecyclerViewBanner rvBanner;
    Context mContext;
    List<MainModel.Banner> eventModels = new ArrayList<>();

    public MainHeadView(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public void setData(List<MainModel.Banner> eventModels) {
        this.eventModels = eventModels;
        initViewData();
    }


    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_main_header, this);
        rvBanner = (RecyclerViewBanner) view.findViewById(R.id.rv_banner);
        LinearLayout.LayoutParams rvBannerparams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), (ScreenUtils.getScreenWidth() * 55) / 75 + 40);
        rvBanner.setLayoutParams(rvBannerparams);
        rvBanner.setIndicatorInterval(3000);
    }


    public void initViewData() {
        if (eventModels != null) {
            if (eventModels.size() > 0) {
                rvBanner.setRvBannerData(eventModels);
                rvBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, AppCompatImageView bannerView) {
                        bannerView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        if (eventModels.size() > 0) {
                            if (!TextUtils.isEmpty(eventModels.get(position).getMainImg())) {
                                Glide.with(bannerView.getContext())
                                        .load(eventModels.get(position).getMainImg())
                                        .placeholder(R.mipmap.shiyu_zhanweitu)
                                        .into(bannerView);
                            }
                        } else {
                            rvBanner.setVisibility(GONE);
                        }
                    }
                });
            } else {
                rvBanner.setVisibility(GONE);
            }
        }

    }
}




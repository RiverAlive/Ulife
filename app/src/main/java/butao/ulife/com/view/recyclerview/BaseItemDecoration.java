package butao.ulife.com.view.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

/**
 * Created by Administrator on 2016/9/25.
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {


    private Context mContext;

    private Drawable mDivider;
    private int mHeight;
    private int mWidth;


    public BaseItemDecoration(Context mContext, int res) {
        this.mContext = mContext;
        this.mDivider = getDivider(res);
        this.mHeight = mDivider.getIntrinsicHeight() <= 0 ? dp2px(5) : mDivider.getIntrinsicHeight();
        this.mWidth = mDivider.getIntrinsicWidth() <= 0 ? dp2px(5) : mDivider.getIntrinsicWidth();
    }



    public BaseItemDecoration(Context mContext, int res, int mHeight, int mWidth) {
        this.mContext = mContext;
        this.mDivider = getDivider(res);
        this.mHeight = mHeight;
        this.mWidth = mWidth;
    }


    public BaseItemDecoration(Context mContext, Drawable mDivider, int mHeight, int mWidth) {
        this.mContext = mContext;
        this.mDivider = mDivider;
        this.mHeight = mHeight;
        this.mWidth = mWidth;
    }



    private Drawable getDivider(int res) {
        Resources resources = mContext.getResources();
        return resources.getDrawable(res);
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

}

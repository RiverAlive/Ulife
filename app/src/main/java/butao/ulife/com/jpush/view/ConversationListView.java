package butao.ulife.com.jpush.view;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import butao.ulife.com.R;


public class ConversationListView {
	
	private ListView mConvListView = null;
	private LinearLayout mHeader;
	private RelativeLayout mLoadingHeader;
    private ImageView mLoadingIv;
    private Context mContext;

	public ConversationListView(ListView view, Context context) {
		this.mConvListView = view;
        this.mContext = context;
	}

	public void initModule() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = (LinearLayout) inflater.inflate(R.layout.conv_list_head_view, mConvListView, false);
        mLoadingHeader = (RelativeLayout) inflater.inflate(R.layout.jmui_drop_down_list_header, mConvListView, false);
        mLoadingIv = (ImageView) mLoadingHeader.findViewById(R.id.jmui_loading_img);
        mConvListView.addHeaderView(mHeader);
        mConvListView.addHeaderView(mLoadingHeader);
	}
	
	public void setConvListAdapter(ListAdapter adapter) {
		mConvListView.setAdapter(adapter);
	}
	

	public void setItemListeners(OnItemClickListener onClickListener) {
		mConvListView.setOnItemClickListener(onClickListener);
	}
	
	public void setLongClickListener(OnItemLongClickListener listener) {
		mConvListView.setOnItemLongClickListener(listener);
	}

    public void showHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.VISIBLE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.VISIBLE);
    }

    public void dismissHeaderView() {
		mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.GONE);
		mHeader.findViewById(R.id.check_network_hit).setVisibility(View.GONE);
    }


	public void showLoadingHeader() {
        mLoadingIv.setVisibility(View.VISIBLE);
        AnimationDrawable drawable = (AnimationDrawable) mLoadingIv.getDrawable();
        drawable.start();
        mLoadingIv.setVisibility(View.VISIBLE);
	}

    public void dismissLoadingHeader() {
        mLoadingIv.setVisibility(View.GONE);
    }
}

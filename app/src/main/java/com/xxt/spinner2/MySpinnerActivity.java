package com.xxt.spinner2;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import org.w3c.dom.Text;

import com.xxt.spinner2.R.layout;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MySpinnerActivity extends Activity {
	private List<String> arrList = new ArrayList<String>();
	private MySpinner mySpinner;
	 private MyAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.test);
		arrList.add("请选择:");
		arrList.add("Mercury");
		arrList.add("test2");
		arrList.add("test3");
		arrList.add("test4");
		arrList.add("test5");
		arrList.add("test6");
		arrList.add("test7");
		arrList.add("test8");
		arrList.add("test9");
		arrList.add("test10");
		mySpinner = (MySpinner) this.findViewById(R.id.mySpinner);
		adapter=new MyAdapter(this, android.R.layout.simple_list_item_1, arrList);
		mySpinner.setAdapter(adapter);
	}

	private class MyAdapter<T> extends ArrayAdapter<T> {
		private LayoutInflater mInflater;
		private ViewHolder holder;
		private View rootView = null;
		private SpinnerHandler handler;

		public MyAdapter(Context context, int textViewResourceId,
				List<T> objects) {
			super(context, textViewResourceId, objects);
			handler=new SpinnerHandler();
			mInflater=(LayoutInflater)((Activity)context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		// 不显示“请选择”这项，总数减1；
		@Override
		public int getCount() {
			return arrList.size()-1;
		}
//獲取下拉列表view
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if(convertView==null){
				//自定义的下拉列表布局
				convertView=mInflater.inflate(R.layout.spinner_item, null);
				holder=new ViewHolder();
				holder.text=(TextView) convertView.findViewById(R.id.spinner_txt);
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//获取点击的布局
						ViewHolder vh=(ViewHolder) v.getTag();
						mySpinner.setSelection(vh.index);
						//刷新
						adapter.notifyDataSetChanged();
						rootView=v.getRootView();
						handler.sendEmptyMessageDelayed(1, 100);
					}
				});
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.text.setText(arrList.get(position+1));
			holder.index=position;
			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int np=0;
			int index=mySpinner.getSelectedItemPosition();
			if(index==-1){
				np=0;
			}else{
				np=position+1;
			}
			return super.getView(np, convertView, parent);
		}

		private class ViewHolder {
			private int index;
			private TextView text;
		}

		private class SpinnerHandler extends Handler {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (rootView == null) {
						rootView.setVisibility(View.GONE);
					}
					break;

				default:
					break;
				}
			}
		}
	}
}

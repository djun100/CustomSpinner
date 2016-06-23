package com.xxt.spinner2;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Spinner2Activity extends Activity {
	private TextView mtxtView;
    // android自带标准Spinner
//    private Spinner mAndroidSpinner;
    // 自定义Spinner
    private CustomSpinner mSpinner;
//    private Button mbtnOK;
    
//    private ArrayAdapter<String> androidAdapter;
    private MyAdapter<String> adapter;
    private List<String> list;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mtxtView = (TextView)findViewById(R.id.txtView);
//        mAndroidSpinner = (Spinner)findViewById(R.id.androidspinner);
        mSpinner = (CustomSpinner)findViewById(R.id.spinner);
//        mbtnOK = (Button)findViewById(R.id.btnOK);
        
        mtxtView.setText("Init");
        
//        mbtnOK.setOnClickListener(new OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                // 获取CustomSpinner选中的哪一项，-1表示没有选中
//                int nSelect = mSpinner.getSelectedItemPosition();
//                if (nSelect == -1)
//                {
//                    Toast.makeText(Spinner2Activity.this, "请选中一项!", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    String str = "" + nSelect;
//                    Toast.makeText(Spinner2Activity.this, str, Toast.LENGTH_LONG).show();
//                }
//                
//            }
//        });
        
        list = new ArrayList<String>();
        list.add("请选择:");
        list.add("Mercury");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        list.add("test5");
        list.add("test6");
        list.add("test7");
        list.add("test8");
        list.add("test9");
        list.add("test10");
        list.add("test11");
        list.add("test12");
        list.add("test13");
        list.add("test14");
        list.add("test15");
        list.add("test16");
        list.add("test17");
        list.add("test18");
        
//        androidAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
//                list);
//        androidAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        adapter = new MyAdapter<String>(this, android.R.layout.simple_spinner_item,
                list);
        
//        mAndroidSpinner.setAdapter(androidAdapter);
//        mAndroidSpinner.setPrompt("测试");
        
        mSpinner.setAdapter(adapter);
//        mSpinner.setPrompt("测试");
    }
    
    private class MyAdapter<T> extends ArrayAdapter<T>
    {
        private LayoutInflater mInflater;
        private ViewHolder holder;
        private View rootView = null;
        private SpinnerHandler handler;
        
        public MyAdapter(Context context, int textViewResourceId, List<T> objects) {
            super(context, textViewResourceId, objects);
            
            handler = new SpinnerHandler();
            mInflater = (LayoutInflater) ((Activity) context)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // 在下拉列表中不要显示“请选择:”这一项，所以要将总数减1
            return list.size() - 1;
        }

        // 获取下拉列表的view
        @Override
        public View getDropDownView(int position, View convertView,
                ViewGroup parent) {

            if (null == convertView)
            {
                // 将下拉列表的每个item用spinner_item.xml布局
                convertView = mInflater.inflate(R.layout.spinner_item, null);
                holder = new ViewHolder();
                
                holder.txt = (TextView)convertView.findViewById(R.id.spinner_txt);
//                holder.radio = (RadioButton)convertView.findViewById(R.id.spinner_radio);
                // 给item添加点击事件，，dismiss下拉列表
                convertView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        
                        ViewHolder vh = (ViewHolder) v.getTag();
                        Log.d("MyAdapter", "onClick index = " + vh.index);
                        // 将RadioButton选中
//                        vh.radio.setChecked(true);
                        // 设置Spinner第几项被选中了
                        mSpinner.setSelection(vh.index);
                        adapter.notifyDataSetChanged();
                        // 获取下拉列表的主窗口句柄
                        rootView = v.getRootView();
                        // 延迟100毫秒dismiss下拉列表
                        handler.sendEmptyMessageDelayed(1, 100);
                    }
                });
                
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            // 下拉列表中要去掉“请选择：”项，所以要“position + 1”
            holder.txt.setText(list.get(position + 1));
            holder.index = position;
            // 设置RadioButton的选中状态
//            if (mSpinner.getSelectedItemPosition() == position)
//            {
//                holder.radio.setChecked(true);
//            }
//            else
//            {
//                holder.radio.setChecked(false);
//            }
            
            return convertView;
        }

        // 获取控件view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
    
            int nPos = 0;
            
            int nIndex = mSpinner.getSelectedItemPosition();
            // 如果没有选中，返回“请选择：”，否则返回选中项
            if (nIndex == -1)
            {
                nPos = 0;
            }
            else
            {
                nPos = position + 1;
            }
            
            return super.getView(nPos, convertView, parent);
        }
        
        private class ViewHolder
        {
            public int index;
            public TextView txt;
//            public RadioButton radio;
        }
        
        private class SpinnerHandler extends Handler
        {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                case 1:
                    if (null != rootView)
                    {
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
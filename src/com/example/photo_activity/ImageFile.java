package com.example.photo_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.photo_adapter.FolderAdapter;
import com.example.photo_util.Bimp;
import com.example.qqphoto.R;

public class ImageFile extends Activity {
	private Button bt_cancel;
	private Context mContext;
	private FolderAdapter folderAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_image_file);
		mContext = this;
		bt_cancel = (Button) findViewById(R.id.cancel);
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(R.id.fileGridView);
		TextView textView = (TextView) findViewById(R.id.headerTitle);
//		int strId = getStringID("photo");
	
		textView.setText ("选择相册");
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}
	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
			Bimp.tempSelectBitmap.clear();
			Intent intent = new Intent();
			intent.setClass(mContext, MainActivity.class);
			startActivity(intent);
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(mContext, MainActivity.class);
			startActivity(intent);
		}
		
		return true;
	}
}

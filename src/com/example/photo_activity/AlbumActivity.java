package com.example.photo_activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.photo_adapter.AlbumGridViewAdapter;
import com.example.photo_util.AlbumHelper;
import com.example.photo_util.Bimp;
import com.example.photo_util.ImageBucket;
import com.example.photo_util.ImageItem;
import com.example.photo_util.PublicWay;
import com.example.qqphoto.R;

public class AlbumActivity extends Activity {

	private GridView gridView;
	//当手机里没有图片时，提示用户没有图片的控件
	private TextView tv;
	//gridView的adapter
	private AlbumGridViewAdapter gridImageAdapter;
	//完成按钮
	private Button okButton;
	// 返回按钮
	private Button back;
	// 取消按钮
	private Button cancel;
	private Intent intent;
	// 预览按钮
	private Button preview;
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	public static Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_list_item);
		mContext = this;
		setContentView(R.layout.plugin_camera_album);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.plugin_camera_no_pictures);
		init();
		initListener();
		//这个函数主要用来控制预览和完成按钮的状态
		isShowOkBt();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		
		
		contentList = helper.getImagesBucketList(false);
		
		
		dataList = new ArrayList<ImageItem>();
		for(int i = 0; i<contentList.size(); i++){
			dataList.addAll( contentList.get(i).imageList );
		}
		
		
		back = (Button) findViewById(R.id.back);
		cancel = (Button) findViewById(R.id.cancel);
		preview = (Button) findViewById(R.id.preview);
		
		


		cancel.setOnClickListener(new CancelListener());
		back.setOnClickListener(new BackListener());
		
		 preview.setOnClickListener(new PreviewListener());
		gridView = (GridView) findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
				Bimp.tempSelectBitmap);
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(R.id.myText);
		gridView.setEmptyView(tv);
		okButton = (Button) findViewById(R.id.ok_button);
		okButton.setText("完成"+"(" + Bimp.tempSelectBitmap.size()
				+ "/"+PublicWay.num+")");

	}
	// 预览按钮的监听
	private class PreviewListener implements OnClickListener {
		public void onClick(View v) {
			if (Bimp.tempSelectBitmap.size() > 0) {
				Intent intent= new Intent();
				intent.putExtra("position", "1");
				intent.setClass(AlbumActivity.this, GalleryActivity.class);
				startActivity(intent);
			}
		}

	}
	
	
	// 完成按钮的监听
	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
			Intent intent= new Intent(mContext, MainActivity.class);
			startActivity(intent);
			finish();
		}

	}

	// 取消按钮的监听
	private class CancelListener implements OnClickListener {
		public void onClick(View v) {
			// Bimp.tempSelectBitmap.clear();
			Intent intent = new Intent(mContext, MainActivity.class);
			startActivity(intent);
		}
	}

	// 返回按钮监听，返回到相册页面
	private class BackListener implements OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(AlbumActivity.this, ImageFile.class);
			startActivity(intent);
		}
	}
	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked,Button chooseBt) {
						if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							if (!removeOneData(dataList.get(position))) {
								Toast.makeText(AlbumActivity.this, "超出可选图片张数",
										200).show();
							}
							return;
						}
						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);
							Bimp.tempSelectBitmap.add(dataList.get(position));
							okButton.setText("完成"+"(" + Bimp.tempSelectBitmap.size()
									+ "/"+PublicWay.num+")");
						} else {
							Bimp.tempSelectBitmap.remove(dataList.get(position));
							chooseBt.setVisibility(View.GONE);
							okButton.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
						}
						isShowOkBt();
					}
				});

		okButton.setOnClickListener(new AlbumSendListener());

	}
	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			okButton.setText("完成"+"(" +Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
			return true;
		}
	return false;
}
	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			okButton.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
			preview.setPressed(true);
			okButton.setPressed(true);
			preview.setClickable(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);
			preview.setTextColor(Color.WHITE);
		} else {
			okButton.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
			preview.setPressed(false);
			preview.setClickable(false);
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
			preview.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent= new Intent(AlbumActivity.this, ImageFile.class);
			startActivity(intent);
		}
		return false;

	}
	
	
	@Override
	protected void onRestart() {
		isShowOkBt();
		super.onRestart();
	}

}

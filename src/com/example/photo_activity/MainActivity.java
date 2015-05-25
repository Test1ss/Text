package com.example.photo_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photo_util.Bimp;
import com.example.qqphoto.R;

public class MainActivity extends Activity {
	private GridView noScrollgridview;
	public static Bitmap bimap;
	private PopupWindow pop;
	private GridAdapter adapter;
	private LinearLayout ll_popup;
	public TextView send_message;
	private View parentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(parentView);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		
		inin();
	}

	private void inin() {
		// TODO Auto-generated method stub
		pop = new PopupWindow(MainActivity.this);
		// LayoutInflater inflater;
		View view = View.inflate(this, R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		//整个线性布局
				parent.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pop.dismiss();
						ll_popup.clearAnimation();
					}
				});
				//拍照
				bt1.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
//						photo();
						pop.dismiss();
						ll_popup.clearAnimation();
					}
				});
				//相册
				bt2.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								AlbumActivity.class);
						startActivity(intent);
						/**
						 * overridePendingTransition
						 * 实现两个 Activity 切换时的动画。在Activity中使用
		                                                    有两个参数：进入动画和出去的动画。
		                                                    注意
							1、必须在 StartActivity()  或 finish() 之后立即调用。
							2、而且在 2.1 以上版本有效
							3、手机设置-显示-动画，要开启状态
						 */
						overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
						pop.dismiss();
						ll_popup.clearAnimation();
					}
				});
				//取消
				bt3.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						pop.dismiss();
						ll_popup.clearAnimation();
					}
				});
				

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
//		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {//查看某个照片

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Toast.makeText(MainActivity.this, Bimp.tempSelectBitmap.get(arg2).imagePath, Toast.LENGTH_LONG).show();
		
				}
			}
		});

	}


	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
//			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
//				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

	}
}

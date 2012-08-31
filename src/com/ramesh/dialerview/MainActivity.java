package com.ramesh.dialerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,
		com.ramesh.dialerview.DialAdapterView.OnItemSelectedListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.imageView1);

		DialAdapterView d = (DialAdapterView) findViewById(R.id.dialAdapterView1);
		// DialAdapterView d = new DialAdapterView(this);
		d.setBackgroundColor(Color.LTGRAY);
		d.setAdapter(new MyAdapter());
		d.setOnItemClickListener(this);
		d.setOnItemSelectedCallBack(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(this, "item" + position, Toast.LENGTH_SHORT).show();
	}

	public int[] IDS = new int[] { R.drawable.facebook, R.drawable.yahoo,
			R.drawable.linkedin, R.drawable.twitter, R.drawable.myspace,
			R.drawable.blogspot };

	public String[] names = new String[] { "facebook", "yahoo", "linkedin",
			"twitter", "myspace", "blogspot" };

	private ImageView image;

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return IDS.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView image = new ImageView(MainActivity.this);
			image.setImageResource(IDS[position]);
			return image;
		}

	}

	@Override
	public void onItemSeletedCallback(AdapterView<?> adapterview, int position) {
		// Toast.makeText(this, "Selected Item:" + names[position],
		// Toast.LENGTH_SHORT).show();
		image.setImageResource(IDS[position]);

	}

}

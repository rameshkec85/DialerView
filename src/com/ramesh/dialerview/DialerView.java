package com.ramesh.dialerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialerView extends ViewGroup {

	public final int RADIUS = 100;

	public DialerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DialerView(Context context) {
		super(context);
		init(context);
	}

	TextView text[];

	public void init(Context context) {
		text = new TextView[4];
		for (int i = 0; i < 4; i++) {
			text[i] = new TextView(context);
			text[i].setText("R");
			addView(text[i], new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));

			addAndMeasureChild(text[i]);
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {

		double angle = 0;
		final int children = getChildCount(), height = getMeasuredHeight();
		int width = getMeasuredWidth();

		for (int i = 0; i < children; i++) {
			final View view = getChildAt(i);
			// addAndMeasureChild(view);
			width = view.getMeasuredWidth();
			if (view.getVisibility() != View.VISIBLE)
				continue;
			// view.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);

			final int h = view.getMeasuredHeight();

			angle = (i + 90) * Math.PI / 180;

			int x = 200 + (int) (RADIUS * Math.cos(angle));
			int y = 200 + (int) (RADIUS * Math.sin(angle));

			view.layout(x, y, x + width, y + h);

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	private void addAndMeasureChild(View child) {
		LayoutParams params = child.getLayoutParams();
		if (params == null) {
			params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		addViewInLayout(child, -1, params, true);
		int itemWidth = 50;
		child.measure(MeasureSpec.EXACTLY | itemWidth, MeasureSpec.UNSPECIFIED);

	}
}

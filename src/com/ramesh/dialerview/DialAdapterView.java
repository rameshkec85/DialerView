package com.ramesh.dialerview;

import static com.ramesh.dialerview.DialerUtil.getPointAtCirlce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class DialAdapterView extends AdapterView<Adapter> {

	private final String TAG = DialAdapterView.class.getSimpleName();

	private static final int INVALID_INDEX = -1;
	private Adapter mAdapter;
	private List<ChildItems> childeItems;// = new ArrayList<ChildItems>();
	public final int RADIUS = 150;
	public final int ItemWidth = 70;

	public DialAdapterView(Context context) {
		super(context);
		init(context);
	}

	public DialAdapterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		detector = new GestureDetector(context, s);
		childeItems = new ArrayList<ChildItems>();
	}

	@Override
	public Adapter getAdapter() {
		return mAdapter;
	}

	@Override
	public void setAdapter(Adapter adapter) {
		mAdapter = adapter;
		removeAllViewsInLayout();
		requestLayout();
	}

	@Override
	public View getSelectedView() {
		return mAdapter.getView(selection, null, this);
	}

	private int selection;

	@Override
	public void setSelection(int position) {
		selection = position;
		Log.e(TAG, "Selected Item" + position);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		center = extracted();
		if (mAdapter == null) {
			return;
		}
		if (getChildCount() == 0) {
			int position = 0;
			int bottomEdge = 0;

			Log.i(TAG, "" + mAdapter.getCount());
			while (bottomEdge < getHeight() && position < mAdapter.getCount()) {

				View newBottomChild = mAdapter.getView(position, null, this);
				newBottomChild.setTag(position);
				addAndMeasureChild(newBottomChild);
				// bottomEdge += newBottomChild.getMeasuredHeight();
				position++;
			}

		}
		positionItems();
	}

	// private View centerLayout;
	// private boolean isCenterAvailable;

	// public void setCenterLayout(View view) {
	// ImageView image = new ImageView(getContext());
	// image.setImageResource(R.drawable.yahoo);
	// image.setLayoutParams(new LayoutParams(100, 100));
	// centerLayout = image;
	// isCenterAvailable = true;
	// }

	private Point extracted() {
		return new Point(getWidth() / 2, getHeight() / 2);
	}

	private void addAndMeasureChild(View child) {
		LayoutParams params = child.getLayoutParams();
		if (params == null) {
			params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		addViewInLayout(child, -1, params, true);
		int itemWidth = ItemWidth;// getWidth();
		child.measure(MeasureSpec.EXACTLY | itemWidth, MeasureSpec.UNSPECIFIED);
	}

	// Point middle = new Point();
	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	/**
	 * Positions the children at the "correct" positions
	 */
	private void positionItems() {

		if (childeItems != null && !childeItems.isEmpty()) {
			childeItems.clear();
		}

		int top = 0;
		double angle;
		int childCount = getChildCount();

		for (int index = 0; index < childCount; index++) {
			View child = getChildAt(index);
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			int left;

			angle = (index * 360 / childCount);
			Point positions = getPointAtCirlce(RADIUS, center, angle);
			left = positions.x - width / 2;
			top = positions.y - height / 2;
			ChildItems items = new ChildItems();
			items.setWidth(width);
			items.setHeight(height);
			items.setCurrentX(positions.x);
			items.setCurrentY(positions.y);
			items.setCurrentAngle(angle);
			items.setPosition(index);
			childeItems.add(items);

			Log.d(TAG, index + "item-->" + positions.x + ":" + positions.y);

			child.layout(left, top, left + width, top + height);
		}

		/****************************************/
	}

	// private void drawCenter(View view)
	// {
	// if (isCenterAvailable)
	// {
	// View child = getChildAt(6);
	// int width = child.getMeasuredWidth();
	// int height = child.getMeasuredHeight();
	// int left, top;
	// Point positions = getPointAtCirlce(0, center, 0);
	// left = positions.x - width / 2;
	// top = positions.y - height / 2;
	// child.layout(left, top, left + width, top + height);
	// }
	// }

	SimpleGester s = new SimpleGester();
	private GestureDetector detector;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (detector.onTouchEvent(event)) {
			return true;
		}
		int action = event.getAction();
		if (action == MotionEvent.ACTION_UP) {
			// Helper method for lifted finger
			onUp();
		} else if (action == MotionEvent.ACTION_CANCEL) {
			onUp();
			// onCancel();
		}
		return true;
	}

	public class SimpleGester extends SimpleOnGestureListener {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// int c = getContainingChildIndex((int) e.getX(), (int) e.getY());
			clickChildAt((int) e.getX(), (int) e.getY());
			Log.v(TAG, e.getX() + "-----------------" + e.getY());
			return super.onSingleTapUp(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			mHandler.removeCallbacks(mRunnable);
			mRunnable.startUsingVelocity((int) velocityX);
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			trackMotionScroll(/* -1 * */(int) distanceX);
			return true;
		}

	}

	RotatorRunnable mRunnable = new RotatorRunnable();
	Handler mHandler = new Handler();
	private int mAnimationDuration = 900;
	private boolean mShouldStopFling;

	class RotatorRunnable implements Runnable {
		/**
		 * Tracks the decay of a fling rotation
		 */
		private DialRotator mRotator;

		/**
		 * Angle value reported by mRotator on the previous fling
		 */
		private float mLastFlingAngle;

		/**
		 * Constructor
		 */
		public RotatorRunnable() {
			mRotator = new DialRotator(getContext());
		}

		private void startCommon() {
			// Remove any pending flings
			removeCallbacks(this);
		}

		public void startUsingVelocity(float initialVelocity) {
			if (initialVelocity == 0)
				return;

			startCommon();

			mLastFlingAngle = 0.0f;

			mRotator.fling(initialVelocity);

			post(this);
		}

		public void startUsingDistance(double deltaAngle) {
			if (deltaAngle == 0)
				return;

			startCommon();

			mLastFlingAngle = 0;
			synchronized (this) {
				mRotator.startRotate(0.0f, (float) -deltaAngle,
						mAnimationDuration);
			}
			post(this);
		}

		public void stop(boolean scrollIntoSlots) {
			removeCallbacks(this);
			endFling(scrollIntoSlots);
		}

		private void endFling(boolean scrollIntoSlots) {
			/*
			 * Force the scroller's status to finished (without setting its
			 * position to the end)
			 */
			synchronized (this) {
				mRotator.forceFinished(true);
			}
			if (scrollIntoSlots)
				scrollIntoSlots();
		}

		public void run() {
			if (DialAdapterView.this.getChildCount() == 0) {
				endFling(true);
				return;
			}

			mShouldStopFling = false;

			final DialRotator rotator;
			final float angle;
			boolean more;
			synchronized (this) {
				rotator = mRotator;
				more = rotator.computeAngleOffset();
				angle = rotator.getCurrAngle();
			}

			// Flip sign to convert finger direction to list items direction
			// (e.g. finger moving down means list is moving towards the top)
			float delta = mLastFlingAngle - angle;

			// ////// Shoud be reworked
			trackMotionScroll(delta);

			if (more && !mShouldStopFling) {
				mLastFlingAngle = angle;
				post(this);
			} else {
				mLastFlingAngle = 0.0f;
				endFling(true);
				// trackMotionScrollTrim();
			}
		}

	}

	// private void trackMotionScrollTrim() {
	// int top = 0;
	// double angle;
	// int count = childeItems.size();
	//
	// ChildItems list = Collections.min(childeItems);
	// Log.e(TAG, "selected position:" + childeItems.indexOf(list));
	// double deltaAngle = childeItems.get(childeItems.indexOf(list))
	// .getCurrentAngle();
	//
	// for (int index = 0; index < count; index++) {
	// View child = getChildAt(index);
	// ChildItems item = childeItems.get(index);
	// int width = item.getWidth();
	// int height = item.getHeight();
	// int left = 0;
	// angle = item.getCurrentAngle() + deltaAngle;
	// item.setCurrentAngle(angle);
	// Point positions = DialerUtil
	// .getPointAtCirlce(RADIUS, center, angle);
	// left = positions.x - width / 2;
	// top = positions.y - height / 2;
	// item.setCurrentX(positions.x);
	// item.setCurrentY(positions.y);
	// Log.i(TAG, index + "current angle:" + angle);
	// childeItems.set(index, item);
	// child.layout(left, top, left + width, top + height);
	// }
	// }

	private void trackMotionScroll(float deltaAngle) {
		int top = 0;
		double angle;
		int count = childeItems.size();
		for (int index = 0; index < count; index++) {
			View child = getChildAt(index);
			ChildItems item = childeItems.get(index);
			int width = item.getWidth();
			int height = item.getHeight();
			int left = 0;
			angle = item.getCurrentAngle() + deltaAngle;
			while (angle > 360.0f)
				angle -= 360.0f;
			while (angle < 0.0f)
				angle += 360.0f;
			item.setCurrentAngle(angle);
			Point positions = DialerUtil
					.getPointAtCirlce(RADIUS, center, angle);
			left = positions.x - width / 2;
			top = positions.y - height / 2;
			item.setCurrentX(positions.x);
			item.setCurrentY(positions.y);

			Log.i(TAG, index + "current angle:" + angle);
			childeItems.set(index, item);
			child.layout(left, top, left + width, top + height);
		}

	}

	private Rect mRect;
	private Point center;

	private void clickChildAt(final int x, final int y) {
		final int index = getContainingChildIndex(x, y);
		if (index != INVALID_INDEX) {
			final View itemView = getChildAt(index);
			final int position = index;// mFirstItemPosition + index;
			final long id = mAdapter.getItemId(position);
			performItemClick(itemView, position, id);
		}
	}

	private int getContainingChildIndex(final int x, final int y) {
		if (mRect == null) {
			mRect = new Rect();
		}
		for (int index = 0; index < getChildCount(); index++) {
			getChildAt(index).getHitRect(mRect);
			if (mRect.contains(x, y)) {
				return index;
			}
		}
		return INVALID_INDEX;

	}

	void onUp() {
		if (mRunnable.mRotator.isFinished()) {
			scrollIntoSlots();
		}
	}

	private void scrollIntoSlots() {
		if (getChildCount() == 0)
			return;
		// get nearest item to the 0 degrees angle
		// Sort itmes and get nearest angle
		double angle;
		int position;

		ArrayList<ChildItems> arr = new ArrayList<ChildItems>();

		for (int i = 0; i < getAdapter().getCount(); i++)
			arr.add(childeItems.get(i));
		Collections.sort(arr);
		angle = arr.get(0).getCurrentAngle();
		if (angle > 180.0f)
			angle = -(360.0f - angle);
		if (angle != 0.0f) {
			mRunnable.startUsingDistance(-angle);
		} else {
			position = arr.get(0).getPosition();
			setSelection(position);
			if (mOnItemSelectedListener != null)
				mOnItemSelectedListener.onItemSeletedCallback(this, position);
		}
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {

		int left = child.getLeft();
		int top = child.getTop();

		int position = (Integer) child.getTag();

		// get offset to center
		int centerX = child.getWidth() / 2;
		int centerY = child.getHeight() / 2;
		// get absolute center of child
		float pivotX = left + centerX;
		float pivotY = top + centerY;
		canvas.save();

		double rotate = childeItems.get(position).getCurrentAngle() + 90;
		canvas.rotate((float) rotate, pivotX, pivotY);
		super.drawChild(canvas, child, drawingTime);
		canvas.restore();
		return true;
	}

	public void setOnItemSelectedCallBack(
			OnItemSelectedListener mOnItemSelectedListener) {
		this.mOnItemSelectedListener = mOnItemSelectedListener;
	}

	private OnItemSelectedListener mOnItemSelectedListener;

	public interface OnItemSelectedListener {
		public void onItemSeletedCallback(AdapterView<?> adapterview,
				int position);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		return super.onSaveInstanceState();
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		super.onRestoreInstanceState(state);
	}

}

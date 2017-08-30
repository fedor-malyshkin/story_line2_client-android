package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class SlidingPaneLayoutEx extends SlidingPaneLayout {

	private int mEdgeSlop = 0;
	private float mInitialMotionX;


	public SlidingPaneLayoutEx(Context context) {
		super(context);
	}

	public SlidingPaneLayoutEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingPaneLayoutEx(Context context, AttributeSet attrs,
		int defStyle) {
		super(context, attrs, defStyle);
		ViewConfiguration config = ViewConfiguration.get(context);
		mEdgeSlop = config.getScaledEdgeSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int mev = MotionEventCompat.getActionMasked(ev);

		switch (MotionEventCompat.getActionMasked(ev)) {
			case MotionEvent.ACTION_DOWN: {
				mInitialMotionX = ev.getX();

				break;
			}

			case MotionEvent.ACTION_MOVE: {
				final float x = ev.getX();
				final float y = ev.getY();

				// проверяем на следующие критерии:
				// 1) двигаемся за границей дальшей края
				// 2) закрыта
				// 3) дочерние элементы могут двигаться (для вложенных с
				// горизонтальной прокруткой)
				if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this,
					false,
					Math.round(x - mInitialMotionX), Math.round(x),
					Math.round(y))) {

					// при удовлетворяющих условиях вызываем родительский
					// метод с событием ACTION_CANCEL и отказом от открытия
					MotionEvent cancelEvent = MotionEvent.obtain(ev);
					cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
					return super.onInterceptTouchEvent(cancelEvent);
				}

			}
		}

		return super.onInterceptTouchEvent(ev);
	}
}

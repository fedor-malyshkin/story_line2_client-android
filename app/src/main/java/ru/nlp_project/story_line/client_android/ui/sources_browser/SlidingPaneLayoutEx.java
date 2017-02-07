package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by fedor on 08.02.17.
 */

public class SlidingPaneLayoutEx extends SlidingPaneLayout {
	private  int mEdgeSlop = 0;
	private float mInitialMotionX;
	private float mInitialMotionY;
	
	public SlidingPaneLayoutEx(Context context) {
		super(context);
	}

	public SlidingPaneLayoutEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingPaneLayoutEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ViewConfiguration config = ViewConfiguration.get(context);
		mEdgeSlop = config.getScaledEdgeSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (MotionEventCompat.getActionMasked(ev)) {
			case MotionEvent.ACTION_DOWN: {
				mInitialMotionX = ev.getX();
				mInitialMotionY = ev.getY();
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				final float x = ev.getX();
				final float y = ev.getY();
				// The user should always be able to "close" the pane, so we only check
				// for child scrollability if the pane is currently closed.
				if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
						Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {

					// How do we set super.mIsUnableToDrag = true?

					// send the parent a cancel event
					MotionEvent cancelEvent = MotionEvent.obtain(ev);
					cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
					return super.onInterceptTouchEvent(cancelEvent);
				}
			}
		}

		return super.onInterceptTouchEvent(ev);
	}
}

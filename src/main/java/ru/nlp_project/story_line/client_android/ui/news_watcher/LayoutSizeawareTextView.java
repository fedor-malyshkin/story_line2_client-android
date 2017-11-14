package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import ru.nlp_project.story_line.client_android.ui.utils.DimensionUtils;

public class LayoutSizeawareTextView extends AppCompatTextView {

	public LayoutSizeawareTextView(Context context) {
		super(context);
	}

	public LayoutSizeawareTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LayoutSizeawareTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setMaxLines(h/getLineHeight());
	}
}

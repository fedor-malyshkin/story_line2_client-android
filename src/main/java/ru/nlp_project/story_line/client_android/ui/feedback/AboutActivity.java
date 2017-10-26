package ru.nlp_project.story_line.client_android.ui.feedback;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.FeedbackComponent;

public class AboutActivity extends AppCompatActivity implements IFeedbackView {

	private static final String TAG = AboutActivity.class.getSimpleName();
	@Inject
	public IFeedbackPresenter presenter;


	@BindView(R.id.activity_feedback_about_text)
	TextView aboutTextView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		ButterKnife.bind(this);
		FeedbackComponent builder = DaggerBuilder
				.createFeedbackBuilder();
		builder.inject(this);
		presenter.bindView(this);

		// last step - after full interface initialization
		presenter.initializePresenter();
		// because we mix presenters and view -- use separate initialization (not in this#initializePresenter)
		presenter.loadAboutInfo();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}


	@Override
	public Context getContext() {
		return getBaseContext();
	}

	@Override
	public void clearTextFields() {
		// do nothing
	}

	@Override
	public void lockTextFields() {
		// do nothing
	}

	@Override
	public void unlockTextFields() {
		// do nothing
	}

	@Override
	public void convertSendToUnlockButton() {
		// do nothing
	}

	@Override
	public void convertUnlockToSendButton() {
		// do nothing
	}

	@Override
	public void loadAboutInfo(String text) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			aboutTextView.setText(Html.fromHtml(text,
					Html.FROM_HTML_MODE_COMPACT | Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));
		} else {
			aboutTextView.setText(Html.fromHtml(text));
		}
	}

	@Override
	public void loadAboutInfoError(Throwable throwable) {
		aboutTextView.setText(throwable.toString());
	}


}

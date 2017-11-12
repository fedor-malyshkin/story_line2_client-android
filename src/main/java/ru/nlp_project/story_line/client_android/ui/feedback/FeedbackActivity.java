package ru.nlp_project.story_line.client_android.ui.feedback;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.FeedbackComponent;
import ru.nlp_project.story_line.client_android.ui.utils.ThemeUtils;

public class FeedbackActivity extends AppCompatActivity implements IFeedbackView {

	private static final String TAG = FeedbackActivity.class.getSimpleName();
	@Inject
	public IFeedbackPresenter presenter;
	@BindView(R.id.activity_feedback_from)
	TextInputEditText fromEditText;
	@BindView(R.id.activity_feedback_message)
	TextInputEditText messageEditText;
	@BindView(R.id.activity_feedback_send)
	Button sendButton;
	private boolean creationTimeThemeDark;


	@Override
	protected void onResume() {
		super.onResume();
		boolean isNowThemeDark = ThemeUtils.isCurrentThemeDark(this);
		if (isNowThemeDark != creationTimeThemeDark) {
			ThemeUtils.setThemeAndRecreate(this, isNowThemeDark);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// must be called before "super.onCreate"
		creationTimeThemeDark = ThemeUtils.isCurrentThemeDark(this);
		ThemeUtils.setTheme(this, creationTimeThemeDark);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		ButterKnife.bind(this);
		FeedbackComponent builder = DaggerBuilder
				.createFeedbackBuilder();
		builder.inject(this);
		presenter.bindView(this);

		initializeControls();

		// last step - after full interface initialization
		presenter.initializePresenter();
	}

	private void initializeControls() {
		sendButton.setOnClickListener(this::onSend);
	}

	private void onSend(View view) {
		presenter.sendFeedback(fromEditText.getText().toString(), messageEditText.getText().toString());
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
		fromEditText.setText("");
		messageEditText.setText("");
	}

	@Override
	public void lockTextFields() {
		fromEditText.setEnabled(false);
		messageEditText.setEnabled(false);
	}


	@Override
	public void unlockTextFields() {
		fromEditText.setEnabled(true);
		messageEditText.setEnabled(true);
	}


	@Override
	public void convertSendToUnlockButton() {
		sendButton.setText(R.string.activity_feedback_send_unlock);
		sendButton.setOnClickListener(this::onUnlnock);
	}

	private void onUnlnock(View view) {
		presenter.unlock();
	}


	@Override
	public void convertUnlockToSendButton() {
		sendButton.setText(R.string.activity_feedback_send);
		sendButton.setOnClickListener(this::onSend);
	}

	@Override
	public void loadAboutInfo(String s) {
		// do nothing
	}

	@Override
	public void loadAboutInfoError(Throwable throwable) {
		// do nothing
	}
}

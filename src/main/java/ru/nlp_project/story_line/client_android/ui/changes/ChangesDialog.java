package ru.nlp_project.story_line.client_android.ui.changes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.ChangesComponent;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;

public class ChangesDialog extends DialogFragment implements IChangesView {

	@Inject
	IChangesPresenter presenter;

	@BindView(R.id.dialog_changes_text)
	TextView textView;
	@BindView(R.id.dialog_changes_ok)
	Button buttonOn;

	public ChangesDialog() {
	}


	public static ChangesDialog newInstance() {
		ChangesDialog frag = new ChangesDialog();
		return frag;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_changes, container);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ButterKnife.bind(this, view);
		ChangesComponent builder = DaggerBuilder.createChangesBuilder();
		builder.inject(this);
		presenter.bindView(this);

		initializeComponenets(view);

		// last step - after full interface initialization
		presenter.initializePresenter();
	}

	private void initializeComponenets(View view) {
		getDialog().setTitle(R.string.dialog_changes_title);
		buttonOn.setOnClickListener(presenter::onClickButtonOk);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		presenter.unbindView();
	}

	@Override
	public void setChangesText(String presentation) {
		textView.setText(presentation);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		presenter.onDismiss();
	}

	@Override
	public void hideDialog() {
		dismiss();
	}
}



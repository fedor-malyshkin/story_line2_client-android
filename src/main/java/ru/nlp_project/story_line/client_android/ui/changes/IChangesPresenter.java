package ru.nlp_project.story_line.client_android.ui.changes;

import android.view.View;
import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface IChangesPresenter extends IPresenter<IChangesView> {

	void onDismiss();

	void onClickButtonOk(View view);
}

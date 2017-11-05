package ru.nlp_project.story_line.client_android.ui.changes;

import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.change_records.IChangeRecordsInteractor;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.ChangesScope;

@ChangesScope
public class ChangesPresenterImpl implements IChangesPresenter {


	@Inject
	IChangeRecordsInteractor interactor;
	private IChangesView view;

	@Inject
	public ChangesPresenterImpl() {
	}

	@Override
	public void bindView(IChangesView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	@Override
	public void initializePresenter() {
		String presentation = formatPresentation(interactor.getUnseenRecords());
		view.setChangesText(presentation);
	}

	private String formatPresentation(List<ChangeRecordBusinessModel> unseenRecords) {
		return unseenRecords.toString();
	}

	@Override
	public void onDismiss() {

	}
}

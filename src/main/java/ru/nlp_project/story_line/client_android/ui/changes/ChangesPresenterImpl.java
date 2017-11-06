package ru.nlp_project.story_line.client_android.ui.changes;

import android.view.View;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.change_records.IChangeRecordsInteractor;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel.ChangeRecordTypes;
import ru.nlp_project.story_line.client_android.dagger.ChangesScope;

@ChangesScope
public class ChangesPresenterImpl implements IChangesPresenter {


	@Inject
	IChangeRecordsInteractor interactor;
	private IChangesView view;
	private List<ChangeRecordBusinessModel> records;

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
		records = interactor.getUnseenRecords();
		String presentation = formatPresentation(records);
		view.setChangesText(presentation);
	}

	protected String formatPresentation(List<ChangeRecordBusinessModel> unseenRecords) {
		StringBuilder result = new StringBuilder();
		for (ChangeRecordBusinessModel record : unseenRecords) {
			result.append(formatPresentation(record));
		}
		return result.toString();
	}

	protected String formatPresentation(ChangeRecordBusinessModel record) {
		String result = "";
		if (record.getType() == ChangeRecordTypes.NEW_SOURCE) {
			result = String.format("Был добавлен новый источник - сайт '%s'\n", record.getMessage());
		}
		return result;
	}

	@Override
	public void onDismiss() {
		markRecordsAsSeen();
	}

	@Override
	public void onClickButtonOk(View eventView) {
		markRecordsAsSeen();
		view.hideDialog();
	}

	private void markRecordsAsSeen() {
		if (records == null || records.isEmpty()) {
			return;
		}
		interactor.markRecordsAsSeen(records);
	}
}

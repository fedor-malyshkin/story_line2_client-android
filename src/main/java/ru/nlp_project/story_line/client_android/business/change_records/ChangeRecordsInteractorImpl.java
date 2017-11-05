package ru.nlp_project.story_line.client_android.business.change_records;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel.ChangeRecordTypes;
import ru.nlp_project.story_line.client_android.data.change_records.IChangeRecordsRepository;

public class ChangeRecordsInteractorImpl implements IChangeRecordsInteractor {

	@Inject
	IChangeRecordsRepository repository;


	@Inject
	public ChangeRecordsInteractorImpl() {
	}


	@Override
	public void initializeInteractor() {
	}

	@Override
	public void addNewSourceRecord(Date additionDate, String sourceName) {
		repository.insertNewRecord(additionDate, ChangeRecordTypes.NEW_SOURCE, sourceName);
	}

	@Override
	public boolean hasUnseenRecords() {
		return repository.hasUnseenChangeRecords();
	}

	@Override
	public List<ChangeRecordBusinessModel> getUnseenRecords() {
		return repository.getUnseenChangeRecords();
	}

	@Override
	public void markRecordsAsSeen(List<ChangeRecordBusinessModel> records) {
		ArrayList<Long> ids = new ArrayList<>();
		for (ChangeRecordBusinessModel s : records) {
			ids.add(s.getId());
		}
		repository.setChangeRecordsAsSeen(ids);
	}
}

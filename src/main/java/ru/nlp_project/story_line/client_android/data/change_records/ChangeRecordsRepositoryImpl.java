package ru.nlp_project.story_line.client_android.data.change_records;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel.ChangeRecordTypes;
import ru.nlp_project.story_line.client_android.data.models.ChangeRecordDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;

public class ChangeRecordsRepositoryImpl implements IChangeRecordsRepository {

	@Inject
	ILocalDBStorage localDBStorage;


	@Inject
	public ChangeRecordsRepositoryImpl() {
	}

	@Override
	public void initializeRepository() {

	}

	@Override
	public void setChangeRecordsAsSeen(List<Long> ids) {
		localDBStorage.setChangeRecordsAsSeen(ids);
	}

	@Override
	public void insertNewRecord(Date additionDate, ChangeRecordTypes type, String message) {
		ChangeRecordDataModel dataModel = new ChangeRecordDataModel(null, type.getIndex(), additionDate,
				false, message);
		localDBStorage.addChangeRecord(dataModel);
	}

	@Override
	public boolean hasUnseenChangeRecords() {
		return localDBStorage.hasUnseenChangeRecords();
	}

	@Override
	public List<ChangeRecordBusinessModel> getUnseenChangeRecords() {
		List<ChangeRecordDataModel> recs = localDBStorage.getUnseenChangeRecords();
		return ChangeRecordDataModel.convertList(recs);
	}
}

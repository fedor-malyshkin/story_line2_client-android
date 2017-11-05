package ru.nlp_project.story_line.client_android.business.change_records;

import java.util.Date;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;

public interface IChangeRecordsInteractor extends IInteractor {

	void addNewSourceRecord(Date additionDate, String sourceName);

	boolean hasUnseenRecords();

	List<ChangeRecordBusinessModel> getUnseenRecords();

	void markRecordsAsSeen(List<ChangeRecordBusinessModel> records);
}

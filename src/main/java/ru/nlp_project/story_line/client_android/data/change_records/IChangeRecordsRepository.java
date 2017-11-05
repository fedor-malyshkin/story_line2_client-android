package ru.nlp_project.story_line.client_android.data.change_records;

import java.util.Date;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;
import ru.nlp_project.story_line.client_android.data.IRepository;

public interface IChangeRecordsRepository extends IRepository {

	void insertNewRecord(Date additionDate, ChangeRecordBusinessModel.ChangeRecordTypes type,
			String message);

	boolean hasUnseenChangeRecords();

	List<ChangeRecordBusinessModel> getUnseenChangeRecords();

	void setChangeRecordsAsSeen(List<Long> ids);

}

package ru.nlp_project.story_line.client_android.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;

public class ChangeRecordDataModel {

	/**
	 * id for cupboard
	 */
	private Long _id;
	/**
	 * {@link ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel.ChangeRecordTypes}
	 */
	private int type;
	private Date additionDate;
	private boolean seen = false;
	private String message;

	// necessary for Cupboard
	public ChangeRecordDataModel() {
	}



	public ChangeRecordDataModel(Long _id, int type, Date additionDate, boolean seen,
			String message) {
		this._id = _id;
		this.type = type;
		this.additionDate = additionDate;
		this.seen = seen;
		this.message = message;
	}

	public static List<ChangeRecordBusinessModel> convertList(List<ChangeRecordDataModel> in) {

		List<ChangeRecordBusinessModel> result = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			result = in.stream().map(ChangeRecordDataModel::convert).collect(Collectors.toList());
		} else {
			result = new ArrayList<>();
			for (ChangeRecordDataModel s : in) {
				result.add(s.convert());
			}
		}

		return result;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getAdditionDate() {
		return additionDate;
	}

	public void setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ChangeRecordBusinessModel convert() {
		return new ChangeRecordBusinessModel(_id,
				ChangeRecordBusinessModel.ChangeRecordTypes.getByIndex(type), additionDate, seen, message);
	}
}



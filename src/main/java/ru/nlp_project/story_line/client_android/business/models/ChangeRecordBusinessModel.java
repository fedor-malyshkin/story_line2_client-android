package ru.nlp_project.story_line.client_android.business.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import ru.nlp_project.story_line.client_android.data.models.ChangeRecordDataModel;

public class ChangeRecordBusinessModel {

	/**
	 * id for cupboard
	 */
	private Long id;
	private ChangeRecordTypes type;
	private Date additionDate;
	private boolean seen = false;
	private String message;
	public ChangeRecordBusinessModel(Long id,
			ChangeRecordTypes type, Date additionDate, boolean seen, String message) {
		this.id = id;
		this.type = type;
		this.additionDate = additionDate;
		this.seen = seen;
		this.message = message;
	}

	public static List<ChangeRecordDataModel> convertList(List<ChangeRecordBusinessModel> in) {
		List<ChangeRecordDataModel> result = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			result = in.stream().map(
					s -> new ChangeRecordDataModel(s.getId(), s.getType().index, s.getAdditionDate(),
							s.isSeen(),
							s.getMessage())).collect(Collectors.toList());
		} else {
			result = new ArrayList<>();
			for (ChangeRecordBusinessModel s : in) {
				result.add(
						new ChangeRecordDataModel(s.getId(), s.getType().index, s.getAdditionDate(), s.isSeen(),
								s.getMessage()));
			}
		}
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChangeRecordTypes getType() {
		return type;
	}

	public void setType(
			ChangeRecordTypes type) {
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

	public enum ChangeRecordTypes {
		NEW_SOURCE(0), CHANGES(1), NEWS(2);

		private final int index;


		ChangeRecordTypes(int indexIn) {
			this.index = indexIn;
		}

		public static ChangeRecordTypes getByIndex(int i) {
			for (ChangeRecordTypes type : values()) {
				if (i == type.index) {
					return type;
				}
			}
			// unknown type
			return null;
		}

		public int getIndex() {
			return index;
		}
	}
}

package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceDataModel {

	/**
	 * id for cupboard
	 */
	private Long _id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("title")
	private String title;
	@JsonProperty("title_short")
	private String titleShort;
	/**
	 * Addition date.
	 */
	private Date additionDate;
	private boolean enabled = true;
	private int _order = -1;

	// necessary for unmarshalling from JSON
	public SourceDataModel() {
	}

	public SourceDataModel(Long id, String name, String title, String titleShort) {
		this._id = id;
		this.name = name;
		this.title = title;
		this.titleShort = titleShort;

	}

	public SourceDataModel(Long id, String name, String title, String titleShort, boolean enabled,
			int order, Date additionDate) {
		this(id, name, title, titleShort);
		this.enabled = enabled;
		this._order = order;
		this.additionDate = additionDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleShort() {
		return titleShort;
	}

	public void setTitleShort(String titleShort) {
		this.titleShort = titleShort;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		this._order = order;
	}

	public Long getId() {
		return _id;
	}

	public void setId(Long _id) {
		this._id = _id;
	}

	public SourceBusinessModel convert() {
		return new SourceBusinessModel(getId(), getName(), getTitle(), getTitleShort(), isEnabled(),
				getOrder(), getAdditionDate());
	}

	public Date getAdditionDate() {
		return additionDate;
	}

	public void setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
	}

	/**
	 * see analogious {@link SourceBusinessModel#updatePresentationData(SourceBusinessModel)}
	 */
	public void updatePresentationData(SourceDataModel other) {
		if (null == other) {
			throw new IllegalArgumentException("SourceDataModel 'other' must be not null.");
		}
		this.title = other.title;
		this.titleShort = other.titleShort;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SourceDataModel{");
		sb.append("_id=").append(_id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", titleShort='").append(titleShort).append('\'');
		sb.append(", additionDate=").append(additionDate);
		sb.append(", enabled=").append(enabled);
		sb.append(", _order=").append(_order);
		sb.append('}');
		return sb.toString();
	}
}



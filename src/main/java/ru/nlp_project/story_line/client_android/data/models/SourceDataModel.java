package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceDataModel {
	private Long _id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("title")
	private String title;
	@JsonProperty("title_short")
	private String titleShort;
	private boolean enabled = true;
	private int _order = -1;
	@JsonIgnore
	private long requestId;

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
			int order) {
		this(id, name, title, titleShort);
		this.enabled = enabled;
		this._order = order;
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

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
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
}



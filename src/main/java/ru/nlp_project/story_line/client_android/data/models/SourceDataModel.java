package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceDataModel {


	// neccessary for unmarshalling from JSON
	public SourceDataModel() {
	}

	public SourceDataModel(String name, String title, String titleShort) {
		this.name = name;
		this.title = title;
		this.titleShort = titleShort;
	}

	private Long _id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("title")
	private String title;
	@JsonProperty("title_short")
	private String titleShort;

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleShort() {
		return titleShort;
	}

	@JsonIgnore
	private long requestId;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
}

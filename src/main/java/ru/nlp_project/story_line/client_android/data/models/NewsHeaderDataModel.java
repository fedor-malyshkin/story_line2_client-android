package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsHeaderDataModel {

	@JsonIgnore
	private Long _id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("source")
	private String source;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("_id")
	private String serverId;

	public NewsHeaderDataModel(Long _id, String title, String source, Date date,
			String serverId) {
		this._id = _id;
		this.title = title;
		this.source = source;
		this.date = date;
		this.serverId = serverId;
	}

	// neccessary for unmarshalling from JSON
	public NewsHeaderDataModel() {
	}

	public Long getId() {
		return _id;
	}

	public String getTitle() {
		return title;
	}

	public String getSource() {
		return source;
	}

	public Date getDate() {
		return date;
	}

	public String getServerId() {
		return serverId;
	}

}

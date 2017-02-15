package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsArticleDataModel {

	// neccessary for unmarshalling from JSON
	public NewsArticleDataModel() {
	}

	public NewsArticleDataModel(Long id, String content, String path, String title, Date date,
		Date processingDate, String source, String serverId) {
		this.id = id;
		this.content = content;
		this.path = path;
		this.title = title;
		this.date = date;
		this.processingDate = processingDate;
		this.source = source;
		this.serverId = serverId;
	}


	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getPath() {
		return path;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	public Date getProcessingDate() {
		return processingDate;
	}

	public String getSource() {
		return source;
	}

	public String getServerId() {
		return serverId;
	}

	private Long id;
	@JsonProperty("content")
	private String content;
	@JsonProperty("path")
	private String path;
	@JsonProperty("title")
	private String title;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("processing_date")
	private Date processingDate;
	@JsonProperty("name")
	private String source;
	@JsonProperty("id")
	private String serverId;
}

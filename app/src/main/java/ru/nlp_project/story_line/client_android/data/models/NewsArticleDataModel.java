package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsArticleDataModel {


	public NewsArticleDataModel() {
	}

	private Long id;
	@JsonProperty("titles")
	private String title;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("processed_date")
	private Date processedDate;
	@JsonProperty("content")
	private String content;
	@JsonProperty("id")
	private String serverId;
}

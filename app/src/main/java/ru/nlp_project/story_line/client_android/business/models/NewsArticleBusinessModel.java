package ru.nlp_project.story_line.client_android.business.models;

import java.util.Date;

public class NewsArticleBusinessModel {

	private String content;
	private String path;
	private String title;
	private Date date;
	private Date processingDate;
	private String source;
	private String serverId;

	public NewsArticleBusinessModel(String content, String path, String title, Date date,
		Date processingDate, String source, String serverId) {
		this.content = content;
		this.path = path;
		this.title = title;
		this.date = date;
		this.processingDate = processingDate;
		this.source = source;
		this.serverId = serverId;
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
}

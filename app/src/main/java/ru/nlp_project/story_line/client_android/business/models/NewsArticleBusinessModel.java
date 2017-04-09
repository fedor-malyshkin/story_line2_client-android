package ru.nlp_project.story_line.client_android.business.models;

import java.util.Date;

public class NewsArticleBusinessModel {

	private String content;
	private String path;
	private String title;
	private Date publicationDate;
	private String source;
	private String serverId;

	public NewsArticleBusinessModel(String content, String path, String title,
		Date publicationDate, String source, String serverId) {
		this.content = content;
		this.path = path;
		this.title = title;
		this.publicationDate = publicationDate;
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

	public Date getPublicationDate() {
		return publicationDate;
	}

	public String getSource() {
		return source;
	}

	public String getServerId() {
		return serverId;
	}
}

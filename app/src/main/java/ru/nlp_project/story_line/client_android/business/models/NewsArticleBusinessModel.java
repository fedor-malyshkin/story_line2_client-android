package ru.nlp_project.story_line.client_android.business.models;

import java.util.Date;

public class NewsArticleBusinessModel {

	private String content;
	private String path;
	private String title;
	private Date publicationDate;
	private String source;
	private String url;
	private String imageUrl;
	private String serverId;

	public NewsArticleBusinessModel(String content, String path, String title,
		Date publicationDate, String source, String serverId, String url, String imageUrl) {
		this.content = content;
		this.path = path;
		this.title = title;
		this.publicationDate = publicationDate;
		this.source = source;
		this.url = url;
		this.imageUrl = imageUrl;
		this.serverId = serverId;
	}

	public String getUrl() {
		return url;
	}

	public String getImageUrl() {
		return imageUrl;
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

	@Override
	public String toString() {
		return "NewsArticleBusinessModel{" +
			"content='" + content + '\'' +
			", path='" + path + '\'' +
			", title='" + title + '\'' +
			", publicationDate=" + publicationDate +
			", source='" + source + '\'' +
			", url='" + url + '\'' +
			", imageUrl='" + imageUrl + '\'' +
			", serverId='" + serverId + '\'' +
			'}';
	}
}

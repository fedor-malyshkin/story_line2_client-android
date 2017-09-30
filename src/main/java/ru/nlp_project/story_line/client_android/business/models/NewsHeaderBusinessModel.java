package ru.nlp_project.story_line.client_android.business.models;

import java.util.Date;

/**
 * Заголовок статьи со всей служебной информацией необходимой для поверхностного ознакомления.
 */

public class NewsHeaderBusinessModel {

	private String title;
	private String source;
	private Date publicationDate;
	private String serverId;
	private String imageUrl;

	public String getTitle() {
		return title;
	}

	public String getSource() {
		return source;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public String getServerId() {
		return serverId;
	}

	public NewsHeaderBusinessModel(String title, String source, Date publicationDate,
		String serverId) {
		this.title = title;
		this.source = source;
		this.publicationDate = publicationDate;
		this.serverId = serverId;
	}


}

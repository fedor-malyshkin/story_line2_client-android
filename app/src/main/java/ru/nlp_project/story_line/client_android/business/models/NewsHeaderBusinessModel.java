package ru.nlp_project.story_line.client_android.business.models;

import java.util.Date;

/**
 * Заголовок статьи со всей служебной информацией необходимой для поверхностного ознакомления.
 */

public class NewsHeaderBusinessModel {

	private String title;
	private String source;
	private Date date;
	private String serverId;

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

	public NewsHeaderBusinessModel(String title, String source, Date date,
		String serverId) {
		this.title = title;
		this.source = source;
		this.date = date;
		this.serverId = serverId;
	}


}

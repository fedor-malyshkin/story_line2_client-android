package ru.nlp_project.story_line.client_android.business.models;

/**
 * Заголовок статьи со всей служебной информацией необходимой для поверхностного ознакомления.
 */

public class NewsHeaderBusinessModel {

	private String name;
	private String serverId;

	public NewsHeaderBusinessModel() {
	}

	public String getServerId() {
		return serverId;
	}

	public String getName() {
		return name;
	}


	public NewsHeaderBusinessModel(String name, String serverId) {
		this.name = name;
		this.serverId = serverId;
	}
}

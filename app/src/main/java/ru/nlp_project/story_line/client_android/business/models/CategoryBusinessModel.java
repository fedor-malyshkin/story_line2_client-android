package ru.nlp_project.story_line.client_android.business.models;

public class CategoryBusinessModel {

	public CategoryBusinessModel(String name, String serverId) {
		this.name = name;
		this.serverId = serverId;
	}

	private String name;
	private String serverId;


	public String getName() {
		return name;
	}

	public String getServerId() {
		return serverId;
	}

}

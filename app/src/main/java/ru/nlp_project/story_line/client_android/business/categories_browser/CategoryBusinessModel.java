package ru.nlp_project.story_line.client_android.business.categories_browser;

/**
 * Created by fedor on 10.02.17.
 */
public class CategoryBusinessModel {

	public CategoryBusinessModel(Long id, String name, String serverId) {
		this.id = id;
		this.name = name;
		this.serverId = serverId;
	}

	private Long id;
	private String name;
	private String serverId;


	public String getName() {
		return name;
	}

	public String getServerId() {
		return serverId;
	}
}

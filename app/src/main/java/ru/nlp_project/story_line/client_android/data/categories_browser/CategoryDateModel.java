package ru.nlp_project.story_line.client_android.data.categories_browser;

/**
 * Created by fedor on 10.02.17.
 */
public class CategoryDateModel {

	public CategoryDateModel(Long id, String name, String serverId) {
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

	public Long getId() {
		return id;
	}
}

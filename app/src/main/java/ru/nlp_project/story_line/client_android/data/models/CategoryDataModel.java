package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fedor on 10.02.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDataModel {

	public CategoryDataModel(Long id, String name, String serverId) {
		this.id = id;
		this.name = name;
		this.serverId = serverId;
	}

	public CategoryDataModel() {
	}

	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("id")
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

	@Override
	public String toString() {
		return "CategoryDataModel{" +
			"id=" + id +
			", name='" + name + '\'' +
			", serverId='" + serverId + '\'' +
			'}';
	}
}

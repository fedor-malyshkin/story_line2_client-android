package ru.nlp_project.story_line.client_android.data.models;

/**
 * Created by fedor on 04.02.17.
 */

public class NewsHeaderDataModel {

	private Long _id;
	private String name;
	private String serverId;

	public NewsHeaderDataModel(Long _id, String name, String serverId) {
		this._id = _id;
		this.name = name;
		this.serverId = serverId;
	}

	public Long get_id() {
		return _id;
	}

	public String getName() {
		return name;
	}

	public String getServerId() {
		return serverId;
	}
}

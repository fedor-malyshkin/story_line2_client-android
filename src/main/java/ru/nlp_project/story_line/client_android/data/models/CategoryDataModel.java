package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDataModel {

	@JsonIgnore
	private Long _id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("_id")
	private String serverId;

	public CategoryDataModel(Long id, String name, String serverId) {
		this._id = id;
		this.name = name;
		this.serverId = serverId;
	}


	// neccessary for unmarshalling from JSON
	public CategoryDataModel() {
	}

	public String getName() {
		return name;
	}

	public String getServerId() {
		return serverId;
	}

	public Long getId() {
		return _id;
	}

	@Override
	public String toString() {
		return "CategoryDataModel{" +
				"id=" + _id +
				", name='" + name + '\'' +
				", serverId='" + serverId + '\'' +
				'}';
	}

	@JsonIgnore
	private long requestId;
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

}

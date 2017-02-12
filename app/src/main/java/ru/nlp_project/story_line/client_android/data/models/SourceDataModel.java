package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fedor on 10.02.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceDataModel {

	public SourceDataModel(Long id, String domain, String shortName, String name,
		String serverId) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.domain = domain;
		this.serverId = serverId;
	}

	public SourceDataModel() {
	}

	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("short_name")
	private String shortName;
	@JsonProperty("domain")
	private String domain;
	@JsonProperty("id")
	private String serverId;


	public String getShortName() {
		return shortName;
	}


	public String getDomain() {
		return domain;
	}

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
		return "SourceDataModel{" +
			"id=" + id +
			", name='" + name + '\'' +
			", shortName='" + shortName + '\'' +
			", domain='" + domain + '\'' +
			", serverId='" + serverId + '\'' +
			'}';
	}
}

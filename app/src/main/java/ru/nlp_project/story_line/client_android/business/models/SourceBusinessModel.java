package ru.nlp_project.story_line.client_android.business.models;

public class SourceBusinessModel {

	private String name;
	private String shortName;
	private String domain;
	private String serverId;

	public SourceBusinessModel(String domain, String shortName, String name,
		String serverId) {
		this.name = name;
		this.shortName = shortName;
		this.domain = domain;
		this.serverId = serverId;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDomain() {
		return domain;
	}

	public String getServerId() {
		return serverId;
	}
}

package ru.nlp_project.story_line.client_android.business.models;

public class SourceBusinessModel {

	private String name;
	private String title;
	private String titleShort;

	public SourceBusinessModel(String name, String title, String titleShort) {
		this.name = name;
		this.title = title;
		this.titleShort = titleShort;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleShort() {
		return titleShort;
	}

	public String getName() {

		return name;
	}
}

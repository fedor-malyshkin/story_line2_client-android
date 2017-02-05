package ru.nlp_project.story_line.client_android.data.repositories.news_tape;

/**
 * Created by fedor on 04.02.17.
 */

public class NewsArticleDataModel {
	private Long _id;
	private String name;

	public NewsArticleDataModel() {
	}

	public Long getId() {
		return _id;
	}

	public void setId(Long _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NewsArticleDataModel(Long id, String name) {
		this._id = id;
		this.name = name;
	}
}

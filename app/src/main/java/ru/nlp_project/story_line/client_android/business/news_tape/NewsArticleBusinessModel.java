package ru.nlp_project.story_line.client_android.business.news_tape;

/**
 * Заголовок статьи со всей служебной информацией необходимой для поверхностного ознакомления.
 */

public class NewsArticleBusinessModel {
	private Long _id;
	private String name;

	public NewsArticleBusinessModel() {
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

	public NewsArticleBusinessModel(Long id, String name) {
		this._id = id;
		this.name = name;
	}
}

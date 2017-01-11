package ru.nlp_project.story_line.client_android.data.model;

/**
 * Заголовок статьи со всей служебной информацией необходимой для поверхностного ознакомления.
 */

public class NewsArticleHeader {
	private Long _id;
	private String name;

	public NewsArticleHeader() {
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

	public NewsArticleHeader(Long id, String name) {
		this._id = id;
		this.name = name;
	}
}

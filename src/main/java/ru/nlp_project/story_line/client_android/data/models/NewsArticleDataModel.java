package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;

@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsArticleDataModel {

	/**
	 * id for cupboard
	 */
	@JsonIgnore
	private Long _id;
	@JsonProperty("content")
	private String content;
	@JsonProperty("path")
	private String path;
	@JsonProperty("title")
	private String title;
	@JsonProperty("publication_date")
	private Date publicationDate;
	@JsonProperty("url")
	private String url;
	@JsonProperty("image_url")
	private String imageUrl;
	@JsonProperty("source")
	private String source;
	/**
	 * id for server side identification (mongodb/elasticsearch)
	 */
	@JsonProperty("_id")
	private String serverId;

	// neccessary for unmarshalling from JSON
	public NewsArticleDataModel() {
	}

	public NewsArticleDataModel(Long id, String content, String path, String title,
			Date publicationDate, String source, String serverId) {
		this._id = id;
		this.content = content;
		this.path = path;
		this.title = title;
		this.publicationDate = publicationDate;
		this.source = source;
		this.url = url;
		this.imageUrl = imageUrl;
		this.serverId = serverId;
	}

	public NewsArticleBusinessModel convert() {
		return new NewsArticleBusinessModel(getContent(), getPath(), getTitle(), getPublicationDate(),
				getSource(), getServerId(), getUrl(), getImageUrl());
	}

	public Long getId() {
		return _id;
	}

	public String getContent() {
		return content;
	}

	public String getPath() {
		return path;
	}

	public String getTitle() {
		return title;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public String getSource() {
		return source;
	}

	public String getServerId() {
		return serverId;
	}

	public String getUrl() {
		return url;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

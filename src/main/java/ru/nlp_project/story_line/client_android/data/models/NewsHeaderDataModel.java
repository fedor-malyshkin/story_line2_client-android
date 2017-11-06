package ru.nlp_project.story_line.client_android.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsHeaderDataModel {

	/**
	 * id for cupboard
	 */
	@JsonIgnore
	private Long _id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("source")
	private String source;
	@JsonProperty("image_url")
	private String imageUrl;


	@JsonProperty("publication_date")
	private Date publicationDate;
	/**
	 * id for server side identification (mongodb/elasticsearch)
	 */
	@JsonProperty("_id")
	private String serverId;

	public NewsHeaderDataModel(Long _id, String title, String source, Date publicationDate,
			String imageUrl,
			String serverId) {
		this._id = _id;
		this.title = title;
		this.source = source;
		this.publicationDate = publicationDate;
		this.imageUrl = imageUrl;
		this.serverId = serverId;
	}

	// neccessary for unmarshalling from JSON & Cupboard
	public NewsHeaderDataModel() {
	}

	public Long getId() {
		return _id;
	}

	public String getTitle() {
		return title;
	}

	public String getSource() {
		return source;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getServerId() {
		return serverId;
	}

	public NewsHeaderBusinessModel convert() {
		return new NewsHeaderBusinessModel(getTitle(), getSource(),
				getPublicationDate(), getImageUrl(),
				getServerId());
	}

}

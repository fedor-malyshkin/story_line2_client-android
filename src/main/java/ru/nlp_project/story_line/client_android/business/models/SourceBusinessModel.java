package ru.nlp_project.story_line.client_android.business.models;

public class SourceBusinessModel {

	/**
	 * Имя источника (системное).
	 */
	private String name;
	/**
	 * Наименоение видимое (возможно длинное).
	 */
	private String title;
	/**
	 * Наименоение видимое (возможно короткое).
	 */
	private String titleShort;

	/**
	 * Активность данного источника.
	 */
	private boolean enabled = true;
	/**
	 * Порядок источника в списке.
	 */
	private int order = -1;

	public SourceBusinessModel(String name, String title, String titleShort) {
		this.name = name;
		this.title = title;
		this.titleShort = titleShort;
	}

	public SourceBusinessModel(String name, String title, String titleShort, boolean enabled,
			int order) {
		this(name, title, titleShort);
		this.enabled = enabled;
		this.order = order;
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

	public boolean isEnabled() {
		return enabled;
	}

	public int getOrder() {
		return order;
	}
}

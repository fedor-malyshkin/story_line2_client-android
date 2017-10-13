package ru.nlp_project.story_line.client_android.business.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public class SourceBusinessModel {

	private Long id;
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

	public SourceBusinessModel(Long id, String name, String title, String titleShort) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.titleShort = titleShort;
	}

	public SourceBusinessModel(Long id, String name, String title, String titleShort, boolean enabled,
			int order) {
		this(id, name, title, titleShort);
		this.enabled = enabled;
		this.order = order;
	}

	public static List<SourceDataModel> convertList(List<SourceBusinessModel> in) {
		List<SourceDataModel> result = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			result = in.stream().map(
					s -> new SourceDataModel(s.getId(), s.getName(), s.getTitle(), s.getTitleShort(),
							s.isEnabled(),
							s.getOrder())).collect(Collectors.toList());
		} else {
			result = new ArrayList<>();
			for (SourceBusinessModel s : in) {
				result.add(new SourceDataModel(s.getId(), s.getName(), s.getTitle(), s.getTitleShort(),
						s.isEnabled(),
						s.getOrder()));
			}
		}
		return result;
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

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SourceBusinessModel that = (SourceBusinessModel) o;

		if (enabled != that.enabled) {
			return false;
		}
		if (order != that.order) {
			return false;
		}
		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (!name.equals(that.name)) {
			return false;
		}
		if (!title.equals(that.title)) {
			return false;
		}
		return titleShort.equals(that.titleShort);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + name.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + titleShort.hashCode();
		result = 31 * result + (enabled ? 1 : 0);
		result = 31 * result + order;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SourceBusinessModel{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", titleShort='").append(titleShort).append('\'');
		sb.append(", enabled=").append(enabled);
		sb.append(", order=").append(order);
		sb.append('}');
		return sb.toString();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	/**
	 * see analogious {@link SourceDataModel#updatePresentationData(SourceDataModel)}
 	 */

	public void updatePresentationData(SourceBusinessModel other) {
		if (null == other) {
			throw new IllegalArgumentException("SourceBusinessModel 'other' must be not null.");
		}
		this.title = other.title;
		this.titleShort = other.titleShort;
	}

	public void updateSystemData(SourceBusinessModel other) {
		if (null == other) {
			throw new IllegalArgumentException("SourceBusinessModel 'other' must be not null.");
		}
		this.enabled = other.enabled;
		this.setOrder(other.getOrder());
	}

}

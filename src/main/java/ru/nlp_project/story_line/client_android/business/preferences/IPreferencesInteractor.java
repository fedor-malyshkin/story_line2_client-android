package ru.nlp_project.story_line.client_android.business.preferences;

import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

public interface IPreferencesInteractor {

	/**
	 * Сформировать поток настроек источников.
	 *
	 * Поток формируется посредством объединения данных из локального хранилища и сетевого (с
	 * выставлением соотвествующих полей (enabled, order)).
	 */
	Observable<SourceBusinessModel> createCombinedSourcePreferencesStream();

	/**
	 * Обновить настройки источников данных, включая их активность и порядок.
	 */
	void upsetSources(List<SourceBusinessModel> sources);

}

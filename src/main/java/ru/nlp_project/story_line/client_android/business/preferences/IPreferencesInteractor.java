package ru.nlp_project.story_line.client_android.business.preferences;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.preferences.IPreferencesPresenter;

public interface IPreferencesInteractor extends IInteractor<IPreferencesPresenter> {

	/**
	 * Сформировать поток настроек источников.
	 *
	 * Поток формируется посредством объединения данных из локального хранилища и сетевого (с
	 * выставлением соотвествующих полей (enabled, order)).
	 */
	Observable<SourceBusinessModel> createCombinedSourcesStream();

	void updateSourceState(String key, boolean checked);
}

package ru.nlp_project.story_line.client_android.business.preferences;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

public interface IPreferencesInteractor {
	Observable<SourceBusinessModel> createSourceStream();
}

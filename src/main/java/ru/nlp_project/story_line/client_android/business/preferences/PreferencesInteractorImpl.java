package ru.nlp_project.story_line.client_android.business.preferences;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.PreferencesScope;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;

@PreferencesScope
public class PreferencesInteractorImpl implements IPreferencesInteractor {

	@Inject
	ISourcesBrowserRepository repository;

	private ObservableTransformer<SourceDataModel,
			SourceBusinessModel> transformer = new PreferencesInteractorImpl.DataToBusinessModelTransformer();


	@Inject
	public PreferencesInteractorImpl() {
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStream() {
		return repository.createSourceStream().compose(transformer);
	}

	private class DataToBusinessModelTransformer implements
			ObservableTransformer<SourceDataModel,
					SourceBusinessModel> {

		@Override
		public ObservableSource<SourceBusinessModel> apply(
				Observable<SourceDataModel> upstream) {
			return upstream.map(
					data -> new SourceBusinessModel(data.getName(), data.getTitle(), data
							.getTitleShort())
			);
		}
	}

}

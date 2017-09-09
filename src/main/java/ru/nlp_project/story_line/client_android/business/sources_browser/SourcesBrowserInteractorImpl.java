package ru.nlp_project.story_line.client_android.business.sources_browser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserScope;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;

@SourcesBrowserScope
public class SourcesBrowserInteractorImpl implements ISourcesBrowserInteractor {

	@Inject
	ISourcesBrowserRepository repository;
	private ObservableTransformer<SourceDataModel,
			SourceBusinessModel> transformer = new DataToBusinessModelTransformer();


	@Inject
	public SourcesBrowserInteractorImpl() {
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStream() {
		return repository.createSourceStreamRemoteCached().compose(transformer);
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStreamFromCache() {
		return repository.createSourceStreamLocal().compose(transformer);
	}

	private class DataToBusinessModelTransformer implements
			ObservableTransformer<SourceDataModel,
					SourceBusinessModel> {

		@Override
		public ObservableSource<SourceBusinessModel> apply(
				Observable<SourceDataModel> upstream) {
			return upstream.map(
					data -> new SourceBusinessModel(data.getId(), data.getName(), data.getTitle(), data
							.getTitleShort(), data.isEnabled(), data.getOrder())
			);
		}
	}
}

package ru.nlp_project.story_line.client_android.business.news_tape;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.news_tape.INewsTapeRepository;

@NewsTapeScope
public class NewsTapeInteractorImpl implements INewsTapeInteractor {

	@Inject
	INewsTapeRepository repository;
	private ObservableTransformer<NewsHeaderDataModel,
			NewsHeaderBusinessModel> transformer = new DataToBusinessModelTransformer();


	@Inject
	public NewsTapeInteractorImpl() {
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createNewsHeaderRemoteCachedStream(
			String sourceDomain) {
		return repository.createNewsHeaderRemoteCachedStream(sourceDomain, null).compose
				(transformer);
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createAdditionNewsHeaderRemoteCachedStream(String sourceDomain,
			String  lastNewsHeaderServerId) {
		return repository.createNewsHeaderRemoteCachedStream(sourceDomain, lastNewsHeaderServerId).compose
				(transformer);
	}

	private class DataToBusinessModelTransformer implements
			ObservableTransformer<NewsHeaderDataModel,
					NewsHeaderBusinessModel> {

		@Override
		public ObservableSource<NewsHeaderBusinessModel> apply(
				Observable<NewsHeaderDataModel> upstream) {
			return upstream.map(
					data -> new NewsHeaderBusinessModel(data.getTitle(), data.getSource(),
							data.getPublicationDate(),
							data.getServerId()));
		}
	}
}

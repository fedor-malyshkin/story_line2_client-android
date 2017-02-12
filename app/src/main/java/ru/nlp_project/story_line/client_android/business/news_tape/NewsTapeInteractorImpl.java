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

	private ObservableTransformer<NewsHeaderDataModel,
		NewsHeaderBusinessModel> transformer = new DataToBusinessModelTransformer();

	@Inject
	INewsTapeRepository repository;


	@Inject
	public NewsTapeInteractorImpl() {
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createNewsHeaderStream(String sourceServerId) {
		return repository.createNewsArticleStream(sourceServerId).compose
			(transformer);
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createAdditionNewsHeaderStream(String sourceServerId,
		Long lastNewsId) {
		return repository.createAdditionNewsArticleStream(sourceServerId, lastNewsId).compose
			(transformer);
	}

	private class DataToBusinessModelTransformer implements
		ObservableTransformer<NewsHeaderDataModel,
			NewsHeaderBusinessModel> {

		@Override
		public ObservableSource<NewsHeaderBusinessModel> apply(
			Observable<NewsHeaderDataModel> upstream) {
			return upstream.map(
				data -> new NewsHeaderBusinessModel(data.getName(), data.getServerId())
			);
		}
	}
}

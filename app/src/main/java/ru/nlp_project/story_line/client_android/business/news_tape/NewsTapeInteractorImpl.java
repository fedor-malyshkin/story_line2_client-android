package ru.nlp_project.story_line.client_android.business.news_tape;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;
import ru.nlp_project.story_line.client_android.data.news_tape.INewsTapeRepository;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsArticleDataModel;

/**
 * Created by fedor on 05.02.17.
 */
@NewsTapeScope
public class NewsTapeInteractorImpl implements INewsTapeInteractor {

	private ObservableTransformer<NewsArticleDataModel,
			NewsArticleBusinessModel> transformer = new DataToBusinessModelTransformer();

	@Inject
	INewsTapeRepository repository;


	@Inject
	public NewsTapeInteractorImpl() {
	}

	@Override
	public Observable<NewsArticleBusinessModel> createNewsArticleStream() {
		return repository.createNewsArticleStream().compose
				(transformer);
	}

	@Override
	public Observable<NewsArticleBusinessModel> createAdditionNewsArticleStream(
			Long lastNewsId) {
		return repository.createAdditionNewsArticleStream(lastNewsId).compose
				(transformer);
	}

	private class DataToBusinessModelTransformer implements ObservableTransformer<NewsArticleDataModel,
			NewsArticleBusinessModel> {

		@Override
		public ObservableSource<NewsArticleBusinessModel> apply(
				Observable<NewsArticleDataModel> upstream) {
			return upstream.map(
					data -> new NewsArticleBusinessModel(data.getId(), data.getName())
			);
		}
	}
}

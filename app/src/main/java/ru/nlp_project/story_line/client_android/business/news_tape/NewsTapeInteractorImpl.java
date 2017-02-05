package ru.nlp_project.story_line.client_android.business.news_tape;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;
import ru.nlp_project.story_line.client_android.data.repositories.news_tape.INewsTapeRepository;
import ru.nlp_project.story_line.client_android.data.repositories.news_tape.NewsArticleDataModel;

/**
 * Created by fedor on 05.02.17.
 */
@NewsTapeScope
public class NewsTapeInteractorImpl implements INewsTapeInteractor {

	private DataToBusinessModelTransformer transformer = new DataToBusinessModelTransformer();

	@Inject
	INewsTapeRepository repository;

	@Inject
	public NewsTapeInteractorImpl() {
	}

	@Override
	public Observable<NewsArticleBusinessModel> getNewsArticleStream() {
		return repository.getNewsArticleStream().compose
				(transformer);
	}

	@Override
	public Observable<NewsArticleBusinessModel> getAdditionNewsArticleStream() {
		return repository.getAdditionNewsArticleStream().compose
				(transformer);
	}

	@Override
	public void requsetUpdate() {
		repository.requsetUpdate();
	}

	@Override
	public void requsetAddition(Long lastNewsId) {
		repository.requsetAddition(lastNewsId);
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

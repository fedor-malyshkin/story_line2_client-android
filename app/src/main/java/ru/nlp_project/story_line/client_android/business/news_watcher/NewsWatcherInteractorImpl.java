package ru.nlp_project.story_line.client_android.business.news_watcher;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherScope;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.news_watcher.INewsWatcherRepository;

@NewsWatcherScope
public class NewsWatcherInteractorImpl implements INewsWatcherInteractor {

	private SingleTransformer<NewsArticleDataModel,
		NewsArticleBusinessModel> transformer = new DataToBusinessModelTransformer();


	@Inject
	INewsWatcherRepository repository;


	@Inject
	public NewsWatcherInteractorImpl() {
	}

	@Override
	public Single<NewsArticleBusinessModel> createCachedNewsArticleStream(
		String newsArticleServerId) {
		return repository.createCachedNewsArticleStream(newsArticleServerId).compose(transformer);
	}

	private class DataToBusinessModelTransformer implements
		SingleTransformer<NewsArticleDataModel,
			NewsArticleBusinessModel> {

		@Override
		public SingleSource<NewsArticleBusinessModel> apply(Single<NewsArticleDataModel> upstream) {
			return upstream.map(
				// String content, String path, String title, Date date,
				// Date processingDate, String source, String serverId
				data -> new NewsArticleBusinessModel(data.getContent(), data.getPath(), data
					.getTitle(), data.getDate(), data.getProcessingDate(), data.getSource(), data
					.getServerId())
			);
		}
	}
}

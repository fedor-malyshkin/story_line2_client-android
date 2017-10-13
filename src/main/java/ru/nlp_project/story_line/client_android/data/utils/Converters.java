package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public class Converters {

	public static SourceDataModelTransformer toSourceBusinessModel = new SourceDataModelTransformer();
	public static NewsArticleDataModelTransformer toNewsArticleBusinessModel = new NewsArticleDataModelTransformer();
	public static NewsHeaderDataModelTransformer toNewsHeaderBusinessModel = new NewsHeaderDataModelTransformer();

	private static class SourceDataModelTransformer implements
			ObservableTransformer<SourceDataModel,
					SourceBusinessModel> {

		@Override
		public ObservableSource<SourceBusinessModel> apply(
				Observable<SourceDataModel> upstream) {
			return upstream.map(
					SourceDataModel::convert
			);
		}
	}


	private static class NewsArticleDataModelTransformer implements
			SingleTransformer<NewsArticleDataModel,
					NewsArticleBusinessModel> {

		@Override
		public SingleSource<NewsArticleBusinessModel> apply(Single<NewsArticleDataModel> upstream) {
			return upstream.map(
					// String content, String path, String title, Date date,
					// Date processingDate, String source, String serverId
					NewsArticleDataModel::convert
			);
		}
	}

	private static class NewsHeaderDataModelTransformer implements
			ObservableTransformer<NewsHeaderDataModel,
					NewsHeaderBusinessModel> {

		@Override
		public ObservableSource<NewsHeaderBusinessModel> apply(
				Observable<NewsHeaderDataModel> upstream) {
			return upstream.map(
					NewsHeaderDataModel::convert);
		}
	}
}

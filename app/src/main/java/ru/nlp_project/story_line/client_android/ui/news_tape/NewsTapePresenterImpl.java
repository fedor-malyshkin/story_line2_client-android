package ru.nlp_project.story_line.client_android.ui.news_tape;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import ru.nlp_project.story_line.client_android.business.news_tape.INewsTapeInteractor;
import ru.nlp_project.story_line.client_android.business.news_tape.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;

/**
 * Created by fedor on 05.02.17.
 */
@NewsTapeScope
public class NewsTapePresenterImpl implements INewsTapePresenter {

	private BusinessToUiModelTransformer transformer = new BusinessToUiModelTransformer();

	@Inject
	public NewsTapePresenterImpl() {
	}

	@Inject
	INewsTapeInteractor interactor;

	private INewsTapeView view;

	@Override
	public void connectNewsArticlesStream() {
		interactor.getNewsArticleStream().compose
				(transformer).subscribe(news -> view.updateNewsArticle(news));
	}

	@Override
	public void bindView(INewsTapeView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}


	private class BusinessToUiModelTransformer implements ObservableTransformer<NewsArticleBusinessModel,
			NewsArticleUIModel> {
		@Override
		public ObservableSource<NewsArticleUIModel> apply(
				Observable<NewsArticleBusinessModel> upstream) {
			return upstream.map(
					data -> new NewsArticleUIModel(data.getId(), data.getName())
			);

		}
	}

	@Override
	public void connectAdditionNewsArticlesStream() {
		interactor.getAdditionNewsArticleStream().compose
				(transformer).subscribe(news -> view.addNewsArticle(news));
	}

	@Override
	public void requsetUpdate() {
		view.clearTape();
		interactor.requsetUpdate();
	}

	@Override
	public void requsetAddition(Long lastNewsId) {
		interactor.requsetAddition(0L);
	}
}

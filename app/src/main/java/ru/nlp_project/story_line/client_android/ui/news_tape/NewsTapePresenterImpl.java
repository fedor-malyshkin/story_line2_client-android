package ru.nlp_project.story_line.client_android.ui.news_tape;

import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.news_tape.INewsTapeInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;

/**
 * Created by fedor on 05.02.17.
 */
@NewsTapeScope
public class NewsTapePresenterImpl implements INewsTapePresenter {

	@Inject
	public NewsTapePresenterImpl() {
	}

	@Inject
	INewsTapeInteractor interactor;

	private INewsTapeView view;


	@Override
	public void bindView(INewsTapeView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}


	@Override
	public void reloadNewsArticles() {
		view.showUpdateIndicator(true);
		view.clearTape();
		Observable<NewsHeaderBusinessModel> stream = interactor
			.createNewsHeaderStream();
		stream.subscribe(
			news -> view.addNewsArticle(news),
			e -> e.printStackTrace(),
			() -> view.showUpdateIndicator(false));
	}


}

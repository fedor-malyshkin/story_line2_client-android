package ru.nlp_project.story_line.client_android.ui.news_tape;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.news_tape.INewsTapeInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;

/**
 * Created by fedor on 05.02.17.
 */
@NewsTapeScope
public class NewsTapePresenterImpl implements INewsTapePresenter {

	private String sourceName;
	private String sourceTitle;
	private String sourceTitleShort;


	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;

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
	public void reloadNewsHeaders() {
		view.showUpdateIndicator(true);
		view.clearTape();
		Observable<NewsHeaderBusinessModel> stream = interactor
			.createNewsHeaderStream(sourceName).observeOn(uiScheduler);
		stream.subscribe(
			news -> view.addNewsHeader(news),
			e -> e.printStackTrace(),
			() -> view.showUpdateIndicator(false));
	}

	@Override
	public void initialize(String sourceName, String sourceTitle, String sourceTitleShort) {
		this.sourceName = sourceName;
		this.sourceTitle = sourceTitle;
		this.sourceTitleShort = sourceTitleShort;
	}
}

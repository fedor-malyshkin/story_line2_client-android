package ru.nlp_project.story_line.client_android.ui.news_tape;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.ArrayList;
import java.util.List;
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

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;
	@Inject
	INewsTapeInteractor interactor;
	private String sourceName;
	private String sourceTitle;
	private String sourceTitleShort;
	private List<NewsHeaderBusinessModel> newsHeaders;
	private INewsTapeView view;

	@Inject
	public NewsTapePresenterImpl() {
		newsHeaders = new ArrayList<>();
	}

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
		view.enableNewsUploading(false);
		view.showUpdateIndicator(true);
		view.clearTape(newsHeaders.size());
		newsHeaders.clear();

		Observable<NewsHeaderBusinessModel> stream = interactor
				.createNewsHeaderRemoteCachedStream(sourceName).observeOn(uiScheduler);
		stream.subscribe(
				news -> {
					view.addNewsHeader(newsHeaders.size());
					newsHeaders.add(news);
				},
				e -> e.printStackTrace(),
				() -> {
					view.showUpdateIndicator(false);
					view.enableNewsUploading(true);
				});
	}

	@Override
	public void initialize(String sourceName, String sourceTitle, String sourceTitleShort) {
		this.sourceName = sourceName;
		this.sourceTitle = sourceTitle;
		this.sourceTitleShort = sourceTitleShort;
		reloadNewsHeaders();
	}

	@Override
	public void uploadMoreNewsHeaders() {
		view.enableNewsUploading(false);
		view.showUpdateIndicator(true);

		Observable<NewsHeaderBusinessModel> stream = interactor
				.createAdditionNewsHeaderRemoteCachedStream(sourceName,
						newsHeaders.get(newsHeaders.size() - 1).getServerId()).observeOn(uiScheduler);
		stream.subscribe(
				news -> {
					view.addNewsHeader(newsHeaders.size());
					newsHeaders.add(news);
				},
				e -> e.printStackTrace(),
				() -> {
					view.showUpdateIndicator(false);
					view.enableNewsUploading(true);
				});
	}


	@Override
	public int getNewsHeaderCount() {
		return newsHeaders.size();
	}

	@Override
	public NewsHeaderBusinessModel getNewsHeader(int position) {
		return newsHeaders.get(position);
	}

	@Override
	public List<NewsHeaderBusinessModel> getNewsHeaders() {
		return newsHeaders;
	}
}

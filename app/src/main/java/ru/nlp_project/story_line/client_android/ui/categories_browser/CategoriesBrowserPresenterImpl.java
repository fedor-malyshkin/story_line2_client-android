package ru.nlp_project.story_line.client_android.ui.categories_browser;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.categories_browser.CategoryBusinessModel;
import ru.nlp_project.story_line.client_android.business.categories_browser.ICategoriesBrowserInteractor;
import ru.nlp_project.story_line.client_android.dagger.CategoriesBrowserScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;

@CategoriesBrowserScope
public class CategoriesBrowserPresenterImpl implements ICategoriesBrowserPresenter {

	private ICategoriesBrowserView view;

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;

	@Inject
	public CategoriesBrowserPresenterImpl() {
	}

	@Inject
	ICategoriesBrowserInteractor interactor;

	@Override
	public void bindView(ICategoriesBrowserView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	@Override
	public void loadCategories() {
		Observable<CategoryBusinessModel> stream = interactor
			.createCategoryStream();
		ConnectableObservable<CategoryBusinessModel> observable = stream.publish();
		observable.observeOn(uiScheduler).subscribe(
			category -> view.addCategoryInTop(category.getName(), category.getServerId()),
			e -> e.printStackTrace(),
			() -> view.noMoreAddCategory());
		// run emitting
		observable.connect();
	}
}

package ru.nlp_project.story_line.client_android.ui.categories_browser;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.categories_browser.CategoryBusinessModel;
import ru.nlp_project.story_line.client_android.business.categories_browser.ICategoriesBrowserInteractor;
import ru.nlp_project.story_line.client_android.dagger.CategoriesBrowserScope;

@CategoriesBrowserScope
public class CategoriesBrowserPresenterImpl implements ICategoriesBrowserPresenter {

	private ICategoriesBrowserView view;

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
		observable.subscribe(
			category -> view.addCategory(category.getName(), category.getServerId()),
			e -> e.printStackTrace(),
			() -> view.noMoreCategory());
		// run emitting
		observable.connect();
	}
}

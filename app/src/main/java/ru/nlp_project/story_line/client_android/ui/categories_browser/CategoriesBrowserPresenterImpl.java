package ru.nlp_project.story_line.client_android.ui.categories_browser;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.categories_browser.CategoryBusinessModel;
import ru.nlp_project.story_line.client_android.business.categories_browser.ICategoriesBrowserInteractor;
import ru.nlp_project.story_line.client_android.dagger.CategoriesBrowserScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.ui.categories_browser.ICategoriesBrowserView.ICategorySelectionListener;

@CategoriesBrowserScope
public class CategoriesBrowserPresenterImpl implements ICategoriesBrowserPresenter {

	private ICategoriesBrowserView view;

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;
	private ICategorySelectionListener categorySelectionListener;

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
		stream.observeOn(uiScheduler).subscribe(
			category -> view.addCategoryOnTop(category.getName(), category.getServerId()),
			e -> e.printStackTrace(),
			() -> view.commitAddCategoryOnTop());
		// run emitting
	}


	@Override
	public void onCategorySelection(View view) {
		if (!Button.class.isInstance(view)) {
			return;
		}

		Button btn = (Button) view;
		Log.i("onCategorySelection", btn.getText().toString());

		if (categorySelectionListener != null) {
			categorySelectionListener.categorySelected(btn
				.getText().toString());
		}
	}

	@Override
	public void setCategorySelectionListener(ICategorySelectionListener listener) {
		this.categorySelectionListener = listener;
	}
}

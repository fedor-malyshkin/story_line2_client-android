package ru.nlp_project.story_line.client_android.business.categories_browser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.categories_browser.ICategoriesBrowserRepository;

/**
 * Created by fedor on 09.02.17.
 */
public class CategoriesBrowserInteractorImpl implements ICategoriesBrowserInteractor {

	private ObservableTransformer<CategoryDataModel,
		CategoryBusinessModel> transformer = new DataToBusinessModelTransformer();

	@Inject
	ICategoriesBrowserRepository repository;


	@Inject
	public CategoriesBrowserInteractorImpl() {
	}

	@Override
	public Observable<CategoryBusinessModel> createCategoryStream() {
		return repository.createCategoryStream().compose
			(transformer);
	}


	private class DataToBusinessModelTransformer implements ObservableTransformer<CategoryDataModel,
		CategoryBusinessModel> {

		@Override
		public ObservableSource<CategoryBusinessModel> apply(
			Observable<CategoryDataModel> upstream) {
			return upstream.map(
				data -> new CategoryBusinessModel(data.getId(), data.getName(),
					data.getServerId()));
		}
	}
}

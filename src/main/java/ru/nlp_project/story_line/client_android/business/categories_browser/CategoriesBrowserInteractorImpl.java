package ru.nlp_project.story_line.client_android.business.categories_browser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.CategoryBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.CategoriesBrowserScope;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.categories_browser.ICategoriesBrowserRepository;

@CategoriesBrowserScope
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
		return repository.createCategoryStreamRemoteCached().compose
			(transformer);
	}


	private class DataToBusinessModelTransformer implements ObservableTransformer<CategoryDataModel,
		CategoryBusinessModel> {

		@Override
		public ObservableSource<CategoryBusinessModel> apply(
			Observable<CategoryDataModel> upstream) {
			return upstream.map(
				data -> new CategoryBusinessModel( data.getName(),
					data.getServerId()));
		}
	}
}

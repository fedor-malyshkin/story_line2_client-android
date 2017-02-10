package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoriesBrowserRetrofitService;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoryDateModel;
import ru.nlp_project.story_line.client_android.data.categories_browser.ICategoriesBrowserRepository;

public class ServerWebEndpointRepositoryImpl implements ICategoriesBrowserRepository {

	private final RetrofiService retrofiService;
	private final Scheduler bckgScheduler;

	@Inject
	public ServerWebEndpointRepositoryImpl(RetrofiService retrofiService, Scheduler bckgScheduler) {
		this.retrofiService = retrofiService;
		this.bckgScheduler = bckgScheduler;
	}

	@Override
	public Observable<CategoryDateModel> createCategoryStream() {
		CategoriesBrowserRetrofitService service = retrofiService.getCategoriesBrowserService();
		return service.list().subscribeOn(bckgScheduler).flatMap(Observable::fromIterable);
	}
}

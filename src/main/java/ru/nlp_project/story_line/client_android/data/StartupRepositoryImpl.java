package ru.nlp_project.story_line.client_android.data;

import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;

public class StartupRepositoryImpl implements IStartupRepository {

	@Inject
	public RetrofitService retrofitService;
	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public StartupRepositoryImpl() {
	}

	@Override
	public void initializeRepository() {

	}
}

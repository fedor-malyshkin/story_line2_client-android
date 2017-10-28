package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.ui.news_tape.INewsTapePresenter;
import ru.nlp_project.story_line.client_android.ui.news_tape.NewsTapePresenterImpl;

/**
 * Created by fedor on 05.02.17.
 */
@Module
public class NewsTapeModule {

	@Provides
	@NewsTapeScope
	public INewsTapePresenter provideNewsTapePresenter(NewsTapePresenterImpl implementation) {
		return implementation;
	}


}

package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.support.v4.app.Fragment;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserScope;
import ru.nlp_project.story_line.client_android.ui.news_tape.NewsTapeFragment;

@SourcesBrowserScope
public class NewsWatcherPresenterImpl implements INewsWatcherPresenter {

	private INewsWatcherView view;

	@Inject
	public NewsWatcherPresenterImpl() {
	}

	@Inject
	INewsWatcherInteractor interactor;

	@Override
	public int getFragmentsCount() {
		return 3;
	}

	@Override
	public Fragment getFragmentByIndex(int position) {
		switch (position) {
			case 0: // Fragment # 0 - This will show FirstFragment
				return NewsTapeFragment.newInstance("Page # 1");
			case 1: // Fragment # 0 - This will show FirstFragment different title
				return NewsTapeFragment.newInstance("Page # 2");
			case 2: // Fragment # 1 - This will show SecondFragment
				return NewsTapeFragment.newInstance("Page # 3");
			default:
				return null;
		}
	}

	@Override
	public void bindView(INewsWatcherView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

}

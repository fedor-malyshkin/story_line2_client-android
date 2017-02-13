package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherComponent;

public class NewsWatcherFragment extends Fragment implements INewsWatcherView {

	public static final String TAG = NewsWatcherFragment.class.getName();
	private static final String FRAGMENT_ARG_SERVER_ID = "serverId";

	@Inject
	public INewsWatcherPresenter presenter;

	@BindView(R.id.news_watcher_content)
	TextView newsContent;

	// newInstance constructor for creating fragment with arguments
	public static NewsWatcherFragment newInstance(String serverId) {
		NewsWatcherFragment fragment = new NewsWatcherFragment();
		Bundle args = new Bundle();
		args.putString(FRAGMENT_ARG_SERVER_ID, serverId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_news_watcher, container, false);
		ButterKnife.bind(this, view);
		// args
		Bundle arguments = getArguments();
		String serverId = arguments.getString(FRAGMENT_ARG_SERVER_ID);
		newsContent.setText(serverId);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		NewsWatcherComponent builder = DaggerBuilder.createNewsWatcherBuilder();
		builder.inject(this);
		presenter.bindView(this);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		presenter.unbindView();
	}

}
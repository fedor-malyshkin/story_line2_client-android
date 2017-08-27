package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
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
	@BindView(R.id.news_watcher_title)
	TextView newsTitle;

	@BindView(R.id.news_watcher_image)
	ImageView newsImage;

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

		NewsWatcherComponent builder = DaggerBuilder.createNewsWatcherBuilder();
		builder.inject(this);
		presenter.bindView(this);
		presenter.initialize(serverId);
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
		presenter.loadContent();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		presenter.unbindView();
	}

	@Override
	public void setContent(String title, String content, String imageUrl) {
		newsContent.setText(content);
		newsTitle.setText(title);
		Picasso.with(getContext()).load(imageUrl).into(newsImage);
	}

}

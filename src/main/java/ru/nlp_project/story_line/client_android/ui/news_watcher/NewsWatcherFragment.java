package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherComponent;
import ru.nlp_project.story_line.client_android.ui.utils.IImageDownloader;

public class NewsWatcherFragment extends Fragment implements INewsWatcherView {

	public static final String TAG = NewsWatcherFragment.class.getName();
	private static final String FRAGMENT_ARG_SERVER_ID = "serverId";

	@Inject
	public INewsWatcherPresenter presenter;
	@Inject
	public IImageDownloader imageDownloader;

	@BindView(R.id.news_watcher_news_article_content)
	TextView newsArticleContentTextView;
	@BindView(R.id.news_watcher_news_article_source)
	TextView newsArticleSourceTextView;
	@BindView(R.id.news_watcher_news_article_publication_date)
	TextView newsArticlePublicationDateTextView;
	@BindView(R.id.news_watcher_news_article_title)
	TextView newsArticleTitleTextView;
	@BindView(R.id.news_watcher_news_article_image)
	ImageView newsArticleImageView;

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

		initializeFAB(container);

		presenter.initialize(serverId);
		return view;
	}

	private void initializeFAB(ViewGroup container) {
		FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.activity_news_browser_fab);
		CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
		layoutParams.setAnchorId(R.id.news_watcher_news_article_image);
		layoutParams.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT;
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
	public void setContent(String newsArticleId, String sourceTitleShort,
			String publicationDatePresentation, String title, String content) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			newsArticleContentTextView.setText(Html.fromHtml(content,
					Html.FROM_HTML_MODE_COMPACT | Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));
		} else {
			newsArticleContentTextView.setText(Html.fromHtml(content));
		}
		newsArticleTitleTextView.setText(title);
		imageDownloader.loadImageInto(newsArticleId, getContext(), newsArticleImageView);
		newsArticlePublicationDateTextView.setText(publicationDatePresentation);
		newsArticleSourceTextView.setText(sourceTitleShort);
	}



}

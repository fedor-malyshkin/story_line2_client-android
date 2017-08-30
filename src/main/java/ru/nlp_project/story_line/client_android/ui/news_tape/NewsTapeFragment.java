package ru.nlp_project.story_line.client_android.ui.news_tape;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeComponent;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserActivity;

public class NewsTapeFragment extends Fragment implements INewsTapeView {

	public static final String TAG = NewsTapeFragment.class.getName();

	private static final String FRAGMENT_ARG_SOURCE_TITLE_SHORT = "sourceTitleShort";
	private static final String FRAGMENT_ARG_SOURCE_TITLE = "sourceTitle";
	private static final String FRAGMENT_ARG_SOURCE_NAME = "sourceName";

	@Inject
	public INewsTapePresenter presenter;
	private List<NewsHeaderBusinessModel> articleHeaders;

	@BindView(R.id.news_tape_recycler_view)
	RecyclerView newsRecyclerView;
	@BindView(R.id.news_tape_swipe_сontainer)
	SwipeRefreshLayout swipeLayout;
	private NewsTapeRecyclerViewAdapter newsTapeRecyclerViewAdapter;
	private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

	// newInstance constructor for creating fragment with arguments
	public static NewsTapeFragment newInstance(SourceBusinessModel source) {
		NewsTapeFragment fragment = new NewsTapeFragment();
		Bundle args = new Bundle();
		args.putString(FRAGMENT_ARG_SOURCE_TITLE_SHORT, source.getTitleShort());
		args.putString(FRAGMENT_ARG_SOURCE_TITLE, source.getTitle());
		args.putString(FRAGMENT_ARG_SOURCE_NAME, source.getName());
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_news_tape, container, false);
		ButterKnife.bind(this, view);
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
		NewsTapeComponent builder = DaggerBuilder.createNewsTapeBuilder();
		builder.inject(this);
		presenter.bindView(this);
		initializeRecyclerView();
		initializeSwipeUpdate();
		// arguments
		String sourceTitleShort = getArguments().getString(FRAGMENT_ARG_SOURCE_TITLE_SHORT);
		String sourceTitle = getArguments().getString(FRAGMENT_ARG_SOURCE_TITLE);
		String sourceName = getArguments().getString(FRAGMENT_ARG_SOURCE_NAME);

		presenter.initialize(sourceName, sourceTitle, sourceTitleShort);
		presenter.reloadNewsHeaders();
	}

	private void initializeSwipeUpdate() {
		swipeLayout.setRefreshing(false);
		swipeLayout.setOnRefreshListener(presenter::reloadNewsHeaders);
	}

	private void initializeRecyclerView() {
		// Set layout manager to position the items
		LinearLayoutManager newsRecyclerViewLM = new LinearLayoutManager(getContext());
		endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(
			newsRecyclerViewLM) {
			@Override
			public void onLoadMore(int page, int totalItemsCount, RecyclerView recyclerView) {
				onLoadMoreNewsHeaders(page, totalItemsCount, recyclerView);
			}
		};
		// Initialize headers
		articleHeaders = new ArrayList<>();

		// Create adapter passing in the sample user data
		newsTapeRecyclerViewAdapter = new NewsTapeRecyclerViewAdapter(this, getContext(),
			articleHeaders);
		// Attach the adapter to the recyclerview to populate items
		newsRecyclerView.setAdapter(newsTapeRecyclerViewAdapter);
		newsRecyclerView.setLayoutManager(newsRecyclerViewLM);
		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
			DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		newsRecyclerView.addItemDecoration(itemDecoration);
		newsRecyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
	}

	private void onLoadMoreNewsHeaders(int page, int totalItemsCount, RecyclerView recyclerView) {
		presenter.loadMoreNewsHeaders(articleHeaders.get(articleHeaders.size() - 1).getServerId());
	}

	@Override
	public void addNewsHeader(NewsHeaderBusinessModel news) {
		newsTapeRecyclerViewAdapter.addNewsHeader(news);
	}

	@Override
	public void clearTape() {
		newsTapeRecyclerViewAdapter.clear();
		endlessRecyclerViewScrollListener.resetState();
	}

	@Override
	public void showUpdateIndicator(boolean show) {
		swipeLayout.setRefreshing(show);
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		presenter.unbindView();
	}

	@Override
	public void newsSelected(int position) {
		NewsHeaderBusinessModel newsArticleUIModel = articleHeaders.get(position);

		// сформировать список id'ов для листания свайпом
		ArrayList articlesArray = new ArrayList();
		for (NewsHeaderBusinessModel a : articleHeaders) {
			articlesArray.add(a.getServerId());
		}

		Intent intent = new Intent(this.getContext(), NewsBrowserActivity.class);
		intent.putStringArrayListExtra(NewsBrowserActivity.INTENT_KEY_ARTICLES, articlesArray);
		intent.putExtra(NewsBrowserActivity.INTENT_KEY_POSITION, position);
		startActivity(intent);
	}

	@Override
	public void enableNewsUploading(boolean enable) {
		endlessRecyclerViewScrollListener.setEnabled(enable);
	}
}
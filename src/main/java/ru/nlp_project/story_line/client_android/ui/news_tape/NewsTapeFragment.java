package ru.nlp_project.story_line.client_android.ui.news_tape;

import android.content.Context;
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
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeComponent;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserActivity;
import ru.nlp_project.story_line.client_android.ui.utils.IImageDownloader;

public class NewsTapeFragment extends Fragment implements INewsTapeView {

	public static final String TAG = NewsTapeFragment.class.getName();

	private static final String FRAGMENT_ARG_SOURCE_TITLE_SHORT = "sourceTitleShort";
	private static final String FRAGMENT_ARG_SOURCE_TITLE = "sourceTitle";
	private static final String FRAGMENT_ARG_SOURCE_NAME = "sourceName";

	@Inject
	public INewsTapePresenter presenter;

	@BindView(R.id.fragment_news_tape_recycler_view)
	RecyclerView newsRecyclerView;
	@BindView(R.id.fragment_news_tape_swipe_сontainer)
	SwipeRefreshLayout swipeLayout;
	@Inject
	IImageDownloader imageDownloader;

	private NewsTapeRecyclerViewAdapter newsTapeRecyclerViewAdapter;
	private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
	private Context storedAppContext;

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
		storedAppContext = getActivity().getApplicationContext();
		View view = inflater.inflate(R.layout.fragment_news_tape, container, false);
		ButterKnife.bind(this, view);

		NewsTapeComponent builder = DaggerBuilder.createNewsTapeBuilder();
		builder.inject(this);
		presenter.bindView(this);
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
		initializeRecyclerView();
		initializeSwipeUpdate();
		// arguments
		String sourceTitleShort = getArguments().getString(FRAGMENT_ARG_SOURCE_TITLE_SHORT);
		String sourceTitle = getArguments().getString(FRAGMENT_ARG_SOURCE_TITLE);
		String sourceName = getArguments().getString(FRAGMENT_ARG_SOURCE_NAME);

		presenter.initialize(sourceName, sourceTitle, sourceTitleShort);
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
				presenter.uploadMoreNewsHeaders();
			}
		};

		// Create adapter passing in the sample user data
		newsTapeRecyclerViewAdapter = new NewsTapeRecyclerViewAdapter(this, getContext(),
				presenter, imageDownloader);
		// Attach the adapter to the recyclerView to populate items
		newsRecyclerView.setAdapter(newsTapeRecyclerViewAdapter);
		newsRecyclerView.setLayoutManager(newsRecyclerViewLM);

		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
				DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		newsRecyclerView.addItemDecoration(itemDecoration);

		newsRecyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
	}


	@Override
	public void addNewsHeader(int prevSize) {
		newsTapeRecyclerViewAdapter.addNewsHeader(prevSize);
	}

	@Override
	public void clearTape(int oldSize) {
		newsTapeRecyclerViewAdapter.clear(oldSize);
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
	public void onNewsSelected(int position) {
		NewsHeaderBusinessModel newsArticleUIModel = presenter.getNewsHeader(position);

		// сформировать список id'ов для листания свайпом
		ArrayList articlesArray = new ArrayList();
		for (NewsHeaderBusinessModel a : presenter.getNewsHeaders()) {
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

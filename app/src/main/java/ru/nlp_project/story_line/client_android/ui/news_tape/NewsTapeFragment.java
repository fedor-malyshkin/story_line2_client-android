package ru.nlp_project.story_line.client_android.ui.news_tape;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeComponent;

public class NewsTapeFragment extends Fragment implements INewsTapeView {
	public static final String TAG = NewsTapeFragment.class.getName();

	@Inject
	public INewsTapePresenter presenter;
	private List<NewsArticleUIModel> articles;
	@BindView(R.id.news_tape_recycler_view)
	RecyclerView newsRecyclerView;
	@BindView(R.id.news_tape_swipe_сontainer)
	SwipeRefreshLayout swipeLayout;
	private NewsTapeRecyclerViewAdapter newsTapeRecyclerViewAdapter;

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
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		NewsTapeComponent builder = DaggerBuilder.createNewsTapeBuilder();
		builder.inject(this);
		presenter.bindView(this);
		initializeRecyclerView();
		initializeSwipeUpdate();
	}

	private void initializeSwipeUpdate() {
		swipeLayout.setRefreshing(false);
		swipeLayout.setOnRefreshListener(
				() -> {
					swipeLayout.setRefreshing(true);
					try {
						presenter.requsetUpdate();
						TimeUnit.MICROSECONDS.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
					}
					swipeLayout.setRefreshing(false);
				}
		);
	}

	@Override
	public void updateNewsArticle(NewsArticleUIModel news) {
		newsTapeRecyclerViewAdapter.addNewsArticle(news);
	}

	private void initializeRecyclerView() {
		// Initialize contacts
		articles = new ArrayList<>();


		// Create adapter passing in the sample user data
		newsTapeRecyclerViewAdapter = new NewsTapeRecyclerViewAdapter(getContext(),
				articles);
		// Attach the adapter to the recyclerview to populate items
		newsRecyclerView.setAdapter(newsTapeRecyclerViewAdapter);
		// Set layout manager to position the items
		newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
				DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		newsRecyclerView.addItemDecoration(itemDecoration);

		presenter.connectNewsArticlesStream();
		presenter.requsetUpdate();
		presenter.connectAdditionNewsArticlesStream();
	}

	@Override
	public void addNewsArticle(NewsArticleUIModel news) {
		newsTapeRecyclerViewAdapter.addNewsArticle(news);
	}

	@Override
	public void clearTape() {
		newsTapeRecyclerViewAdapter.clear();
	}
}

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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeComponent;
import ru.nlp_project.story_line.client_android.ui.news_watcher.NewsWatcherActivity;

public class NewsTapeFragment extends Fragment implements INewsTapeView {

	public static final String TAG = NewsTapeFragment.class.getName();

	@Inject
	public INewsTapePresenter presenter;
	private List<NewsHeaderBusinessModel> articles;

	@BindView(R.id.news_tape_caption)
	TextView newsTapeCaption;
	@BindView(R.id.news_tape_recycler_view)
	RecyclerView newsRecyclerView;
	@BindView(R.id.news_tape_swipe_сontainer)
	SwipeRefreshLayout swipeLayout;
	private NewsTapeRecyclerViewAdapter newsTapeRecyclerViewAdapter;

	// newInstance constructor for creating fragment with arguments
	public static NewsTapeFragment newInstance(String caption) {
		NewsTapeFragment fragmentFirst = new NewsTapeFragment();
		Bundle args = new Bundle();
		args.putString("caption", caption);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_news_tape, container, false);
		ButterKnife.bind(this, view);
		String caption = getArguments().getString("caption");
		newsTapeCaption.setText(caption);
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
		presenter.reloadNewsArticles();
	}

	private void initializeSwipeUpdate() {
		swipeLayout.setRefreshing(false);
		swipeLayout.setOnRefreshListener(presenter::reloadNewsArticles);
	}

	private void initializeRecyclerView() {
		// Initialize contacts
		articles = new ArrayList<>();

		// Create adapter passing in the sample user data
		newsTapeRecyclerViewAdapter = new NewsTapeRecyclerViewAdapter(this, getContext(),
			articles);
		// Attach the adapter to the recyclerview to populate items
		newsRecyclerView.setAdapter(newsTapeRecyclerViewAdapter);
		// Set layout manager to position the items
		newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
			DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		newsRecyclerView.addItemDecoration(itemDecoration);
	}

	@Override
	public void addNewsArticle(NewsHeaderBusinessModel news) {
		newsTapeRecyclerViewAdapter.addNewsArticle(news);
	}

	@Override
	public void clearTape() {
		newsTapeRecyclerViewAdapter.clear();
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
		NewsHeaderBusinessModel newsArticleUIModel = articles.get(position);
//		Toast.makeText(getContext(), newsArticleUIModel.getName(),
//			Toast.LENGTH_LONG).show();

		Intent intent = new Intent(this.getContext(), NewsWatcherActivity.class);
		ArrayList articlesArray = new ArrayList();
		for (NewsHeaderBusinessModel a : articles) {
			articlesArray.add(a.getServerId());
		}

		intent.putStringArrayListExtra(NewsWatcherActivity.INTENT_KEY_ARTICLES, articlesArray);
		intent.putExtra(NewsWatcherActivity.INTENT_KEY_POSITION, position);
		startActivity(intent);
	}
}

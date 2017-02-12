package ru.nlp_project.story_line.client_android.ui.news_tape;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;


class NewsTapeRecyclerViewAdapter extends
	RecyclerView.Adapter<NewsTapeRecyclerViewAdapter.ViewHolder> {

	private final Context context;
	private final NewsTapeFragment parentFragment;
	private List<NewsHeaderBusinessModel> articles;

	public void addNewsArticle(NewsHeaderBusinessModel news) {
		articles.add(news);
		notifyItemInserted(articles.size() - 1);
	}

	public void clear() {
		int oldSize = articles.size();
		articles.clear();
		notifyItemRangeRemoved(0, oldSize);
	}

	// Provide a direct reference to each of the views within a data item
	// Used to cache the views within the item layout for fast access
	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private Context context;
		// Your holder should contain a member variable
		// for any view that will be set as you render a row
		@BindView(R.id.news_article_id)
		public TextView idTextView;
		@BindView(R.id.news_article_name)
		public TextView nameTextView;

		// We also create a constructor that accepts the entire item row
		// and does the view lookups to find each subview
		public ViewHolder(Context context, View itemView) {
			// Stores the itemView in a public final member variable that can be used
			// to access the context from any ViewHolder instance.
			super(itemView);
			// Store the context
			this.context = context;
			ButterKnife.bind(this, itemView);
			// Attach a click listener to the entire row view
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			int position = getAdapterPosition(); // gets item position
			if (position
				!= RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
				// We can access the data within the views
				parentFragment.newsSelected(position);
				/*
				Toast.makeText(context, nameTextView.getText(),
					Toast.LENGTH_SHORT).show();
					*/
			}
		}
	}

	@Override
	public NewsTapeRecyclerViewAdapter.ViewHolder onCreateViewHolder(
		ViewGroup parent,
		int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		// Inflate the custom layout
		View articleHeaderView = inflater
			.inflate(R.layout.view_news_tape_entry, parent, false);

		// Return a new holder instance
		ViewHolder viewHolder = new ViewHolder(context, articleHeaderView);
		return viewHolder;
	}


	@Override
	public void onBindViewHolder(NewsTapeRecyclerViewAdapter.ViewHolder holder,
		int position) {
		// Get the data model based on position
		NewsHeaderBusinessModel article = articles.get(position);

		// Set item views based on your views and data model
		TextView textView = holder.idTextView;
		textView = holder.nameTextView;
		textView.setText(article.getName());
	}

	@Override
	public int getItemCount() {
		return articles.size();
	}


	public NewsTapeRecyclerViewAdapter(NewsTapeFragment newsTapeFragment, Context context,
		List<NewsHeaderBusinessModel> articleHeaders) {
		this.parentFragment = newsTapeFragment;
		this.context = context;
		this.articles = articleHeaders;
	}
}

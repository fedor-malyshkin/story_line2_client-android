package ru.nlp_project.story_line.client_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.nlp_project.story_line.client_android.datamodel.NewsArticleHeader;


/**
 * Created by fedor on 09.01.17.
 */

public class WallNewspaperRecyclerViewAdapter extends RecyclerView.Adapter<WallNewspaperRecyclerViewAdapter.ViewHolder> {
	
	private final Context context;
	private List<NewsArticleHeader> articleHeaders;
	
	// Provide a direct reference to each of the views within a data item
	// Used to cache the views within the item layout for fast access
	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private Context context;
		// Your holder should contain a member variable
		// for any view that will be set as you render a row
		public TextView idTextView;
		public TextView nameTextView;

		// We also create a constructor that accepts the entire item row
		// and does the view lookups to find each subview
		public ViewHolder(Context context, View itemView) {
			// Stores the itemView in a public final member variable that can be used
			// to access the context from any ViewHolder instance.
			super(itemView);
// Store the context
			this.context = context;
			idTextView = (TextView) itemView.findViewById(R.id.news_article_id);
			nameTextView = (TextView) itemView.findViewById(R.id.news_article_name);
			// Attach a click listener to the entire row view
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			int position = getAdapterPosition(); // gets item position
			if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
				// We can access the data within the views
				Toast.makeText(context, nameTextView.getText(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public WallNewspaperRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		// Inflate the custom layout
		View articleHeaderView = inflater.inflate(R.layout.view_news_article_header, parent, false);

		// Return a new holder instance
		ViewHolder viewHolder = new ViewHolder(context, articleHeaderView);
		return viewHolder;
	}



	@Override
	public void onBindViewHolder(WallNewspaperRecyclerViewAdapter.ViewHolder holder, int position) {
		// Get the data model based on position
		NewsArticleHeader article = articleHeaders.get(position);

		// Set item views based on your views and data model
		TextView textView = holder.idTextView;
		textView.setText(article.getId() + "");
		textView = holder.nameTextView;
		textView.setText(article.getName());
	}

	@Override
	public int getItemCount() {
		return articleHeaders.size();
	}


	public WallNewspaperRecyclerViewAdapter(Context context, List<NewsArticleHeader> articleHeaders) {
		this.context = context;
		this.articleHeaders = articleHeaders;
	}
}

package ru.nlp_project.story_line.client_android.ui.news_tape;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.ui.utils.IImageDownloader;


class NewsTapeRecyclerViewAdapter extends
		RecyclerView.Adapter<NewsTapeRecyclerViewAdapter.ViewHolder> {

	private final Context context;
	private final NewsTapeFragment parentFragment;
	private final IImageDownloader imageDownloader;
	private INewsTapePresenter presenter;

	public NewsTapeRecyclerViewAdapter(NewsTapeFragment newsTapeFragment, Context context,
			INewsTapePresenter presenter,
			IImageDownloader imageDownloader) {
		this.parentFragment = newsTapeFragment;
		this.context = context;
		this.presenter = presenter;
		this.imageDownloader = imageDownloader;
	}

	public void addNewsHeader(int prevSize) {
		notifyItemInserted(prevSize);
	}

	public void clear(int oldSize) {
		notifyItemRangeRemoved(0, oldSize);
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
		return new ViewHolder(context, articleHeaderView, parent.getWidth());
	}


	@Override
	public void onBindViewHolder(NewsTapeRecyclerViewAdapter.ViewHolder holder,
			int position) {
		// Get the data model based on position
		NewsHeaderBusinessModel header = presenter.getNewsHeader(position);

		holder.newsArticleSourceTextView.setText(presenter.getSourceTitleShort());
		holder.newsArticleTitleTextView.setText(header.getTitle());
		holder.newsArticlePubDateTextView
				.setText(
						presenter.getPublicationDatePresentation(holder.context, header.getPublicationDate()));
		imageDownloader
				.loadImageInto(header.getServerId(), holder.context, holder.newsArticleImageView, null,
						convertDpToPixel(holder.width, holder.context));
	}

	@Override
	public int getItemCount() {
		return presenter.getNewsHeaderCount();
	}

	public int convertDpToPixel(int dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

	// Provide a direct reference to each of the views within a data item
	// Used to cache the views within the item layout for fast access
	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private final int width;
		// Your holder should contain a member variable
		// for any view that will be set as you render a row
		@BindView(R.id.news_article_title)
		TextView newsArticleTitleTextView;
		@BindView(R.id.news_article_source)
		TextView newsArticleSourceTextView;
		@BindView(R.id.news_article_publication_date)
		TextView newsArticlePubDateTextView;
		@BindView(R.id.news_article_image)
		ImageView newsArticleImageView;
		private Context context;

		// We also create a constructor that accepts the entire item row
		// and does the view lookups to find each subview
		public ViewHolder(Context context, View itemView, int width) {
			// Stores the itemView in a public final member variable that can be used
			// to access the context from any ViewHolder instance.
			super(itemView);
			// Store the context
			this.context = context;
			this.width = width;
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
				parentFragment.onNewsSelected(position);
			}
		}
	}

}

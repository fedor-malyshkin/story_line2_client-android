package ru.nlp_project.story_line.client_android.ui.preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

public class SourcesPreferencesFragment extends Fragment {

	@Inject
	IPreferencesPresenter presenter;
	@BindView(R.id.prefernces_sources_recycler_view)
	RecyclerView recyclerView;
	private ArrayList<SourceBusinessModel> sourceHeaders;
	private SourcesRecyclerViewAdapter recyclerViewAdapter;


	public SourcesPreferencesFragment() {
	}

	public static Fragment newInstance() {
		SourcesPreferencesFragment result = new SourcesPreferencesFragment();
		return result;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_preferences_source, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		presenter.unbindView();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		NewsTapeComponent builder = DaggerBuilder.createNewsTapeBuilder();
//		builder.inject(this);
//		presenter.bindView(this);
		initializeRecyclerView();
	}


	private void initializeRecyclerView() {
		// Set layout manager to position the items
		LinearLayoutManager recyclerViewLM = new LinearLayoutManager(getContext());
		// Initialize headers
		sourceHeaders = new ArrayList<>();

		// Create adapter passing in the sample user data
		recyclerViewAdapter = new SourcesRecyclerViewAdapter(this, getContext(),
				sourceHeaders);
		// Attach the adapter to the recyclerview to populate items
		recyclerView.setAdapter(recyclerViewAdapter);
		recyclerView.setLayoutManager(recyclerViewLM);
		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
				DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(itemDecoration);
	}

	private static class SourcesRecyclerViewAdapter extends
			RecyclerView.Adapter<SourceViewHolder> {

		public SourcesRecyclerViewAdapter(
				SourcesPreferencesFragment sourcesPreferencesFragment, Context context,
				ArrayList<SourceBusinessModel> sourceHeaders) {
		}

		@Override
		public SourceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			Context context = viewGroup.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);

			// Inflate the custom layout
			View articleHeaderView = inflater
					.inflate(R.layout.view_preferences_source_entry, viewGroup, false);

			// Return a new holder instance
			SourceViewHolder viewHolder = new SourceViewHolder(context, articleHeaderView);
			return viewHolder;
		}

		@Override
		public void onBindViewHolder(SourceViewHolder sourceViewHolder, int i) {
			// Get the data model based on position
//			NewsHeaderBusinessModel article = articleHeaders.get(position);

			// Set item views based on your views and data model
			TextView textView = sourceViewHolder.sourceShortTitleTextView;
			textView.setText("" + System.currentTimeMillis());
			textView = sourceViewHolder.sourceTitleTextView;
			textView.setText("" + System.currentTimeMillis() + "long description");

		}

		@Override
		public int getItemCount() {
			return 30;
		}
	}

	// Provide a direct reference to each of the views within a data item
	// Used to cache the views within the item layout for fast access
	public static class SourceViewHolder extends RecyclerView.ViewHolder {

		// Your holder should contain a member variable
		// for any view that will be set as you render a row
		@BindView(R.id.source_title)
		public TextView sourceTitleTextView;
		@BindView(R.id.source_short_title)
		public TextView sourceShortTitleTextView;
		@BindView(R.id.source_enabled)
		public CheckBox sourceEnabledCheckBox;
		private Context context;

		// We also create a constructor that accepts the entire item row
		// and does the view lookups to find each subview
		public SourceViewHolder(Context context, View itemView) {
			// Stores the itemView in a public final member variable that can be used
			// to access the context from any ViewHolder instance.
			super(itemView);
			// Store the context
			this.context = context;
			ButterKnife.bind(this, itemView);
			// Attach a click listener to the entire row view
		}

	}
}

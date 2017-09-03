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
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.PreferencesComponent;

public class SourcesEditorPreferencesFragment extends Fragment implements ISourcePreferencesView{

	@Inject
	IPreferencesPresenter presenter;
	@BindView(R.id.sources_editor_recycler_view)
	RecyclerView recyclerView;


	public SourcesEditorPreferencesFragment() {
	}

	static Fragment newInstance() {
		return new SourcesEditorPreferencesFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_sources_editor, container, false);
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
		PreferencesComponent builder = DaggerBuilder.createPreferencesBuilder();
		builder.inject(this);
		presenter.bindView(this);
		initializeRecyclerView();
		presenter.initialize();
	}


	private void initializeRecyclerView() {
		// Set layout manager to position the items
		LinearLayoutManager recyclerViewLM = new LinearLayoutManager(getContext());

		// Create adapter passing in the sample user data
		SourcesRecyclerViewAdapter recyclerViewAdapter = new SourcesRecyclerViewAdapter(this,
				getContext(),
				presenter);
		// Attach the adapter to the recyclerView to populate items
		recyclerView.setAdapter(recyclerViewAdapter);
		recyclerView.setLayoutManager(recyclerViewLM);
		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
				DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(itemDecoration);
	}

	private static class SourcesRecyclerViewAdapter extends
			RecyclerView.Adapter<SourceViewHolder> {

		private final IPreferencesPresenter presenter;

		SourcesRecyclerViewAdapter(
				SourcesEditorPreferencesFragment sourcesPreferencesFragment, Context context,
				IPreferencesPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		public SourceViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
			Context context = viewGroup.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);

			// Inflate the custom layout
			View articleHeaderView = inflater
					.inflate(R.layout.view_sources_editor_entry, viewGroup, false);

			// Return a new holder instance
			return new SourceViewHolder(context, articleHeaderView);
		}

		@Override
		public void onBindViewHolder(SourceViewHolder sourceViewHolder, int position) {
			SourceBusinessModel source = presenter.getSource(position);

			// Set item views based on your views and data model
			sourceViewHolder.sourceTitleShortTextView.setText(source.getTitleShort());
			sourceViewHolder.sourceTitleTextView.setText(source.getTitle());
			sourceViewHolder.sourceEnabledCheckBox.setEnabled(source.isEnabled());

		}

		@Override
		public int getItemCount() {
			return presenter.getSourcesCount();
		}
	}

	// Provide a direct reference to each of the views within a data item
	// Used to cache the views within the item layout for fast access
	public static class SourceViewHolder extends RecyclerView.ViewHolder {

		// Your holder should contain a member variable
		// for any view that will be set as you render a row
		@BindView(R.id.source_title)
		public TextView sourceTitleTextView;
		@BindView(R.id.source_title_short)
		public TextView sourceTitleShortTextView;
		@BindView(R.id.source_enabled)
		public CheckBox sourceEnabledCheckBox;
		private Context context;

		// We also create a constructor that accepts the entire item row
		// and does the view lookups to find each subview
		 SourceViewHolder(Context context, View itemView) {
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

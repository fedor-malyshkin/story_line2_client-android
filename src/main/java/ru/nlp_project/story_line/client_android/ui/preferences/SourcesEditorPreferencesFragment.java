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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.PreferencesComponent;

public class SourcesEditorPreferencesFragment extends Fragment implements ISourcePreferencesView {

	@Inject
	IPreferencesPresenter presenter;
	@BindView(R.id.sources_editor_recycler_view)
	RecyclerView recyclerView;
	private SourcesRecyclerViewAdapter recyclerViewAdapter;


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

	@Override
	public void onPause() {
		super.onPause();
		presenter.saveSources();
	}


	private void initializeRecyclerView() {
		// Set layout manager to position the items
		LinearLayoutManager recyclerViewLM = new LinearLayoutManager(getContext());

		// Create adapter passing in the sample user data
		recyclerViewAdapter = new SourcesRecyclerViewAdapter(this,
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

	@Override
	public void startUpdates() {
	}

	@Override
	public void finishUpdates() {
		recyclerViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void sourceChangeEnabled(int position, boolean checked) {
		presenter.sourceChangeEnabled(position, checked);
	}

	class SourcesRecyclerViewAdapter extends
			RecyclerView.Adapter<SourcesRecyclerViewAdapter.SourceViewHolder> {

		private final IPreferencesPresenter presenter;
		private final SourcesEditorPreferencesFragment parentFragmen;

		SourcesRecyclerViewAdapter(
				SourcesEditorPreferencesFragment sourcesPreferencesFragment, Context context,
				IPreferencesPresenter presenter) {
			this.parentFragmen = sourcesPreferencesFragment;
			this.presenter = presenter;
		}

		@Override
		public SourceViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
			Context context = viewGroup.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);

			// Inflate the custom layout
			View view = inflater
					.inflate(R.layout.view_sources_editor_entry, viewGroup, false);

			// Return a new holder instance
			return new SourceViewHolder(context, view);
		}

		@Override
		public void onBindViewHolder(SourceViewHolder sourceViewHolder, int position) {
			SourceBusinessModel source = presenter.getSource(position);
			// Set item views based on your views and data model
			sourceViewHolder.sourceTitleShortTextView.setText(source.getTitleShort());
			sourceViewHolder.sourceTitleTextView.setText(source.getTitle());
			sourceViewHolder.sourceEnabledCheckBox.setChecked(source.isEnabled());

		}

		@Override
		public int getItemCount() {
			return presenter.getSourcesCount();
		}

		// Provide a direct reference to each of the views within a data item
		// Used to cache the views within the item layout for fast access
		class SourceViewHolder extends RecyclerView.ViewHolder implements
				OnCheckedChangeListener {

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
				sourceEnabledCheckBox.setOnCheckedChangeListener(this);
			}

			/**
			 * Called when the checked state of a compound button has changed.
			 *
			 * @param buttonView The compound button view whose state has changed.
			 * @param isChecked The new checked state of buttonView.
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int position = getAdapterPosition(); // gets item position
				if (position
						!= RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
					// We can access the data within the views
					parentFragmen.sourceChangeEnabled(position, isChecked);
				}
			}
		}
	}
}


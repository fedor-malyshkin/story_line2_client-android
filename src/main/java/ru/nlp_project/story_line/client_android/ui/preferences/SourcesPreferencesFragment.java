package ru.nlp_project.story_line.client_android.ui.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.PreferencesComponent;

/**
 * Preferences screen for sources.
 *
 * see: https://medium.com/@arasthel92/dynamically-creating-preferences-on-android-ecc56e4f0789
 */
public class SourcesPreferencesFragment extends PreferenceFragmentCompat implements
		ISourcePreferencesView {

	@Inject
	IPreferencesPresenter presenter;
	private Context themeContext = null;


	public SourcesPreferencesFragment() {
	}

	static Fragment newInstance() {
		return new SourcesPreferencesFragment();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		PreferencesComponent builder = DaggerBuilder
				.createPreferencesBuilder();
		builder.inject(this);
		presenter.bindView(this);
		presenter.initializePresenter();
		super.onCreate(savedInstanceState); // call during onCreatePreferences
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		getPreferenceManager()
				.setSharedPreferencesName(IPreferencesPresenter.SHARED_PREFERENCES_SOURCES_NAME);

		// The activity is already attached at this point, so you can use getActivity();
		Context activityContext = getActivity();
		// We need to ask the PreferenceManager to create a PreferenceScreen for us
		PreferenceScreen rootPreferenceScreen = getPreferenceManager()
				.createPreferenceScreen(activityContext);
		setPreferenceScreen(rootPreferenceScreen);
		// We need to set a TypedValue instance that will be used to retrieve the theme id
		TypedValue themeTypedValue = new TypedValue();
		// We load our 'preferenceTheme' Theme attr into themeTypedValue
		activityContext.getTheme().resolveAttribute(R.attr.preferenceTheme, themeTypedValue, true);
		// We create a ContextWrapper which holds a reference to out Preference Theme
		themeContext = new ContextThemeWrapper(activityContext,
				themeTypedValue.resourceId);

		PreferenceCategory preferenceCategory = createSourcesCategory();
		// It's REALLY IMPORTANT to add Preferences with child Preferences to the Preference Hierarchy first
		// Otherwise, the PreferenceManager will fail to load their keys

		// First we add the category to the root PreferenceScreen
		getPreferenceScreen().addPreference(preferenceCategory);

		List<Preference> sourcePreferences = createSourcePreferences();
		// Then their child to it
		for (Preference sourcePreference : sourcePreferences) {
			preferenceCategory.addPreference(sourcePreference);
		}
	}

	/**
	 * @param preference The changed Preference.
	 * @param newValue The new value of the Preference.
	 * @return True to update the state of the Preference with the new value.
	 */
	private boolean onPreferenceChange(Preference preference, Object newValue) {
		CheckBoxPreference pref = (CheckBoxPreference) preference;
		String key = pref.getKey();
		presenter.updateSourceState(key, (Boolean) newValue);
		return true;
	}

	/**
	 * Создать необходимые элемены управления.
	 *
	 * Note: одновременно применяем данные имеющиеся в БД в хранилище, т.к. по разщным причинам
	 * возможны несоотвествия того, что БД и SharedPreferences.
	 */
	private List<Preference> createSourcePreferences() {
		SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
		Editor editor = preferences.edit();
		List<Preference> result = new ArrayList<>();
		for (SourceBusinessModel sourceBusinessModel : presenter.getAllSources()) {
			CheckBoxPreference pref = new CheckBoxPreference(themeContext);
			pref.setChecked(sourceBusinessModel.isEnabled());
			pref.setKey(sourceBusinessModel.getName());
			pref.setTitle(sourceBusinessModel.getTitleShort());
			pref.setSummary(sourceBusinessModel.getTitle());
			editor.putBoolean(sourceBusinessModel.getName(), sourceBusinessModel.isEnabled());
			pref.setOnPreferenceChangeListener(
					this::onPreferenceChange);
			result.add(pref);
		}
		editor.apply();

		return result;
	}

	private PreferenceCategory createSourcesCategory() {
		PreferenceCategory preferenceCategory = new PreferenceCategory(themeContext);
		preferenceCategory.setTitle(R.string.preferences_category_sources_title);
		return preferenceCategory;
	}

/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_sources_editor, container, false);
		ButterKnife.bind(this, view);
		return view;
	}
*/

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

/*
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		PreferencesComponent builder = DaggerBuilder.createPreferencesBuilder();
		builder.inject(this);
		presenter.bindView(this);
		initializeRecyclerView();
		presenter.initializePresenter();
	}
*/


/*
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
*/

/*
	@Override
	public void finishUpdates() {
		recyclerViewAdapter.notifyDataSetChanged();
	}
*/

/*
	@Override
	public void sourceChangeEnabled(int position, boolean checked) {
		presenter.onSourceEnabledChanged(position, checked);
	}
*/

/*
	class SourcesRecyclerViewAdapter extends
			RecyclerView.Adapter<SourcesRecyclerViewAdapter.SourceViewHolder> {

		private final IPreferencesPresenter presenter;
		private final SourcesPreferencesFragment parentFragmen;

		SourcesRecyclerViewAdapter(
				SourcesPreferencesFragment sourcesPreferencesFragment, Context context,
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

			*/
/**
 * Called when the checked state of a compound button has changed.
 *
 * @param buttonView The compound button view whose state has changed.
 * @param isChecked The new checked state of buttonView.
 *//*

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
*/
}


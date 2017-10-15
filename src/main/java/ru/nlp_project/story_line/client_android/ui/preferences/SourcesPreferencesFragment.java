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
import android.widget.Toast;
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
		if (!presenter.isUpdateSourceStateValid(key, (Boolean) newValue)) {
			return false;
		}
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


	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	@Override
	public void showAtlEastOneSourceActiveWarning() {
		Toast toast = Toast.makeText(getContext(), R.string.preferences_screen_sources_at_least_one,
				Toast.LENGTH_LONG);
		toast.show();
	}
}


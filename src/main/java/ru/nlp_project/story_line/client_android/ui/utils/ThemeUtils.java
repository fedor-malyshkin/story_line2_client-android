package ru.nlp_project.story_line.client_android.ui.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.ui.preferences.IPreferencesPresenter;

public class ThemeUtils {

	public static boolean isCurrentThemeDark(Activity activity) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(activity.getBaseContext());
		boolean isDarkTheme = prefs
				.getBoolean(IPreferencesPresenter.SHARED_PREFERENCES_IS_DARK_THEME_NAME,
						false);
		return isDarkTheme;
	}

	public static void setTheme(Activity activity, boolean darkTheme) {
		activity.setTheme(darkTheme ? R.style.AppTheme : R.style.AppThemeLight);
	}

	public static void setThemeAndRecreate(Activity activity, boolean darkTheme) {
		activity.setTheme(darkTheme ? R.style.AppTheme : R.style.AppThemeLight);
		activity.recreate();
	}
}

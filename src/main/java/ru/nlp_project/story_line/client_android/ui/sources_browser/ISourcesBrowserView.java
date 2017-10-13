package ru.nlp_project.story_line.client_android.ui.sources_browser;


import android.content.Context;
import android.content.Intent;
import android.view.View;

public interface ISourcesBrowserView {

	Context getContext();

	void startActivity(Intent intent);

	void startSourcesUpdates();

	void finishSourcesUpdates();

	void onMenuItemSearch(View view);

	void onMenuItemHelp(View view);

	void onMenuItemAbout(View view);

	void onMenuItemFeedback(View view);

	void onMenuItemSource(int i);
}

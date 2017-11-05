package ru.nlp_project.story_line.client_android.ui.sources_browser;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;

public interface ISourcesBrowserView {

	Context getContext();

	void startActivity(Intent intent);

	void finishSourcesUpdates();

	void onMenuItemSearch(View view);

	void onMenuItemHelp(View view);

	void onMenuItemAbout(View view);

	void onMenuItemFeedback(View view);

	void onMenuItemSource(int i);

	void showChangesDialog(List<ChangeRecordBusinessModel> records);
}

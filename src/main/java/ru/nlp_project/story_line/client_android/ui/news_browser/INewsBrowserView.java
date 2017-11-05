package ru.nlp_project.story_line.client_android.ui.news_browser;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;

public interface INewsBrowserView {
	FloatingActionButton getFAB();
	ViewGroup getShareNewsLayout();
	ViewGroup getShareImageLayout();
	ViewGroup getShareTextLayout();
	ViewGroup getShareURLLayout();
	ViewGroup getGotoSourceLayout();
	Context getContext();
	void startActivity(Intent intent);

	void decreaseFont();

	void increaseFont();

}

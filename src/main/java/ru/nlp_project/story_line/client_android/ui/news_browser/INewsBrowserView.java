package ru.nlp_project.story_line.client_android.ui.news_browser;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;

public interface INewsBrowserView {
	FloatingActionButton getFAB();
	ViewGroup getShareNewsLayout();
	ViewGroup getShareImageLayout();
	ViewGroup getShareTextLayout();
	ViewGroup getShareURLLayout();
	ViewGroup getGotoSourceLayout();
	Context getContext();
	void startActivity(Intent intent);

	void refreshCurrentNewsWatcher();

	void decreaseFont();

	void increaseFont();
}

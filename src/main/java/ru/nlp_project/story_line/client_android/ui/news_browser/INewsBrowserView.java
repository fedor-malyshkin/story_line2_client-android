package ru.nlp_project.story_line.client_android.ui.news_browser;


import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;

public interface INewsBrowserView {
	FloatingActionButton getFAB();
	ViewGroup getShareNewsLayout();
	ViewGroup getShareImageLayout();
	ViewGroup getShareTextLayout();
	ViewGroup getShareURLLayout();
	ViewGroup getGotoSourceLayout();
}

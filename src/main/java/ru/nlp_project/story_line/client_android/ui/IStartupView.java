package ru.nlp_project.story_line.client_android.ui;

import android.content.Context;
import java.util.Date;

public interface IStartupView {

	void startApplication();

	Context getContext();

	Date getStartupDateInPreferences();

	void storeStartupDateInPreferences(Date date);
}

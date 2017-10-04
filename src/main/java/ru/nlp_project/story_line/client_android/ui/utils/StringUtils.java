package ru.nlp_project.story_line.client_android.ui.utils;

import android.content.Context;
import android.text.format.DateUtils;
import java.util.Date;

public class StringUtils {

	public static String dateDatePresentation(Context context, Date date) {
		if (null == date) {
			return "";
		}
		return DateUtils.formatDateTime(context, date.getTime(),
				DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY
						| DateUtils.FORMAT_SHOW_YEAR);
	}


	static public String getRelativeDatePresentation(Context context, Date date) {
		if (null == date) {
			return "";
		}
		return DateUtils.getRelativeDateTimeString(context, date.getTime(), 60, 60 * 60 * 24 * 7,
				(int) DateUtils.SECOND_IN_MILLIS & DateUtils.FORMAT_24HOUR).toString();
	}

}

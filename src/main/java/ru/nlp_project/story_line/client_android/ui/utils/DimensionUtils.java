package ru.nlp_project.story_line.client_android.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DimensionUtils {

	public static int convertDpToPixel(Context context, Integer dp) {
		if (dp == null || dp == 0) {
			return 0;
		}
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}


	public static int convertPixelToDP(Context context, Integer pixel) {
		if (pixel == null || pixel == 0) {
			return 0;
		}
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return pixel / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}


}

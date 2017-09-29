package ru.nlp_project.story_line.client_android.ui.utils;

import android.content.Context;
import android.widget.ImageView;

public interface IImageDownloader {

	void loadImageInto(String newsArticleId, Context context, ImageView target);
}

package ru.nlp_project.story_line.client_android.ui.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public interface IImageDownloader {

	void loadImageInto(String newsArticleServerId, Context context, ImageView target);

	void loadImageIntoCrop(String newsArticleServerId, Context context, ImageView newsArticleImageView,
			Integer width, Integer height);

	/**
	 *
	 * @param context context
	 * @param imageView ImageView instance with picture
	 * @return imageName / null in case of failure.
	 */
	String saveImageViewToFile(Context context, ImageView imageView);
}

package ru.nlp_project.story_line.client_android.ui.utils;

import android.widget.ImageView;

public interface IImageDownloader {

	void loadImageInto(String newsArticleServerId, ImageView target);

	void loadImageIntoCrop(String newsArticleServerId,
			ImageView target,
			Integer widthDP, Integer heightDP);

	void loadImageIntoScale(String newsArticleServerId,
			ImageView target,
			Integer widthDP, Integer heightDP);


	/**
	 * @param imageView ImageView instance with picture
	 * @return imageName / null in case of failure.
	 */
	String saveImageViewToFile(ImageView imageView);
}

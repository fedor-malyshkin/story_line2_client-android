package ru.nlp_project.story_line.client_android.ui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;

public class ImageDownloaderImpl implements IImageDownloader {

	private static String TAG = ImageDownloaderImpl.class.getSimpleName();
	private final String baseUrl;
	private Picasso picassoInstance;

	public ImageDownloaderImpl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private String formatUrl(String newsArticleId) {
		return String.format("%s/news_articles/%s/images", baseUrl, newsArticleId);
	}

	@Override
	public void loadImageInto(String newsArticleId, Context context, ImageView target) {
		Picasso picasso = getPicasso(context);
		// Picasso picasso = Picasso.with(context);
		// picasso.setIndicatorsEnabled(true);
		String url = formatUrl(newsArticleId);
		picasso.load(url).into(target, new LoadingCallback(url));
	}

	private synchronized Picasso getPicasso(Context context) {
		if (picassoInstance != null) {
			return picassoInstance;
		}
		Builder builder = new Builder(context);
		OkHttpDownloader okHttpDownloader = new OkHttpDownloader(context);
		LruCache cache = new LruCache(5 * 1_024);
		picassoInstance = builder.downloader(okHttpDownloader).indicatorsEnabled(true)
				.memoryCache(cache).build();
		return picassoInstance;
	}

	static class LoadingCallback implements Callback {

		private String url = null;

		LoadingCallback(String url) {
			this.url = url;
		}

		@Override
		public void onSuccess() {
			Log.d(TAG, String.format("Loading image at url :'%s'.", url));
		}

		@Override
		public void onError() {
			Log.e(TAG, String.format("Error while loading image at url :'%s'.", url));
		}
	}
}

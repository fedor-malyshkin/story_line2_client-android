package ru.nlp_project.story_line.client_android.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
	public void loadImageInto(String newsArticleServerId, Context context, ImageView target) {
		Picasso picasso = getPicasso(context);
		// Picasso picasso = Picasso.with(context);
		// picasso.setIndicatorsEnabled(true);
		String url = formatUrl(newsArticleServerId);
		picasso.load(url).into(target, new LoadingCallback(url, target));
	}

	@Override
	public void loadImageIntoCrop(String newsArticleServerId, Context context,
			ImageView target,
			Integer widthDP, Integer heightDP) {
		Picasso picasso = getPicasso(context);
		// Picasso picasso = Picasso.with(context);
		// picasso.setIndicatorsEnabled(true);
		String url = formatUrl(newsArticleServerId, convertDpToPixel(widthDP, context),
				convertDpToPixel(heightDP, context),
				"crop");
		picasso.load(url).into(target, new LoadingCallback(url, target));
	}

	@Override
	public void loadImageIntoScale(String newsArticleServerId, Context context, ImageView target,
			Integer widthDP, Integer heightDP) {
		Picasso picasso = getPicasso(context);
		// Picasso picasso = Picasso.with(context);
		// picasso.setIndicatorsEnabled(true);
		String url = formatUrl(newsArticleServerId, convertDpToPixel(widthDP, context),
				convertDpToPixel(heightDP, context),
				"scale");
		picasso.load(url).into(target, new LoadingCallback(url, target));
	}

	private int convertDpToPixel(Integer dp, Context context) {
		if (dp == null || dp == 0) {
			return dp;
		}
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

	@SuppressLint("DefaultLocale")
	private String formatUrl(String newsArticleId, Integer width, Integer height,
			String operation) {
		if (height != null && width != null) {
			return String
					.format("%s/news_articles/%s/images?h=%d&w=%d&op=%s", baseUrl, newsArticleId, height,
							width, operation);
		} else if (height != null) {
			return String
					.format("%s/news_articles/%s/images?h=%d&op=%s", baseUrl, newsArticleId, height,
							operation);
		} else if (width != null) {
			return String
					.format("%s/news_articles/%s/images?w=%d&op=%s", baseUrl, newsArticleId, width,
							operation);
		}
		return formatUrl(newsArticleId);
	}

	private synchronized Picasso getPicasso(Context context) {
		if (picassoInstance != null) {
			return picassoInstance;
		}
		Builder builder = new Builder(context);
		OkHttpDownloader okHttpDownloader = new OkHttpDownloader(context);
		LruCache cache = new LruCache(5 * 1_024 * 1_024);
		picassoInstance = builder.downloader(okHttpDownloader).indicatorsEnabled(true)
				.memoryCache(cache).build();
		return picassoInstance;
	}

	@Override
	public String saveImageViewToFile(Context context, ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable) {
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			Log.e(TAG, "!(drawable instanceof BitmapDrawable)");
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {
			// Use methods on Context to access package-specific directories on external storage.
			// This way, you don't need to request external read/write permission.
			// See https://youtu.be/5xVh-7ywKpE?t=25m25s
			File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
					"share_image.png");
			file.mkdirs();
			if (file.exists()) {
				file.delete();
			}

			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			return file.toString();
		} catch (IOException e) {
			Log.e(TAG, "Error while storing image: " + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	static class LoadingCallback implements Callback {

		private final ImageView target;
		private String url = null;

		LoadingCallback(String url, ImageView target) {
			this.url = url;
			this.target = target;
		}

		@Override
		public void onSuccess() {
			Log.d(TAG, String.format("Loading image at url :'%s'.", url));
		}

		@Override
		public void onError() {
			target.setVisibility(View.GONE);
			Log.e(TAG, String.format("Error while loading image at url :'%s'.", url));
		}
	}
}

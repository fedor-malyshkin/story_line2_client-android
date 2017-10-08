package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherComponent;
import ru.nlp_project.story_line.client_android.ui.news_browser.INewsBrowserView;
import ru.nlp_project.story_line.client_android.ui.utils.IImageDownloader;

public class NewsWatcherFragment extends Fragment implements INewsWatcherView {

	public static final String TAG = NewsWatcherFragment.class.getName();
	private static final String FRAGMENT_ARG_SERVER_ID = "serverId";

	@Inject
	public INewsWatcherPresenter presenter;
	@Inject
	public IImageDownloader imageDownloader;

	@BindView(R.id.news_watcher_news_article_content)
	TextView newsArticleContentTextView;
	@BindView(R.id.news_watcher_news_article_source)
	TextView newsArticleSourceTextView;
	@BindView(R.id.news_watcher_news_article_publication_date)
	TextView newsArticlePublicationDateTextView;
	@BindView(R.id.news_watcher_news_article_title)
	TextView newsArticleTitleTextView;
	@BindView(R.id.news_watcher_news_article_image)
	ImageView newsArticleImageView;
	private FloatingActionButton mainFAB;
	private ViewGroup shareNewsFABLayout;
	private boolean isFABMenuExpanded = false;
	private String newsArticleHtmlContent;
	private String newsArticleURL;
	private String newsArticleImageURL;
	private ViewGroup shareImageFABLayout;
	private ViewGroup shareTextFABLayout;
	private ViewGroup shareURLFABLayout;
	private ViewGroup gotoSourceFABLayout;
	private Context storedAppContext;

	// newInstance constructor for creating fragment with arguments
	public static NewsWatcherFragment newInstance(String serverId) {
		NewsWatcherFragment fragment = new NewsWatcherFragment();
		Bundle args = new Bundle();
		args.putString(FRAGMENT_ARG_SERVER_ID, serverId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Context getContext() {
		return storedAppContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreateView.this = " + this);
		storedAppContext = getActivity().getApplicationContext();
		View view = inflater.inflate(R.layout.fragment_news_watcher, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	private void initializeFAB(INewsBrowserView view) {
		if (view != null) {
			this.mainFAB = view.getFAB();
			this.mainFAB.setOnClickListener(this::onPressMainFAB);
			this.shareNewsFABLayout = view.getShareNewsLayout();
			this.shareNewsFABLayout.setOnClickListener(this::onPressShareNewsFABLayout);
			this.shareImageFABLayout = view.getShareImageLayout();
			this.shareImageFABLayout.setOnClickListener(this::onPressShareImageFABLayout);
			this.shareTextFABLayout = view.getShareTextLayout();
			this.shareTextFABLayout.setOnClickListener(this::onPressShareTextFABLayout);
			this.shareURLFABLayout = view.getShareURLLayout();
			this.shareURLFABLayout.setOnClickListener(this::onPressShareURLFABLayout);
			this.gotoSourceFABLayout = view.getGotoSourceLayout();
			this.gotoSourceFABLayout.setOnClickListener(this::onPressGotoSourceFABLayout);

		}
	}

	private void onPressGotoSourceFABLayout(View e) {
		gotoSource();
		collapseFABMenu();
	}

	private void gotoSource() {
		Intent viewIntent = new Intent();
		viewIntent.setAction(Intent.ACTION_VIEW);
		viewIntent.setData(Uri.parse(newsArticleURL));
		startActivity(viewIntent);
	}

	private void onPressShareURLFABLayout(View e) {
		shareURL();
		collapseFABMenu();
	}

	private void onPressShareTextFABLayout(View e) {
		shareText();
		collapseFABMenu();
	}

	private void onPressShareImageFABLayout(View e) {
		shareImage();
		collapseFABMenu();
	}

	private void onPressShareNewsFABLayout(View e) {
		shareNews();
		collapseFABMenu();
	}

	private void collapseFABMenu() {
		List<ViewGroup> viewGroups = Arrays
				.asList(shareNewsFABLayout, shareImageFABLayout, shareTextFABLayout, shareURLFABLayout,
						gotoSourceFABLayout);
		AnimatorListener listener = new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				mainFAB.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				for (ViewGroup gr : viewGroups) {
					gr.setVisibility(View.GONE);
				}
				isFABMenuExpanded = false;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}
		};
		List<AnimatorSet> sets = new ArrayList<>();
		Context context = getContext();
		for (ViewGroup gr : viewGroups) {
			AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context,
					R.animator.animator_fab_menu_hide);
			set.setTarget(gr);
			set.addListener(listener);
			sets.add(set);
		}
		for (AnimatorSet set : sets) {
			set.start();
		}

	}

	private void onPressMainFAB(View e) {
		expandFABMenu();
		isFABMenuExpanded = true;
	}

	private void shareNews() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticleContentTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_TITLE, newsArticleTitleTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_HTML_TEXT, newsArticleHtmlContent);
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(newsArticleImageURL));
		sendIntent.setType("*/*");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
	}

	private void shareImage() {
		String imageName = imageDownloader.saveImageViewToFile(getContext(), newsArticleImageView);
		if (imageName == null) {
			return;
		}

		// see this recommendation - https://developer.android.com/training/sharing/send.html#send-binary-content
		MediaScannerConnection
				.scanFile(getContext(), new String[]{imageName}, new String[]{"image/jpg", "image/png"},
						new MediaScannerConnection.OnScanCompletedListener() {
							public void onScanCompleted(String path, Uri uri) {
								Intent sendIntent = new Intent();
								sendIntent.setAction(Intent.ACTION_SEND);
								sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
								sendIntent.setType("image/png");
								startActivity(
										Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
							}
						});
	}

	private void shareURL() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticleURL);
		sendIntent.putExtra(Intent.EXTRA_TITLE, newsArticleTitleTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_HTML_TEXT,
				formatUrlString(newsArticleURL, newsArticleTitleTextView.getText().toString()));
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
	}

	private String formatUrlString(String url, String title) {
		return String.format("<a href=\"%s\">%s</a>", url, title);
	}

	private void shareText() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticleContentTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_TITLE, newsArticleTitleTextView.getText());
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
	}


	private void expandFABMenu() {
		List<ViewGroup> viewGroups = Arrays
				.asList(shareNewsFABLayout, shareImageFABLayout, shareTextFABLayout, shareURLFABLayout,
						gotoSourceFABLayout);
		AnimatorListener listener = new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				for (ViewGroup gr : viewGroups) {
					gr.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mainFAB.setVisibility(View.GONE);
				isFABMenuExpanded = true;
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		};

		List<AnimatorSet> sets = new ArrayList<>();
		Context context = getContext();
		for (ViewGroup gr : viewGroups) {
			AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context,
					R.animator.animator_fab_menu_show);
			set.setTarget(gr);
			set.addListener(listener);
			sets.add(set);
		}
		for (AnimatorSet set : sets) {
			set.start();
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		NewsWatcherComponent builder = DaggerBuilder.createNewsWatcherBuilder();
		builder.inject(this);
		presenter.bindView(this);

		// args
		Bundle arguments = getArguments();
		String serverId = arguments.getString(FRAGMENT_ARG_SERVER_ID);
		presenter.initialize(serverId);
		presenter.loadContent();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		presenter.unbindView();
	}

	@Override
	public void setContent(String newsArticleId, String sourceTitleShort,
			String publicationDatePresentation, String title, String content, String url,
			String imageUrl) {
		newsArticleHtmlContent = content;
		newsArticleURL = url;
		newsArticleImageURL = imageUrl;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

			newsArticleContentTextView.setText(Html.fromHtml(content,
					Html.FROM_HTML_MODE_COMPACT | Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));

		} else {
			newsArticleContentTextView.setText(Html.fromHtml(content));
		}
		newsArticleTitleTextView.setText(title);
		imageDownloader.loadImageInto(newsArticleId, getContext(), newsArticleImageView);
		newsArticlePublicationDateTextView.setText(publicationDatePresentation);
		newsArticleSourceTextView.setText(sourceTitleShort);
	}

	@Override
	public boolean processBackPressed() {
		if (isFABMenuExpanded) {
			collapseFABMenu();
			return true;
		}
		return false;
	}

	@Override
	public void viewShowToUser(
			INewsBrowserView view) {
		System.out.println("viewShowToUser.this = " + this);
		initializeFAB(view);
	}

}

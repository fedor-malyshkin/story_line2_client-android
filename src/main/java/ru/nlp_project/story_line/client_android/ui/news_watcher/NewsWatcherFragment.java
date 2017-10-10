package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
	@BindView(R.id.news_watcher_news_article_scroll_view)
	NestedScrollView nestedScrollView;


	private FloatingActionButton mainFAB;
	private ViewGroup shareNewsFABLayout;
	private boolean isFABMenuExpanded = false;
	private boolean isFABMenuShown = true;
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
		storedAppContext = getActivity().getApplicationContext();
		View view = inflater.inflate(R.layout.fragment_news_watcher, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	private void initializeFAB(INewsBrowserView view) {
		if (view != null) {
			this.mainFAB = view.getFAB();
			this.mainFAB.setOnTouchListener(this::onPressMainFAB);
			this.shareNewsFABLayout = view.getShareNewsLayout();
			this.shareNewsFABLayout.setOnTouchListener(this::onPressShareNewsFABLayout);
			this.shareImageFABLayout = view.getShareImageLayout();
			this.shareImageFABLayout.setOnTouchListener(this::onPressShareImageFABLayout);
			this.shareTextFABLayout = view.getShareTextLayout();
			this.shareTextFABLayout.setOnTouchListener(this::onPressShareTextFABLayout);
			this.shareURLFABLayout = view.getShareURLLayout();
			this.shareURLFABLayout.setOnTouchListener(this::onPressShareURLFABLayout);
			this.gotoSourceFABLayout = view.getGotoSourceLayout();
			this.gotoSourceFABLayout.setOnTouchListener(this::onPressGotoSourceFABLayout);
		}
	}

	private boolean onPressGotoSourceFABLayout(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		presenter.onPressGotoSourceFABLayout();
		// consume event
		return true;
	}

	@Override
	public void gotoSource() {
		Intent viewIntent = new Intent();
		viewIntent.setAction(Intent.ACTION_VIEW);
		viewIntent.setData(Uri.parse(newsArticleURL));
		startActivity(viewIntent);
	}

	private boolean onPressShareURLFABLayout(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		presenter.onPressShareURLFABLayout();
		// consume event
		return true;
	}

	private boolean onPressShareTextFABLayout(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		presenter.onPressShareTextFABLayout();
		// consume event
		return true;
	}

	private boolean onPressShareImageFABLayout(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}

		presenter.onPressShareImageFABLayout();
		// consume event
		return true;
	}

	private boolean onPressShareNewsFABLayout(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		presenter.onPressShareNewsFABLayout();
		// consume event
		return true;
	}

	@Override
	public void collapseFABMenuFast() {
		mainFAB.setVisibility(View.VISIBLE);
		List<ViewGroup> viewGroups = Arrays
				.asList(shareNewsFABLayout, shareImageFABLayout, shareTextFABLayout, shareURLFABLayout,
						gotoSourceFABLayout);
		for (ViewGroup gr : viewGroups) {
			gr.setVisibility(View.GONE);
		}
		isFABMenuExpanded = false;
	}


	@Override
	public void expandFABMenuFast() {
		mainFAB.setVisibility(View.GONE);
		List<ViewGroup> viewGroups = Arrays
				.asList(shareNewsFABLayout, shareImageFABLayout, shareTextFABLayout, shareURLFABLayout,
						gotoSourceFABLayout);
		for (ViewGroup gr : viewGroups) {
			gr.setVisibility(View.VISIBLE);
		}
		isFABMenuExpanded = true;
	}


	@Override
	public void collapseFABMenu() {
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
					R.animator.animator_fab_menu_collapse);
			set.setTarget(gr);
			set.addListener(listener);
			sets.add(set);
		}
		for (AnimatorSet set : sets) {
			set.start();
		}

	}

	private boolean onPressMainFAB(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		expandFABMenu();
		return true;
	}

	@Override
	public void shareNews() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticleContentTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_TITLE, newsArticleTitleTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_HTML_TEXT, newsArticleHtmlContent);
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(newsArticleImageURL));
		sendIntent.setType("*/*");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
	}

	@Override
	public void shareImage() {
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

	@Override
	public void shareURL() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticleURL);
		sendIntent.putExtra(Intent.EXTRA_TITLE, newsArticleTitleTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_HTML_TEXT,
				presenter.formatUrlString(newsArticleURL, newsArticleTitleTextView.getText().toString()));
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
	}

	@Override
	public void shareText() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticleContentTextView.getText());
		sendIntent.putExtra(Intent.EXTRA_TITLE, newsArticleTitleTextView.getText());
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
	}


	@Override
	public void expandFABMenu() {
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
					R.animator.animator_fab_menu_expand);
			set.setTarget(gr);
			set.addListener(listener);
			sets.add(set);
		}
		for (AnimatorSet set : sets) {
			set.start();
		}
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		NewsWatcherComponent builder = DaggerBuilder.createNewsWatcherBuilder();
		builder.inject(this);
		presenter.bindView(this);

		initializeImageView();
		initializeContentTextView();
		initializeNestedScrollView();

		// args
		Bundle arguments = getArguments();
		String serverId = arguments.getString(FRAGMENT_ARG_SERVER_ID);
		presenter.initialize(serverId);
		presenter.loadContent();
	}

	private void initializeContentTextView() {
		newsArticleContentTextView.setOnClickListener(this::onWholeViewClick);
	}

	private void initializeNestedScrollView() {
		nestedScrollView.setOnScrollChangeListener(this::onScrollViewScrollChange);
	}

	@Override
	public boolean isFABMenuExpanded() {
		return isFABMenuExpanded;
	}

	@Override
	public boolean isFABMenuShown() {
		return isFABMenuShown;
	}

	private void onScrollViewScrollChange(NestedScrollView v, int scrollX, int scrollY,
			int oldScrollX, int oldScrollY) {
		int deltaY = scrollY - oldScrollY;

		// if scroll down
		if (deltaY > 0) {
			presenter.onScrollViewDown();
		} else {
			presenter.onScrollViewUp();
		}
	}

	private void onWholeViewClick(View view) {
		// FAB menu
		if (isFABMenuExpanded) {
			collapseFABMenu();
		}
		// FAB
		if (isFABMenuShown) {
			hideFABMenu();
		} else {
			showFABMenu();
		}
	}

	@Override
	public void showFABMenu() {
		AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
				R.animator.animator_fab_menu_show);
		set.setTarget(mainFAB);
		set.start();
		isFABMenuShown = true;
	}

	@Override
	public void hideFABMenu() {
		AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
				R.animator.animator_fab_menu_hide);
		set.setTarget(mainFAB);
		set.start();
		isFABMenuShown = false;
	}

	/**
	 * Среди прочего выполнить инициализацию размера View maxHeight = 2/3 of display screen height
	 */
	private void initializeImageView() {
		int displayHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
		newsArticleImageView.setMaxHeight(displayHeight * 2 / 3);
		newsArticleImageView.setOnClickListener(this::onWholeViewClick);
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
		return presenter.processBackPressed();
	}

	@Override
	public void viewShownToUser(
			INewsBrowserView view) {
		initializeFAB(view);
		presenter.onShownToUser();
	}

}

package ru.nlp_project.story_line.client_android.ui.news_browser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsBrowserComponent;
import ru.nlp_project.story_line.client_android.ui.news_watcher.INewsWatcherView;
import ru.nlp_project.story_line.client_android.ui.utils.CacheableFragmentStatePageAdapter;

public class NewsBrowserActivity extends AppCompatActivity implements INewsBrowserView {

	public static final String INTENT_KEY_ARTICLES = "articles";
	public static final String INTENT_KEY_POSITION = "position";
	@Inject
	public INewsBrowserPresenter presenter;
	@BindView(R.id.activity_news_browser_view_pager)
	ViewPager viewPager;
	@BindView(R.id.activity_news_browser_toolbar)
	Toolbar toolbar;

	@BindView(R.id.activity_news_browser_fab)
	FloatingActionButton fab;
	@BindView(R.id.activity_news_browser_goto_source_fab_layout)
	LinearLayout gotoSourceFABLayout;
	@BindView(R.id.activity_news_browser_share_text_fab_layout)
	LinearLayout shareTextFABLayout;
	@BindView(R.id.activity_news_browser_share_image_fab_layout)
	LinearLayout shareImageFABLayout;
	@BindView(R.id.activity_news_browser_share_news_fab_layout)
	LinearLayout shareNewsFABLayout;
	@BindView(R.id.activity_news_browser_share_url_fab_layout)
	LinearLayout shareURLFABLayout;


	private NewsArticlesPageAdapter adapterViewPager;


	private void initializeToolbar() {
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_browser);
		ButterKnife.bind(this);
		NewsBrowserComponent builder = DaggerBuilder
				.createNewsBrowserBuilder();
		builder.inject(this);
		presenter.bindView(this);

		Intent intent = getIntent();
		int articlePos = intent.getIntExtra(NewsBrowserActivity.INTENT_KEY_POSITION, 0);
		ArrayList<String> articleServerIds = intent
				.getStringArrayListExtra(NewsBrowserActivity.INTENT_KEY_ARTICLES);
		presenter.setData(articleServerIds, articlePos);

		initializeToolbar();
		initializeViewPager(articlePos);
		presenter.initialize();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sources_browser_toolbar, menu);
		return true;
	}


	private void initializeViewPager(int articlePos) {
		adapterViewPager = new NewsArticlesPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapterViewPager);
		// viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		OnPageChangeListener pageListener = new NewsBrowserOnPageChangeListener(this);
		viewPager.addOnPageChangeListener(pageListener);
		viewPager.setCurrentItem(articlePos, true);

	}

	@Override
	public FloatingActionButton getFAB() {
		return fab;
	}

	@Override
	public ViewGroup getShareNewsLayout() {
		return shareNewsFABLayout;
	}

	@Override
	public void onBackPressed() {
		int currentItem = viewPager.getCurrentItem();
		INewsWatcherView fragment = (INewsWatcherView) adapterViewPager
				.getRegisteredFragment(currentItem);
		if (fragment.processBackPressed()) {
			return;
		}
		super.onBackPressed();
	}

	@Override
	public ViewGroup getShareImageLayout() {
		return shareImageFABLayout;
	}

	@Override
	public ViewGroup getShareTextLayout() {
		return shareTextFABLayout;
	}

	@Override
	public ViewGroup getShareURLLayout() {
		return shareURLFABLayout;
	}

	@Override
	public ViewGroup getGotoSourceLayout() {
		return gotoSourceFABLayout;
	}

	public class NewsArticlesPageAdapter extends CacheableFragmentStatePageAdapter {

		public NewsArticlesPageAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return presenter.getFragmentsCount();
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			return presenter.getFragmentByIndex(position);
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			return presenter.getFragmentTitleByIndex(position);
		}


	}


	private class NewsBrowserOnPageChangeListener implements OnPageChangeListener {

		private final NewsBrowserActivity activity;

		public NewsBrowserOnPageChangeListener(NewsBrowserActivity newsBrowserActivity) {
			this.activity = newsBrowserActivity;
		}

		@Override
		public void onPageScrolled(int position,
				float positionOffset,
				int positionOffsetPixels) {
			// need to call only whe  positionOffsetPixels == 0
			if (positionOffsetPixels == 0) {
				INewsWatcherView fragment = (INewsWatcherView) adapterViewPager
						.getRegisteredFragment(position);
				fragment.viewShowToUser(activity);
			}

		}

		@Override
		public void onPageSelected(int currentItem) {
			// not called when select programmaticaly - use "onPageScrolled"
		}

		@Override
		public void onPageScrollStateChanged(int i) {
		}
	}
}

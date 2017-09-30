package ru.nlp_project.story_line.client_android.ui.news_browser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.NewsBrowserComponent;
import ru.nlp_project.story_line.client_android.ui.utils.CachingFragmentStatePagerAdapter;
import ru.nlp_project.story_line.client_android.ui.utils.ZoomOutPageTransformer;

public class NewsBrowserActivity extends AppCompatActivity implements INewsBrowserView {

	public static final String INTENT_KEY_ARTICLES = "articles";
	public static final String INTENT_KEY_POSITION = "position";

	private NewsArticlesPageAdapter adapterViewPager;


	@BindView(R.id.news_browser_view_pager)
	ViewPager viewPager;

	@Inject
	public INewsBrowserPresenter presenter;

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
		presenter.initialize(articleServerIds, articlePos);
		initializeViewPager(articlePos);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}


	private void initializeViewPager(int articlePos) {
		adapterViewPager = new NewsArticlesPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapterViewPager);
		// viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		viewPager.setCurrentItem(articlePos, true);
	}

	public class NewsArticlesPageAdapter extends CachingFragmentStatePagerAdapter {

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
}

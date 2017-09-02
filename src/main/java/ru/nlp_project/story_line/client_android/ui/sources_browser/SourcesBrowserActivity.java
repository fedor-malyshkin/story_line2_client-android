package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserComponent;
import ru.nlp_project.story_line.client_android.ui.utils.CachingFragmentStatePagerAdapter;
import ru.nlp_project.story_line.client_android.ui.utils.ZoomOutPageTransformer;

public class SourcesBrowserActivity extends AppCompatActivity implements ISourcesBrowserView {

	private SourcesPageAdapter adapterViewPager;


	// Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
	// The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
	private ActionBarDrawerToggle drawerToggle;


	@BindView(R.id.sources_browser_view_pager)
	ViewPager viewPager;

	@BindView(R.id.toolbar)
	Toolbar toolbar;


	@Inject
	public ISourcesBrowserPresenter presenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sources_browser);
		ButterKnife.bind(this);
		SourcesBrowserComponent builder = DaggerBuilder
				.createSourcesBrowserBuilder();
		builder.inject(this);
		presenter.bindView(this);
		presenter.initialize();
		initializeSlidingPanel();
		initailizeToolbar();
		initializeViewPager();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sources_browser, menu);
		return true;
	}

	private void initailizeToolbar() {
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	private void initializeSlidingPanel() {
//		slidingPanel.setParallaxDistance(200);
	}

	private void initializeViewPager() {
		adapterViewPager = new SourcesPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapterViewPager);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}

	public class SourcesPageAdapter extends CachingFragmentStatePagerAdapter {

		public SourcesPageAdapter(FragmentManager fragmentManager) {
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

	@Override
	public void hideLeftPanel() {
//		slidingPanel.closePane();
	}

	@Override
	public void categorySelected(String category) {
		presenter.categorySelected(category);
	}
}

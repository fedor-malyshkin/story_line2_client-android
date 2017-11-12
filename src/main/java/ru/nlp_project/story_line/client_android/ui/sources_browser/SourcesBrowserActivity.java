package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserComponent;
import ru.nlp_project.story_line.client_android.ui.changes.ChangesDialog;
import ru.nlp_project.story_line.client_android.ui.feedback.AboutActivity;
import ru.nlp_project.story_line.client_android.ui.feedback.FeedbackActivity;
import ru.nlp_project.story_line.client_android.ui.utils.CacheableFragmentStatePageAdapter;
import ru.nlp_project.story_line.client_android.ui.utils.ThemeUtils;

public class SourcesBrowserActivity extends AppCompatActivity implements ISourcesBrowserView {

	private static final String TAG = SourcesBrowserActivity.class.getSimpleName();
	@Inject
	public ISourcesBrowserPresenter presenter;
	@BindView(R.id.activity_sources_browser_view_pager)
	ViewPager viewPager;
	@BindView(R.id.activity_sources_browser_toolbar)
	Toolbar toolbar;
	@BindView(R.id.activity_sources_browser_tab_layout)
	TabLayout tabLayout;
	@BindView(R.id.activity_sources_browser_drawer_layout)
	DrawerLayout drawerLayout;
	@BindView(R.id.activity_sources_browser_navigation_recycler_view)
	RecyclerView navigationRecyclerView;
	@BindView(R.id.activity_sources_browser_navigation_view)
	NavigationView navigationView;

	private SourcesPageAdapter sourcesPageAdapter;
	// Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
	// The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
	private ActionBarDrawerToggle drawerToggle;
	private NavigationMenuManager navigationMenuManager;
	private SourcesBrowserActivity.SourcesOnPageChangeListener sourcePageChangeListener;
	private boolean creationTimeThemeDark;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// must be called before "super.onCreate"
		creationTimeThemeDark = ThemeUtils.isCurrentThemeDark(this);
		ThemeUtils.setTheme(this, creationTimeThemeDark);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sources_browser);

		ButterKnife.bind(this);
		SourcesBrowserComponent builder = DaggerBuilder
				.createSourcesBrowserBuilder();
		builder.inject(this);

		presenter.bindView(this);

		initializeToolbar();
		initializeNavigationMenu();
		initializeDrawerLayout();
		initializeViewPager();
		// last step - after full interface initialization
		presenter.initializePresenter();
	}

	private void initializeNavigationMenu() {
		navigationMenuManager = new NavigationMenuManager(presenter, this,
				navigationRecyclerView, navigationView.getHeaderView(0));
		navigationMenuManager.initialize();
	}

	@Override
	protected void onResume() {
		super.onResume();
		presenter.refreshSourcesList();

		boolean isNowThemeDark = ThemeUtils.isCurrentThemeDark(this);
		if (isNowThemeDark != creationTimeThemeDark)
			ThemeUtils.setThemeAndRecreate(this, isNowThemeDark);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sources_browser_toolbar, menu);
		return true;
	}

	private void initializeToolbar() {
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	private void initializeDrawerLayout() {
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};

		// Set the drawer toggle as the DrawerListener
		drawerLayout.addDrawerListener(drawerToggle);
	}

	private void initializeViewPager() {
		sourcePageChangeListener = new SourcesOnPageChangeListener(this);
		sourcesPageAdapter = new SourcesPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(sourcesPageAdapter);
		viewPager.addOnPageChangeListener(sourcePageChangeListener);
		tabLayout.setupWithViewPager(viewPager, true);
		// viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}

	@Override
	public Context getContext() {
		return getBaseContext();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
			case R.id.menu_item_settings: {
				return presenter.openSettings();
			}
			case R.id.menu_item_sources: {
				return presenter.openSources();
			}

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void finishSourcesUpdates() {
		// sourcesPageAdapter.finishUpdate((ViewGroup) viewPager.getParent());
		sourcesPageAdapter.notifyDataSetChanged();
		if (viewPager.getCurrentItem() > presenter.getFragmentsCount()) {
			viewPager.setCurrentItem(0);
			navigationMenuManager.setSelectedItem(0);
		}
		navigationMenuManager.finishSourcesUpdates();
	}

	@Override
	public void onMenuItemSearch(View view) {
		drawerLayout.closeDrawers();
		Toast.makeText(getContext(), "onMenuItemSearch", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMenuItemHelp(View view) {
		drawerLayout.closeDrawers();
		Toast.makeText(getContext(), "onMenuItemHelp", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMenuItemAbout(View view) {
		drawerLayout.closeDrawers();
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	@Override
	public void onMenuItemFeedback(View view) {
		drawerLayout.closeDrawers();
		Intent intent = new Intent(this, FeedbackActivity.class);
		startActivity(intent);
	}

	@Override
	public void onMenuItemSource(int i) {
		viewPager.setCurrentItem(i);
		navigationMenuManager.setSelectedItem(i);
		drawerLayout.closeDrawers();
	}

	@Override
	public void showChangesDialog() {
		FragmentManager fm = getSupportFragmentManager();
		ChangesDialog dialog = ChangesDialog.newInstance();
		dialog.show(fm, "fragment_edit_name");
	}

	public class SourcesPageAdapter extends CacheableFragmentStatePageAdapter {

		SourcesPageAdapter(FragmentManager fragmentManager) {
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

		/**
		 * Necessary method - because modify sources, so i needd to get correct information about
		 * previously created fragments.
		 * <p/>
		 * see: https://stackoverflow.com/questions/8060904/add-delete-pages-to-viewpager-dynamically
		 */
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}


	private class SourcesOnPageChangeListener implements OnPageChangeListener {

		private final SourcesBrowserActivity activity;

		public SourcesOnPageChangeListener(SourcesBrowserActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// need to call only whe  positionOffsetPixels == 0
			if (positionOffsetPixels == 0) {
				navigationMenuManager.setSelectedItem(position);
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

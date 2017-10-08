package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import ru.nlp_project.story_line.client_android.ui.utils.CacheableFragmentStatePageAdapter;

public class SourcesBrowserActivity extends AppCompatActivity implements ISourcesBrowserView {

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
	@BindView(R.id.activity_sources_browser_navigation_view)
	NavigationView navigationView;
	@BindView(R.id.activity_sources_browser_navigation_recycler_view)
	RecyclerView navigationRecyclerView;

	private SourcesPageAdapter sourcesPageAdapter;
	// Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
	// The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
	private ActionBarDrawerToggle drawerToggle;
	private NavigationMenuManager navigationMenuManager;

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
		// last step - after full interface initilization
		presenter.initialize();
	}

	private void initializeNavigationMenu() {
		navigationMenuManager = new NavigationMenuManager(presenter, this,
				navigationRecyclerView);
		navigationMenuManager.initialize();
	}

	@Override
	protected void onResume() {
		super.onResume();
		presenter.updateSources();
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
				prepareNavigationViewMenuOnOpenDrawer();
				super.onDrawerOpened(drawerView);
			}
		};

		// Set the drawer toggle as the DrawerListener
		drawerLayout.addDrawerListener(drawerToggle);
	}

	/**
	 * Подготовить меню NavigationView перед открытием DrawerLayout.
	 */
	private void prepareNavigationViewMenuOnOpenDrawer() {
		navigationMenuManager.prepareMenu();
	}

	private void initializeViewPager() {
		sourcesPageAdapter = new SourcesPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(sourcesPageAdapter);
		tabLayout.setupWithViewPager(viewPager, true);
		// viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}

	@Override
	public Context getContext() {
		return getBaseContext();
	}


	@Override
	public void categorySelected(String category) {
		presenter.categorySelected(category);
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
	public void startSourcesUpdates() {
		sourcesPageAdapter.startUpdate(viewPager);
	}

	@Override
	public void finishSourcesUpdates() {
		if (viewPager.getCurrentItem() > presenter.getFragmentsCount()) {
			viewPager.setCurrentItem(0);
		}
		sourcesPageAdapter.finishUpdate(viewPager);
		sourcesPageAdapter.notifyDataSetChanged();
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
		Toast.makeText(getContext(), "onMenuItemAbout", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMenuItemFeedback(View view) {
		drawerLayout.closeDrawers();
		Toast.makeText(getContext(), "onMenuItemFeedback", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMenuItemSource(int i) {
		viewPager.setCurrentItem(i);
		drawerLayout.closeDrawers();
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

		// TODO: реализовать более интеллектуальный способ обновления (например сделать интерфейс-марке и
		// вставить в него имя источника)
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
}

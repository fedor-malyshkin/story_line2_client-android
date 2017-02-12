package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserComponent;
import ru.nlp_project.story_line.client_android.ui.utils.ZoomOutPageTransformer;

public class SourcesBrowserActivity extends AppCompatActivity implements ISourcesBrowserView {

	private SourcesPageAdapter adapterViewPager;

	@BindView(R.id.browser_sliding_panel)
	SlidingPaneLayoutEx slidingPanel;

	@BindView(R.id.browser_view_pager)
	ViewPager viewPager;

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
		initializeViewPager();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	private void initializeSlidingPanel() {
		slidingPanel.setParallaxDistance(200);
	}

	private void initializeViewPager() {
		adapterViewPager = new SourcesPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapterViewPager);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}


	public class SourcesPageAdapter extends FragmentStatePagerAdapter {

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
			return "Page " + position;
		}

	}

	@Override
	public void categorySelected(String category) {
		presenter.categorySelected(category);
	}
}

package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserComponent;

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
		initializeSlidingPanel();
		initializeViewPager();
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

	public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

		private static final float MIN_SCALE = 0.85f;
		private static final float MIN_ALPHA = 0.5f;

		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) { // [-1,1]
				// Modify the default slide transition to shrink the page as well
				float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
				float vertMargin = pageHeight * (1 - scaleFactor) / 2;
				float horzMargin = pageWidth * (1 - scaleFactor) / 2;
				if (position < 0) {
					view.setTranslationX(horzMargin - vertMargin / 2);
				} else {
					view.setTranslationX(-horzMargin + vertMargin / 2);
				}

				// Scale the page down (between MIN_SCALE and 1)
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

				// Fade the page relative to its size.
				view.setAlpha(MIN_ALPHA +
					(scaleFactor - MIN_SCALE) /
						(1 - MIN_SCALE) * (1 - MIN_ALPHA));

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}


	@Override
	public void categorySelected(String category) {
		presenter.categorySelected(category);
	}
}

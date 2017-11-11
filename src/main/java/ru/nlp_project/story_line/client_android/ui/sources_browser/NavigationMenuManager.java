package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import ru.nlp_project.story_line.client_android.BuildConfig;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

/**
 * Менеджер меню навигации - управляет меню, инициализирует его и обеспечивает жиззненный цикл и
 * связь с основной Activity.
 */
public class NavigationMenuManager {

	// shift for start sources (first item - "search")
	private final int MENU_ITEM_SHIFT_FOR_SOURCES = 1;

	private final ISourcesBrowserView sourcesBrowser;
	private final RecyclerView navigationRecyclerView;
	private final ISourcesBrowserPresenter presenter;
	private final View header;


	@BindView(R.id.menu_header_subtitle)
	TextView headerSubtitleTextView;
	private RecyclerViewAdapter adapter;
	private List<NavigationMenuItem> menuItems = null;
	private NavigationMenuItem menuItemSearch;
	private NavigationMenuItem menuItemAbout;
	private NavigationMenuItem menuItemFeedback;
	/**
	 * Текущий выбранный элемент.
	 */
	private int currentRecyclerViewSelectedItem = MENU_ITEM_SHIFT_FOR_SOURCES;

	NavigationMenuManager(ISourcesBrowserPresenter presenter,
			ISourcesBrowserView sourcesBrowser,
			RecyclerView navigationRecyclerView,
			View header) {
		this.presenter = presenter;
		this.sourcesBrowser = sourcesBrowser;
		this.navigationRecyclerView = navigationRecyclerView;
		this.header = header;
	}

	void initialize() {
		menuItems = new ArrayList<>();
		ButterKnife.bind(this, header);
		initializeHeader();
		initializeStaticMenuItems();
		initializeRecyclerView();
	}

	private void initializeRecyclerView() {
		adapter = new NavigationMenuManager.RecyclerViewAdapter();
		LayoutManager linearLM = new LinearLayoutManager(sourcesBrowser.getContext());
		navigationRecyclerView.setLayoutManager(linearLM);
		navigationRecyclerView.setAdapter(adapter);
	}

	private void initializeHeader() {
		headerSubtitleTextView.setText(BuildConfig.VERSION_NAME);
	}

	private void initializeStaticMenuItems() {
		this.menuItemSearch = new NavigationMenuItem(R.string.menu_sources_browser_item_search,
				android.R.drawable.ic_menu_search,
				sourcesBrowser::onMenuItemSearch);
		this.menuItemAbout = new NavigationMenuItem(R.string.menu_sources_browser_item_about,
				android.R.drawable.ic_menu_help,
				sourcesBrowser::onMenuItemAbout);
		this.menuItemFeedback = new NavigationMenuItem(R.string.menu_sources_browser_item_feedback,
				android.R.drawable.ic_menu_send,
				sourcesBrowser::onMenuItemFeedback);
	}

	void setSelectedItem(int i) {
		// prev position
		adapter.notifyItemChanged(currentRecyclerViewSelectedItem);
		currentRecyclerViewSelectedItem = MENU_ITEM_SHIFT_FOR_SOURCES + i;
		// curr position
		adapter.notifyItemChanged(currentRecyclerViewSelectedItem);
	}

	void finishSourcesUpdates() {
		menuItems.clear();
		menuItems.add(menuItemSearch);

		for (int i = 0; i < presenter.getSources().size(); i++) {
			SourceBusinessModel source = presenter.getSources().get(i);
			int pos = i;
			menuItems
					.add(new NavigationMenuItem(source.getTitleShort(),
							android.R.drawable.ic_menu_view,
							new NavigationMenuItem.OnClickListener() {
								@Override
								public void onClick(View view) {
									sourcesBrowser.onMenuItemSource(pos);
								}
							}));
		}
		menuItems.add(menuItemFeedback);
		menuItems.add(menuItemAbout);
		// rebuild elements
		adapter.notifyDataSetChanged();
	}

	private static class NavigationMenuItem {

		String title = null;
		int titleResId = Integer.MIN_VALUE;
		OnClickListener onClickListener;
		int iconResId;

		NavigationMenuItem(int titleResId, int iconResId, OnClickListener onClickListener) {
			this.titleResId = titleResId;
			this.iconResId = iconResId;
			this.onClickListener = onClickListener;
		}

		NavigationMenuItem(String title, int iconResId, OnClickListener onClickListener) {
			this.title = title;
			this.iconResId = iconResId;
			this.onClickListener = onClickListener;
		}

		interface OnClickListener {

			void onClick(View view);
		}

	}

	private class RecyclerViewAdapter extends
			RecyclerView.Adapter<NavigationMenuManager.ViewHolder> {

		@Override
		public NavigationMenuManager.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
			Context context = parent.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);

			// Inflate the custom layout
			View menuItemView = inflater
					.inflate(R.layout.view_sources_browser_navigation_entry, parent, false);

			// Return a new holder instance
			return new ViewHolder(context, menuItemView);

		}

		@Override
		public void onBindViewHolder(NavigationMenuManager.ViewHolder viewHolder, int pos) {
//			if (currentRecyclerViewSelectedItem == pos) {
//				viewHolder.captionTextView.setTextColor(R.color.primary_text);
//			}

			NavigationMenuItem navMenuItem = menuItems.get(pos);
			if (navMenuItem.title != null) {
				viewHolder.captionTextView.setText(navMenuItem.title);
			} else {
				viewHolder.captionTextView.setText(navMenuItem.titleResId);
			}

			viewHolder.iconImageView.setImageResource(navMenuItem.iconResId);
		}

		@Override
		public int getItemCount() {
			return menuItems.size();
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

		@BindView(R.id.menu_item_caption)
		TextView captionTextView;
		@BindView(R.id.menu_item_icon)
		ImageView iconImageView;

		ViewHolder(Context context, View menuItemView) {
			super(menuItemView);
			ButterKnife.bind(this, menuItemView);
			// Attach a click listener to the entire row view
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			int position = getAdapterPosition();
			if (position
					!= RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
				menuItems.get(position).onClickListener.onClick(v);
			}
		}
	}
}

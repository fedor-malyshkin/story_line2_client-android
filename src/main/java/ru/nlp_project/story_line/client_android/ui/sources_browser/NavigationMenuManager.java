package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
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
	private RecyclerViewAdapter adapter;
	private List<NavigationMenuItem> menuItems = null;
	private NavigationMenuItem menuItemSearch;
	private NavigationMenuItem menuItemHelp;
	private NavigationMenuItem menuItemAbout;
	private NavigationMenuItem menuItemFeedback;
	/**
	 * Текущий выбранный элемент.
	 */
	private int currentRecyclerViewSelectedItem = MENU_ITEM_SHIFT_FOR_SOURCES;

	public NavigationMenuManager(ISourcesBrowserPresenter presenter,
			ISourcesBrowserView sourcesBrowser,
			RecyclerView navigationRecyclerView) {
		this.presenter = presenter;
		this.sourcesBrowser = sourcesBrowser;
		this.navigationRecyclerView = navigationRecyclerView;
	}

	public void initialize() {
		menuItems = new ArrayList<>();
		initializeStaticMenuItems();
		adapter = new NavigationMenuManager.RecyclerViewAdapter();
		LayoutManager linearLM = new LinearLayoutManager(sourcesBrowser.getContext());
		navigationRecyclerView.setLayoutManager(linearLM);
		navigationRecyclerView.setAdapter(adapter);
	}

	private void initializeStaticMenuItems() {
		this.menuItemSearch = new NavigationMenuItem("Search",
				sourcesBrowser::onMenuItemSearch);
		this.menuItemHelp = new NavigationMenuItem("Help",
				sourcesBrowser::onMenuItemHelp);
		this.menuItemAbout = new NavigationMenuItem("About",
				sourcesBrowser::onMenuItemAbout);
		this.menuItemFeedback = new NavigationMenuItem("Feedback",
				sourcesBrowser::onMenuItemFeedback);
	}

	public void setSelectedItem(int i) {
		currentRecyclerViewSelectedItem = MENU_ITEM_SHIFT_FOR_SOURCES + i;
// rebuild elements
		adapter.notifyDataSetChanged();
	}

	public void finishSourcesUpdates() {
		menuItems.clear();
		menuItems.add(menuItemSearch);

		for (int i = 0; i < presenter.getSources().size(); i++) {
			SourceBusinessModel source = presenter.getSources().get(i);
			int pos = i;
			menuItems
					.add(new NavigationMenuItem(source.getTitleShort(),
							new NavigationMenuItem.OnClickListener() {
								@Override
								public void onClick(View view) {
									sourcesBrowser.onMenuItemSource(pos);
								}
							}));
		}

		menuItems.add(menuItemHelp);
		menuItems.add(menuItemFeedback);
		menuItems.add(menuItemAbout);
		// rebuild elements
		adapter.notifyDataSetChanged();
	}

	private static class NavigationMenuItem {

		String title;
		OnClickListener onClickListener;

		public NavigationMenuItem(String title, OnClickListener onClickListener) {
			this.title = title;
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
			NavigationMenuManager.ViewHolder viewHolder = new NavigationMenuManager.ViewHolder(context,
					menuItemView);
			return viewHolder;

		}

		@Override
		public void onBindViewHolder(NavigationMenuManager.ViewHolder viewHolder, int pos) {
			if (currentRecyclerViewSelectedItem == pos) {
				viewHolder.titleTextView.setTextColor(R.color.primary_text);
			}

			// Get the data model based on position
			NavigationMenuItem menuItem = menuItems.get(pos);
			// Set item views based on your views and data model
			viewHolder.titleTextView.setText(menuItem.title);
		}

		@Override
		public int getItemCount() {
			return menuItems.size();
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

		// Your holder should contain a member variable
		// for any view that will be set as you render a row
		@BindView(R.id.menu_item_caption)
		public TextView titleTextView;
		private Context context;


		public ViewHolder(Context context, View menuItemView) {
			super(menuItemView);
			this.context = context;
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

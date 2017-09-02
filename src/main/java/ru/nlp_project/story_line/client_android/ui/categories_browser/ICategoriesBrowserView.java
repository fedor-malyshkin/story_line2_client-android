package ru.nlp_project.story_line.client_android.ui.categories_browser;

public interface ICategoriesBrowserView {
	interface ICategorySelectionListener {
		void categorySelected(String category);
	}

	void addCategoryOnTop(String name, String serverId);

	void commitAddCategoryOnTop();
}

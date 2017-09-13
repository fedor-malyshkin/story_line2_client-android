package ru.nlp_project.story_line.client_android.ui.news_tape;

import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;

/**
 * Created by fedor on 04.02.17.
 */

public interface INewsTapeView  {
	/**
	 * Активировать/деактивировать индикатор загрузки новостей.
	 */
	void showUpdateIndicator(boolean show);
	void addNewsHeader(int prevSize);

	/**
	 * Выполнить очистку списка новостей.
	 */
	void clearTape(int oldSize);
	void onNewsSelected(int position);
	/**
	 * Активировать/деактивировать подкачку новостей при прокрутке в конец.
	 */
	void enableNewsUploading(boolean enable);
}

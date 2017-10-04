package ru.nlp_project.story_line.client_android.ui.news_tape;

import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface INewsTapePresenter extends IPresenter<INewsTapeView> {

	/**
	 * Выполнить первоначальную загрузку/перезагрузку заголовков новостей.
	 */
	void reloadNewsHeaders();

	void initialize(String sourceName, String sourceTitle, String sourceTitleShort);

	/**
	 * Выполнить подзагрузку заголовков новостей и добавить их в интерфейс.
	 */
	void uploadMoreNewsHeaders();

	/**
	 * Получить количество заголовков новостей.
	 */
	int getNewsHeaderCount();

	/**
	 * Получить заголовок новости по внутреннему списку по номеру позиции.
	 *
	 * @param position позиция.
	 * @return заголовок новости
	 */
	NewsHeaderBusinessModel getNewsHeader(int position);

	/**
	 * Получить все заголовки новостей.
	 *
	 * @return все заголовки новостей
	 */
	List<NewsHeaderBusinessModel> getNewsHeaders();

	String getSourceTitleShort();
}

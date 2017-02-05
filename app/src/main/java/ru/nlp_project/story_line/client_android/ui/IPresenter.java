package ru.nlp_project.story_line.client_android.ui;

/**
 * Базовый класс, обязательный для реализации всеми presenter'ами.
 * Created by fedor on 04.02.17.
 */

public interface IPresenter<T> {
	void bindView(T view);

	void unbindView();
}

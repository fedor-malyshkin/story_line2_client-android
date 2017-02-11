package ru.nlp_project.story_line.client_android.ui.categories_browser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.dagger.CategoriesBrowserComponent;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;

/**
 * Created by fedor on 09.02.17.
 */

public class CategoriesBrowserFragment extends Fragment implements ICategoriesBrowserView {

	@Inject
	public ICategoriesBrowserPresenter presenter;
	@BindView(R.id.categories_browser_layout)
	ViewGroup layout;
	private int addTopCategoryPos = 0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View view = inflater.inflate(R.layout.fragment_categories_browser, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		CategoriesBrowserComponent builder = DaggerBuilder
			.createCategoriesBrowserBuilder();
		builder.inject(this);
		presenter.bindView(this);
		loadCategories();
	}

	/**
	 * Выполнить инициализацию элементов управления.
	 *
	 * Среди прочего загрузить список категорий и создать необходимые элементы управления.
	 */
	private void loadCategories() {
		presenter.loadCategories();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		presenter.unbindView();
	}

	@Override
	public void addCategoryInTop(String name, String serverId) {
		//set the properties for button
		Button btnTag = new Button(getContext());
		btnTag.setLayoutParams(
			new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		btnTag.setText(name + "-" + serverId);
		btnTag.setId(1);

		//add button to the layout
		layout.addView(btnTag, addTopCategoryPos++);
	}

	@Override
	public void noMoreAddCategory() {
		addTopCategoryPos = 0;
	}
}

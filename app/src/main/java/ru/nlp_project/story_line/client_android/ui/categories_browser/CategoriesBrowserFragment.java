package ru.nlp_project.story_line.client_android.ui.categories_browser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

	private void initilizeOnClickListener() {
		for (int i = 0; i < layout.getChildCount(); i++) {
			View child = layout.getChildAt(i);
			if (Button.class.isInstance(child)) {
				Button btn = (Button) child;
				btn.setOnClickListener(presenter::onCategorySelection);
			}
		}
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

	private void initializeActivityConnection() {
		FragmentActivity activity = getActivity();
		if (ICategorySelectionListener.class.isInstance(activity)) {
			presenter.setCategorySelectionListener((ICategorySelectionListener) activity);
		}

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
	public void addCategoryOnTop(String name, String serverId) {
		//set the properties for button
		Button btnTag = new Button(getContext());
		btnTag.setLayoutParams(
			new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		btnTag.setText(name + "-" + serverId);
		//add button to the layout
		layout.addView(btnTag, addTopCategoryPos++);
	}

	@Override
	public void commitAddCategoryOnTop() {
		// reset position counter
		addTopCategoryPos = 0;
		// after finish - initialize control and add listeners
		initilizeOnClickListener();
		initializeActivityConnection();
	}
}

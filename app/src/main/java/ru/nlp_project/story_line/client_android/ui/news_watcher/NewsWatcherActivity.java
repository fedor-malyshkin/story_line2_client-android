package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import java.util.ArrayList;
import ru.nlp_project.story_line.client_android.R;

public class NewsWatcherActivity extends AppCompatActivity implements INewsWatcherView {

	public static final String INTENT_KEY_ARTICLES = "articles";
	public static final String INTENT_KEY_POSITION = "position";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_watcher);
		ButterKnife.bind(this);

		Intent intent = getIntent();
		int intExtra = intent.getIntExtra(NewsWatcherActivity.INTENT_KEY_POSITION, 0);
		ArrayList<String> arrayExtra = intent
			.getStringArrayListExtra(NewsWatcherActivity.INTENT_KEY_ARTICLES);

		Toast.makeText(this, intExtra + "",
			Toast.LENGTH_SHORT).show();
	}
}

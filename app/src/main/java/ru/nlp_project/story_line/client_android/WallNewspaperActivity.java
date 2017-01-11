package ru.nlp_project.story_line.client_android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ru.nlp_project.story_line.client_android.data.model.NewsArticleHeader;

import static ru.nlp_project.story_line.client_android.R.id.rvNews;

public class WallNewspaperActivity extends Activity {
	public static final String TAG = WallNewspaperActivity.class.getName();
	private IEventProcessor processor = null;
	private List<NewsArticleHeader> articles;
	private RecyclerView newsRecyclerView;
	private ServiceConnectionImpl serviceConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wall_newspaper);
		initializeEventProcessingSerice();
		initializeRecyclerView();
		initializeOtherControls();
	}

	private void initializeOtherControls() {
		Button populateDatabaseButton = (Button) findViewById(R.id.populateDatabase);
		populateDatabaseButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (processor == null) return;
				processor.populateDB();
			pumpData() ;
			}
		});
	}

	private void initializeEventProcessingSerice() {
		Intent intent = new Intent(this, EventProcessingService.class);
		ComponentName name = startService(intent);

		this.serviceConnection = new ServiceConnectionImpl();
		bindService(new Intent(this, EventProcessingService.class), serviceConnection,
				BIND_AUTO_CREATE);
	}

	/**
	 * Загрузить данные в интерфейс.
	 */
	private void pumpData() {
		if (processor == null) return;
		articles.clear();
		articles.addAll(processor.getDefaultNewsArticleHeaders());
		newsRecyclerView.getAdapter().notifyDataSetChanged();
	}

	class ServiceConnectionImpl implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "onServiceConnected");
			EventProcessingService.ServiceBinder serviceBinder = (EventProcessingService.ServiceBinder) service;
			processor = serviceBinder.getService();
			pumpData();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	}

	private void initializeRecyclerView() {
		// Lookup the recyclerview in activity layout
		newsRecyclerView = (RecyclerView) findViewById(rvNews);

		// Initialize contacts
		articles = new ArrayList<>();
		if (processor != null)
			articles.addAll(processor.getDefaultNewsArticleHeaders());

		// Create adapter passing in the sample user data
		WallNewspaperRecyclerViewAdapter adapter = new WallNewspaperRecyclerViewAdapter(this,
				articles);
		// Attach the adapter to the recyclerview to populate items
		newsRecyclerView.setAdapter(adapter);
		// Set layout manager to position the items
		newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		// That's all!
		RecyclerView.ItemDecoration itemDecoration = new
				DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
		newsRecyclerView.addItemDecoration(itemDecoration);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(this.serviceConnection);
	}
}

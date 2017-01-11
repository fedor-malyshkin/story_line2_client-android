package ru.nlp_project.story_line.client_android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.List;

import javax.inject.Inject;

import ru.nlp_project.story_line.client_android.builder.ApplicationComponent;
import ru.nlp_project.story_line.client_android.builder.ApplicationModule;
import ru.nlp_project.story_line.client_android.builder.DaggerApplicationComponent;
import ru.nlp_project.story_line.client_android.core.IDataManager;
import ru.nlp_project.story_line.client_android.datamodel.NewsArticleHeader;

public class EventProcessingService extends Service implements IEventProcessor {
	public static class ServiceBinder extends Binder {
		private EventProcessingService service = null;

		public ServiceBinder(EventProcessingService service) {
			this.service = service;
		}

		public EventProcessingService getService() {
			return service;
		}
	}

	@Inject
	IDataManager dataManager;

	public EventProcessingService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ApplicationModule applicationModule = new ApplicationModule(getApplicationContext());
		ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(applicationModule).build();
		applicationComponent.inject(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder(this);
	}

	@Override
	public List<NewsArticleHeader> getDefaultNewsArticleHeaders() {
		return dataManager.getCachedLocalyNewsArticleHeaders(-1);
	}

	@Override
	public void populateDB() {
		dataManager.populateDB();
	}

	@Override
	public void updateData() {
		dataManager.downloadNewsArticles();
	}

}

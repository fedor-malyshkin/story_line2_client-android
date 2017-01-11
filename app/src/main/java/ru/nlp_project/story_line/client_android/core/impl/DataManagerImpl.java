package ru.nlp_project.story_line.client_android.core.impl;

import java.util.List;

import ru.nlp_project.story_line.client_android.core.DownloadDataResult;
import ru.nlp_project.story_line.client_android.core.IDataManager;
import ru.nlp_project.story_line.client_android.core.ILocalDataStorage;
import ru.nlp_project.story_line.client_android.core.IRemoteDataStorage;
import ru.nlp_project.story_line.client_android.datamodel.NewsArticle;
import ru.nlp_project.story_line.client_android.datamodel.NewsArticleHeader;

/**
 * Created by fedor on 11.01.17.
 */

public class DataManagerImpl implements IDataManager {
	ILocalDataStorage localDataStorage;
	IRemoteDataStorage remoteDataStorage;

	public DataManagerImpl(
			ILocalDataStorage localDataStorage,
			IRemoteDataStorage remoteDataStorage) {
		this.localDataStorage = localDataStorage;
		this.remoteDataStorage = remoteDataStorage;
	}

	@Override
	public List<NewsArticleHeader> getCachedLocalyNewsArticleHeaders(int limit) {
		return localDataStorage.getNewsArticleHeaders(limit);
	}

	@Override
	public DownloadDataResult downloadNewsArticles() {
		List<NewsArticle> fullArticles = remoteDataStorage.getNewsArticles();
		localDataStorage.storeNewsArticles(fullArticles);
		return DownloadDataResult.RECEIVED_UPDATES;
	}

	@Override
	public DownloadDataResult downloadAdditionalNewsArticles(long fromId) {
		List<NewsArticle> fullArticles = remoteDataStorage.getNewsArticles(fromId);
		localDataStorage.storeNewsArticles(fullArticles);
		return DownloadDataResult.RECEIVED_UPDATES;
	}

	@Override
	public List<NewsArticleHeader> getAdditionalNewsArticles(long fromId, int limit) {
		return localDataStorage.getAdditionalNewsArticleHeaders(fromId, limit);
	}

	@Override
	public void populateDB() {
		localDataStorage.populateDB();
	}
}

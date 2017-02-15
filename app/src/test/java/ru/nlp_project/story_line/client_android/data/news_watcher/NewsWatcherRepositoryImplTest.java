package ru.nlp_project.story_line.client_android.data.news_watcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.MaybeSubject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;


public class NewsWatcherRepositoryImplTest {

	private RetrofiService retrofiService;
	private TestScheduler bckgScheduler;
	private NewsWatcherRepositoryImpl testable;
	private ILocalDBStorage localDBStorage;
	private NewsWatcherRetrofitService service;

	@Before
	public void setUp() throws Exception {
		testable = new NewsWatcherRepositoryImpl();
		retrofiService = Mockito.mock(RetrofiService.class);
		testable.retrofiService = retrofiService;
		bckgScheduler = new TestScheduler();
		testable.bckgScheduler = bckgScheduler;
		localDBStorage = Mockito.mock(ILocalDBStorage.class);
		testable.localDBStorage = localDBStorage;
		service = mock(NewsWatcherRetrofitService.class);

	}


	@Test
	public void testCreateStream_NoAction() {
		// long chain of initialization
		MaybeSubject<NewsArticleDataModel> netSource = MaybeSubject.create();
		MaybeSubject<NewsArticleDataModel> dbSource = MaybeSubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getNewsWatcherService()).thenReturn(service);
		when(service.getNewsArticleById(any(String.class))).thenReturn(netSource);
		when(localDBStorage.createNewsArticleStream(any(String.class))).thenReturn(dbSource);
		Single<NewsArticleDataModel> actualStream = testable.createCachedNewsArticleStream
			("someDomain");

		Assertions.assertThat(actualStream).isNotNull();
		verify(localDBStorage, never()).commitNewsArticleCacheUpdate();
		verify(localDBStorage, never()).addNewsArticleToCache(any());
	}


	// getting from DB first order
	@Test
	public void testCreateStream_Success() {
		// long chain of initialization
		MaybeSubject<NewsArticleDataModel> netSource = MaybeSubject.create();
		MaybeSubject<NewsArticleDataModel> dbSource = MaybeSubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getNewsWatcherService()).thenReturn(service);
		when(service.getNewsArticleById(any(String.class))).thenReturn(netSource);
		when(localDBStorage.createNewsArticleStream(any(String.class))).thenReturn(dbSource);

		Single<NewsArticleDataModel> actualStream = testable.createCachedNewsArticleStream
			("someDomain");

		// prepare datas
		// (Long id, String content, String path, String title, Date date,
		// Date processingDate, String source, String serverId
		dbSource.onSuccess(new NewsArticleDataModel((long) 0, "Content of Новость 3",
			"https://www.bnkomi.ru/data/news/59446/", "Новость 3", new Date(3), new Date(30),
			"bnkomi.ru",
			"asbd3"));

		TestObserver<NewsArticleDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		// no db actions
		verify(localDBStorage, never()).addNewsArticleToCache(any());
		verify(localDBStorage, never()).commitNewsArticleCacheUpdate();
		verify(localDBStorage, never()).cancelNewsArticleCacheUpdate(any());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(1);
	}

	@Test
	public void testCreateStream_NoInDBCache() {
		// long chain of initialization
		MaybeSubject<NewsArticleDataModel> netSource = MaybeSubject.create();
		MaybeSubject<NewsArticleDataModel> dbSource = MaybeSubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getNewsWatcherService()).thenReturn(service);
		when(service.getNewsArticleById(any(String.class))).thenReturn(netSource);
		when(localDBStorage.createNewsArticleStream(any(String.class))).thenReturn(dbSource);
		Single<NewsArticleDataModel> actualStream = testable.createCachedNewsArticleStream
			("someDomain");

		// no_in_db and no_exception
		dbSource.onComplete();

		netSource.onSuccess(new NewsArticleDataModel((long) 0, "Content of Новость 3",
			"https://www.bnkomi.ru/data/news/59446/", "Новость 3", new Date(3), new Date(30),
			"bnkomi.ru",
			"asbd3"));

		TestObserver<NewsArticleDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(1)).addNewsArticleToCache(any());
		inOrder.verify(localDBStorage, times(1)).commitNewsArticleCacheUpdate();

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(1);
	}

}
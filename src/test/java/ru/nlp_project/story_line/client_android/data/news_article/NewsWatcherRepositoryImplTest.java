package ru.nlp_project.story_line.client_android.data.news_article;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.MaybeSubject;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;


public class NewsWatcherRepositoryImplTest {

	private RetrofitService retrofitService;
	private TestScheduler bckgScheduler;
	private NewsArticlesRepositoryImpl testable;
	private ILocalDBStorage localDBStorage;
	private NewsArticlesRetrofitService service;

	@Before
	public void setUp() throws Exception {
		testable = new NewsArticlesRepositoryImpl();
		retrofitService = Mockito.mock(RetrofitService.class);
		testable.retrofitService = retrofitService;
		bckgScheduler = new TestScheduler();
		testable.bckgScheduler = bckgScheduler;
		localDBStorage = Mockito.mock(ILocalDBStorage.class);
		testable.localDBStorage = localDBStorage;
		service = mock(NewsArticlesRetrofitService.class);

	}


	@Test
	public void testCreateStream_NoAction() {
		// long chain of initialization
		MaybeSubject<NewsArticleDataModel> netSource = MaybeSubject.create();
		MaybeSubject<NewsArticleDataModel> dbSource = MaybeSubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getNewsWatcherService()).thenReturn(service);
		when(service.getNewsArticleById(anyString())).thenReturn(netSource);
		when(localDBStorage.createNewsArticleStream(anyString())).thenReturn(dbSource);
		Single<NewsArticleBusinessModel> actualStream = testable.createNewsArticleRemoteCachedStream
				("someDomain");

		Assertions.assertThat(actualStream).isNotNull();
		verify(localDBStorage, never()).addNewsArticleToCache(any());
	}


	// getting from DB first order
	@Test
	public void testCreateStream_Success() {
		// long chain of initialization
		MaybeSubject<NewsArticleDataModel> netSource = MaybeSubject.create();
		MaybeSubject<NewsArticleDataModel> dbSource = MaybeSubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getNewsWatcherService()).thenReturn(service);
		when(service.getNewsArticleById(anyString())).thenReturn(netSource);
		when(localDBStorage.createNewsArticleStream(anyString())).thenReturn(dbSource);

		Single<NewsArticleBusinessModel> actualStream = testable.createNewsArticleRemoteCachedStream
				("NewsArticleBusinessModel");

		// prepare datas
		// (Long id, String content, String path, String title, Date date,
		// Date processingDate, String source, String serverId
		netSource.onSuccess(new NewsArticleDataModel((long) 0, "Content of Новость 3",
				"https://www.bnkomi.ru/data/news/59446/", "Новость 3", new Date(30),
				"bnkomi.ru",
				"asbd3"));

		TestObserver<NewsArticleBusinessModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(1)).addNewsArticleToCache(any());

		testObserver.assertNoErrors();
		testObserver.assertValueCount(1);
		testObserver.assertComplete();

	}

	@Test
	public void testCreateStream_NoInDBCache() {
		// long chain of initialization
		MaybeSubject<NewsArticleDataModel> netSource = MaybeSubject.create();
		MaybeSubject<NewsArticleDataModel> dbSource = MaybeSubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getNewsWatcherService()).thenReturn(service);
		when(service.getNewsArticleById(anyString())).thenReturn(netSource);
		when(localDBStorage.createNewsArticleStream(anyString())).thenReturn(dbSource);
		Single<NewsArticleBusinessModel> actualStream = testable.createNewsArticleRemoteCachedStream
				("someDomain");

		// no_in_db and no_exception
		dbSource.onComplete();

		netSource.onSuccess(new NewsArticleDataModel((long) 0, "Content of Новость 3",
				"https://www.bnkomi.ru/data/news/59446/", "Новость 3", new Date(30),
				"bnkomi.ru",
				"asbd3"));

		TestObserver<NewsArticleBusinessModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(1)).addNewsArticleToCache(any());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(1);
	}

}
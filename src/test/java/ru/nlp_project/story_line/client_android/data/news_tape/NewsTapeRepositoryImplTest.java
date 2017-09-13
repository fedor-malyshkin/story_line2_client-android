package ru.nlp_project.story_line.client_android.data.news_tape;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.ReplaySubject;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implements;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;

@RunWith(RobolectricTestRunner.class)
public class NewsTapeRepositoryImplTest {

	private RetrofiService retrofiService;
	private TestScheduler bckgScheduler;
	private NewsTapeRepositoryImpl testable;
	private ILocalDBStorage localDBStorage;
	private NewsTapeRetrofitService service;

	@Before
	public void setUp() throws Exception {
		testable = new NewsTapeRepositoryImpl();
		retrofiService = Mockito.mock(RetrofiService.class);
		testable.retrofiService = retrofiService;
		bckgScheduler = new TestScheduler();
		testable.bckgScheduler = bckgScheduler;
		localDBStorage = Mockito.mock(ILocalDBStorage.class);
		testable.localDBStorage = localDBStorage;
		service = mock(NewsTapeRetrofitService.class);

	}

	@Test
	public void testCreateStream_NoAction() {
		// long chain of initialization
		ReplaySubject<List<NewsHeaderDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<NewsHeaderDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getNewsTapeService()).thenReturn(service);
		when(service.listHeaders(any(String.class), anyInt(), isNull())).thenReturn(netSource);
		when(localDBStorage.createNewsHeaderStream("someDomain")).thenReturn(dbSource);
		Observable<NewsHeaderDataModel> actualStream = testable.createNewsHeaderRemoteCachedStream
				("someDomain", null);

		Assertions.assertThat(actualStream).isNotNull();
		verify(localDBStorage, never()).addNewsHeaderToCache(any());
	}

	@Test
	public void testCreateStream_Success() {
		// long chain of initialization
		ReplaySubject<List<NewsHeaderDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<NewsHeaderDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getNewsTapeService()).thenReturn(service);
		when(service.listHeaders(any(String.class), anyInt(), isNull())).thenReturn(netSource);
		when(localDBStorage.createNewsHeaderStream("someDomain")).thenReturn(dbSource);
		Observable<NewsHeaderDataModel> actualStream = testable.createNewsHeaderRemoteCachedStream
				("someDomain", null);

		// prepare datas
		List<NewsHeaderDataModel> list = new ArrayList<>();
		list.add(new NewsHeaderDataModel((long) 0, "news0", "source0", null, "serverId0"));
		list.add(new NewsHeaderDataModel((long) 1, "news1", "source1", null, "serverId1"));
		netSource.onNext(list);
		netSource.onComplete();

		TestObserver<NewsHeaderDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(2)).addNewsHeaderToCache(any());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(2);
	}

	@Test
	@Config(shadows = ShadowLog.class, manifest = Config.NONE)
	public void testCreateStream_NetworkError() {

		// long chain of initialization
		ReplaySubject<List<NewsHeaderDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<NewsHeaderDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getNewsTapeService()).thenReturn(service);
		when(service.listHeaders(any(String.class), anyInt(), isNull())).thenReturn(netSource);
		when(localDBStorage.createNewsHeaderStream("someDomain")).thenReturn(dbSource);

		Observable<NewsHeaderDataModel> actualStream = testable.createNewsHeaderRemoteCachedStream
				("someDomain", null);

		// prepare datas
		netSource.onError(new IllegalStateException("test exception"));

		// prepare datas
		dbSource.onNext(new NewsHeaderDataModel((long) 0, "news0", "source0", null, "serverId0"));
		dbSource.onNext(new NewsHeaderDataModel((long) 1, "news1", "source1", null, "serverId1"));
		dbSource.onNext(new NewsHeaderDataModel((long) 2, "news2", "source2", null, "serverId2"));
		dbSource.onComplete();

		TestObserver<NewsHeaderDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
	}

	@Implements(Log.class)
	public static class ShadowLog {

		public static int e(java.lang.String tag, java.lang.String msg, Throwable t) {
			System.out.println("[" + tag + "] " + msg);
			return 0;
		}
	}

}
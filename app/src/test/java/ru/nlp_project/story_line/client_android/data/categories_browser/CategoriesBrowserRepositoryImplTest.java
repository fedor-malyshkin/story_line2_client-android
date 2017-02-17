package ru.nlp_project.story_line.client_android.data.categories_browser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsTapeRepositoryImplTest;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsTapeRepositoryImplTest.ShadowLog;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;

// @RunWith(RobolectricTestRunner.class)
public class CategoriesBrowserRepositoryImplTest {

	private RetrofiService retrofiService;
	private TestScheduler bckgScheduler;
	private CategoriesBrowserRepositoryImpl testable;
	private ILocalDBStorage localDBStorage;
	private CategoriesBrowserRetrofitService service;

	@Implements(Log.class)
	public static class ShadowLog {
		public static int e(java.lang.String tag, java.lang.String msg, Throwable t) {
			System.out.println("[" + tag + "] " + msg);
			return 0;
		}
	}


	@Before
	public void setUp() {
		testable = new CategoriesBrowserRepositoryImpl();
		retrofiService = Mockito.mock(RetrofiService.class);
		testable.retrofiService = retrofiService;
		bckgScheduler = new TestScheduler();
		testable.bckgScheduler = bckgScheduler;
		localDBStorage = Mockito.mock(ILocalDBStorage.class);
		testable.localDBStorage = localDBStorage;
		service = mock(CategoriesBrowserRetrofitService.class);
	}

	@Test
	public void testCreateStream_NoAction() {
		// long chain of initialization
		ReplaySubject<List<CategoryDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<CategoryDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getCategoriesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createCategoryStream()).thenReturn(dbSource);
		Observable<CategoryDataModel> actualStream = testable.createCategoryStream();

		Assertions.assertThat(actualStream).isNotNull();
		verify(localDBStorage, never()).commitCategoryCacheUpdate(anyLong());
		verify(localDBStorage, never()).addCategoryToCache(anyLong(), any());
	}


	@Test
	public void testCreateStream_Success() {
		// long chain of initialization
		ReplaySubject<List<CategoryDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<CategoryDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getCategoriesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createCategoryStream()).thenReturn(dbSource);

		Observable<CategoryDataModel> actualStream = testable.createCategoryStream();

		// prepare datas
		List<CategoryDataModel> list = new ArrayList<>();
		list.add(new CategoryDataModel((long) 0, "name0", "serverId0"));
		list.add(new CategoryDataModel((long) 1, "name1", "serverId1"));
		netSource.onNext(list);
		netSource.onComplete();

		TestObserver<CategoryDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(2)).addCategoryToCache(anyLong(), any());
		inOrder.verify(localDBStorage, times(1)).commitCategoryCacheUpdate(anyLong());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(2);
	}

	@Test
	@Config(shadows=ShadowLog.class, manifest = Config.NONE)
	public void testCreateStream_NetworkError() {
		// long chain of initialization
		ReplaySubject<List<CategoryDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<CategoryDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getCategoriesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createCategoryStream()).thenReturn(dbSource);

		Observable<CategoryDataModel> actualStream = testable.createCategoryStream();

		// prepare datas
		netSource.onError(new IllegalStateException("test exception"));

		dbSource.onNext(new CategoryDataModel((long) 0, "name0", "serverId0"));
		dbSource.onNext(new CategoryDataModel((long) 1, "name1", "serverId1"));
		dbSource.onNext(new CategoryDataModel((long) 2, "name2", "serverId2"));
		dbSource.onComplete();

		TestObserver<CategoryDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(1)).cancelCategoryCacheUpdate(anyLong());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
	}


}
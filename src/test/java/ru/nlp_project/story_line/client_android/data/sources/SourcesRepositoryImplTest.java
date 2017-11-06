package ru.nlp_project.story_line.client_android.data.sources;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.ReplaySubject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implements;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class SourcesRepositoryImplTest {

	private RetrofitService retrofitService;
	private TestScheduler bckgScheduler;
	private SourcesRepositoryImpl testable;
	private ILocalDBStorage localDBStorage;
	private SourcesRetrofitService service;

	@Before
	public void setUp() {
		testable = new SourcesRepositoryImpl();
		retrofitService = Mockito.mock(RetrofitService.class);
		testable.retrofitService = retrofitService;
		bckgScheduler = new TestScheduler();
		testable.bckgScheduler = bckgScheduler;
		localDBStorage = Mockito.mock(ILocalDBStorage.class);
		testable.localDBStorage = localDBStorage;
		service = mock(SourcesRetrofitService.class);
	}

	@Test
	public void testCreateSourceStreamRemoteCached_NoAction() {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);

		Observable<SourceBusinessModel> expectedStream = testable.createSourceStreamRemoteCached();

		assertThat(expectedStream).isNotNull();
		verify(localDBStorage, never()).addSource(any());
	}


	@Test
	public void testCreateSourceStreamRemoteCached_Success() {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);

		Observable<SourceBusinessModel> expectedStream = testable.createSourceStreamRemoteCached();

		// prepare data
		List<SourceDataModel> list = new ArrayList<>();
		list.add(new SourceDataModel(null, "domain0", "name0", "short0"));
		list.add(new SourceDataModel(null, "domain1", "name1", "short1"));
		netSource.onNext(list);
		netSource.onComplete();

		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		expectedStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		verify(localDBStorage, times(2)).addSource(any());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(2);
	}

	/**
	 * test with using toList without subscription
	 */
	@Test
	public void testCreateSourceStreamRemoteCached_toList_Success()
			throws ExecutionException, InterruptedException {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);

		Observable<SourceBusinessModel> expectedStream = testable.createSourceStreamRemoteCached();

		// prepare data
		List<SourceDataModel> list = new ArrayList<>();
		list.add(new SourceDataModel(null, "domain0", "name0", "short0"));
		list.add(new SourceDataModel(null, "domain1", "name1", "short1"));
		netSource.onNext(list);
		netSource.onComplete();

		List<SourceBusinessModel> expected = new ArrayList<>();
		Future<List<SourceBusinessModel>> future = expectedStream.toList().toFuture();
		// run scheduler
		bckgScheduler.triggerActions();
		List<SourceBusinessModel> models = future.get();

		assertThat(models).describedAs("Check that collect all emitted elements").hasSize(2);

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(2)).addSource(any());
	}


	@Test
	public void testCreateSourceStreamRemoteCached_NetworkError() {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofitService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);

		Observable<SourceBusinessModel> expectedStream = testable.createSourceStreamRemoteCached();

		// prepare datas
		netSource.onError(new IllegalStateException("test exception"));

		dbSource.onNext(new SourceDataModel(null, "domain0", "name0", "short0_"));
		dbSource.onNext(new SourceDataModel(null, "domain1", "name1", "short1_"));
		dbSource.onNext(new SourceDataModel(null, "domain2", "name2", "short2_"));
		dbSource.onComplete();

		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		expectedStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, never()).addSource(any());

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
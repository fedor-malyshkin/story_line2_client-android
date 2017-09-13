package ru.nlp_project.story_line.client_android.data.sources_browser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.ReplaySubject;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;

public class SourcesBrowserRepositoryImplTest {

	private RetrofiService retrofiService;
	private TestScheduler bckgScheduler;
	private SourcesBrowserRepositoryImpl testable;
	private ILocalDBStorage localDBStorage;
	private SourcesBrowserRetrofitService service;

	@Before
	public void setUp() {
		testable = new SourcesBrowserRepositoryImpl();
		retrofiService = Mockito.mock(RetrofiService.class);
		testable.retrofiService = retrofiService;
		bckgScheduler = new TestScheduler();
		testable.bckgScheduler = bckgScheduler;
		localDBStorage = Mockito.mock(ILocalDBStorage.class);
		testable.localDBStorage = localDBStorage;
		service = mock(SourcesBrowserRetrofitService.class);
	}

	@Test
	public void testCreateSourceStream_NoAction() {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);
		Observable<SourceDataModel> actualStream = testable.createSourceRemoteCachedStream();

		Assertions.assertThat(actualStream).isNotNull();
		verify(localDBStorage, never()).addSourceToCache(any());
	}


	@Test
	public void testCreateSourceStream_Success() {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);

		Observable<SourceDataModel> actualStream = testable.createSourceRemoteCachedStream();

		// prepare data
		List<SourceDataModel> list = new ArrayList<>();
		list.add(new SourceDataModel(null, "domain0", "name0", "short0"));
		list.add(new SourceDataModel(null, "domain1", "name1", "short1"));
		netSource.onNext(list);
		netSource.onComplete();

		TestObserver<SourceDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, times(2)).addSourceToCache(any());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(2);
	}

	@Test
	public void testCreateSourceStream_NetworkError() {
		// long chain of initialization
		ReplaySubject<List<SourceDataModel>> netSource = ReplaySubject.create();
		ReplaySubject<SourceDataModel> dbSource = ReplaySubject.create();
		Observable tempMock = mock(Observable.class);
		when(retrofiService.getSourcesBrowserService()).thenReturn(service);
		when(service.list()).thenReturn(netSource);
		when(localDBStorage.createSourceStream()).thenReturn(dbSource);

		Observable<SourceDataModel> actualStream = testable.createSourceRemoteCachedStream();

		// prepare datas
		netSource.onError(new IllegalStateException("test exception"));

		dbSource.onNext(new SourceDataModel(null, "domain0", "name0", "short0"));
		dbSource.onNext(new SourceDataModel(null, "domain1", "name1", "short1"));
		dbSource.onNext(new SourceDataModel(null, "domain2", "name2", "short2"));
		dbSource.onComplete();

		TestObserver<SourceDataModel> testObserver = TestObserver.create();
		actualStream.subscribe(testObserver);
		// run scheduler
		bckgScheduler.triggerActions();

		InOrder inOrder = inOrder(localDBStorage);
		inOrder.verify(localDBStorage, never()).addSourceToCache(any());

		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
	}


}
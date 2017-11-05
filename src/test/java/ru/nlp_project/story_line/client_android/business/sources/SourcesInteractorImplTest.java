package ru.nlp_project.story_line.client_android.business.sources;

import static org.mockito.Mockito.*;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.ReplaySubject;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.sources.ISourcesRepository;

public class SourcesInteractorImplTest {

	private SourcesInteractorImpl testable;
	private ISourcesRepository sourceRepository;


	@Before
	public void setUp() {
		testable = new SourcesInteractorImpl();
		sourceRepository = mock(ISourcesRepository.class);
		testable.repository = sourceRepository;
	}


	/**
	 * При сетевой ошибке - переходим на использование локального хранилища.
	 */
	@Test
	public void createCombinedSourcePreferencesStream_ErrorNetwork() throws Exception {
		// prepare
		ReplaySubject<SourceBusinessModel> observableLocal = ReplaySubject.create();
		observableLocal
				.onNext(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1, null));
		observableLocal
				.onNext(new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2, null));
		observableLocal
				.onNext(new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3, null));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceBusinessModel> observableRemote = ReplaySubject.create();
		observableRemote.onError(new IllegalStateException());
		when(sourceRepository.createSourceRemoteStream()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcesStreamRemoteCached();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1, null),
						new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2, null),
						new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3, null));
		testObserver.assertValueSequence(expected);
	}

	/**
	 * Выполнение большинства случаев - нет изменений.
	 */
	@Test
	public void createCombinedSourcePreferencesStream_NoChanges() throws Exception {
		// prepare
		ReplaySubject<SourceBusinessModel> observableLocal = ReplaySubject.create();
		observableLocal
				.onNext(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1, null));
		observableLocal
				.onNext(new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2, null));
		observableLocal
				.onNext(new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3, null));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceBusinessModel> observableRemote = ReplaySubject.create();
		observableRemote.onNext(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK"));
		observableRemote.onNext(new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7"));
		observableRemote.onNext(new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM"));
		observableRemote.onComplete();
		when(sourceRepository.createSourceRemoteStream()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcesStreamRemoteCached();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1, null),
						new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2, null),
						new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3, null));
		testObserver.assertValueSequence(expected);
	}

	/**
	 * Изменения есть: на сервере появился новый источник.
	 */
	@Test
	public void createCombinedSourcePreferencesStream_NewFromRemote() throws Exception {
		// prepare
		ReplaySubject<SourceBusinessModel> observableLocal = ReplaySubject.create();
		observableLocal
				.onNext(new SourceBusinessModel(new Long(1), "bnkomi.ru", "BNKLong", "BNK", true, 1, null));
		observableLocal
				.onNext(new SourceBusinessModel(new Long(2), "7x7.ru", "7x7Long", "7x7", true, 2, null));
		observableLocal
				.onNext(
						new SourceBusinessModel(new Long(3), "komiinform.ru", "KIMLong", "KIM", true, 3, null));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceBusinessModel> observableRemote = ReplaySubject.create();
		observableRemote.onNext(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK"));
		observableRemote.onNext(new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7"));
		observableRemote.onNext(new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM"));
		observableRemote.onNext(new SourceBusinessModel(null, "new_source.ru", "NSLong", "NS"));
		observableRemote.onComplete();
		when(sourceRepository.createSourceRemoteStream()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcesStreamRemoteCached();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(4);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(new Long(1), "bnkomi.ru", "BNKLong", "BNK", true, 1, null),
						new SourceBusinessModel(new Long(2), "7x7.ru", "7x7Long", "7x7", true, 2, null),
						new SourceBusinessModel(new Long(3), "komiinform.ru", "KIMLong", "KIM", true, 3, null),
						new SourceBusinessModel(null, "new_source.ru", "NSLong", "NS", true, -1, null));
		testObserver.assertValueSequence(expected);

	}

	/**
	 * Изменения есть: на сервере пропал ранее существующий источник (молча возвращаем меньшее
	 * кол-во).
	 */

	@Test
	public void createCombinedSourcePreferencesStream_AbsentOnRemote() throws Exception {
		// prepare
		ReplaySubject<SourceBusinessModel> observableLocal = ReplaySubject.create();
		observableLocal
				.onNext(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1, null));
		observableLocal
				.onNext(new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2, null));
		observableLocal
				.onNext(new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3, null));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceBusinessModel> observableRemote = ReplaySubject.create();
		observableRemote.onNext(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK"));
		observableRemote.onNext(new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7"));
		observableRemote.onComplete();
		when(sourceRepository.createSourceRemoteStream()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcesStreamRemoteCached();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(2);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1, null),
						new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2, null)
				);
		testObserver.assertValueSequence(expected);

	}


}
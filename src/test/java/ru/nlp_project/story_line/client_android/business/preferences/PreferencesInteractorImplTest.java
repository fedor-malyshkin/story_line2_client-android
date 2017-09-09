package ru.nlp_project.story_line.client_android.business.preferences;

import static org.mockito.Mockito.*;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.ReplaySubject;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;

public class PreferencesInteractorImplTest {

	private PreferencesInteractorImpl testable;
	private ISourcesBrowserRepository sourceRepository;


	@Before
	public void setUp() {
		testable = new PreferencesInteractorImpl();
		sourceRepository = mock(ISourcesBrowserRepository.class);
		testable.repository = sourceRepository;
	}

	/**
	 * При сетевой ошибке - переходим на использование локального хранилища.
	 *
	 * @throws Exception
	 */
	@Test
	public void createCombinedSourcePreferencesStream_ErrorNetwork() throws Exception {
		// prepare
		ReplaySubject<SourceDataModel> observableLocal = ReplaySubject.create();
		observableLocal.onNext(new SourceDataModel(null,"bnkomi.ru", "BNKLong", "BNK", true, 1));
		observableLocal.onNext(new SourceDataModel(null,"7x7.ru", "7x7Long", "7x7", true, 2));
		observableLocal.onNext(new SourceDataModel(null,"komiinform.ru", "KIMLong", "KIM", true, 3));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceDataModel> observableRemote = ReplaySubject.create();
		observableRemote.onError(new IllegalStateException());
		when(sourceRepository.createSourceStreamRemote()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcePreferencesStream();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1),
						new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2),
						new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3));
		testObserver.assertValueSequence(expected);
	}

	/**
	 * Выполнение большинства случаев - нет изменений.
	 */
	@Test
	public void createCombinedSourcePreferencesStream_NoChanges() throws Exception {
		// prepare
		ReplaySubject<SourceDataModel> observableLocal = ReplaySubject.create();
		observableLocal.onNext(new SourceDataModel(null,"bnkomi.ru", "BNKLong", "BNK", true, 1));
		observableLocal.onNext(new SourceDataModel(null,"7x7.ru", "7x7Long", "7x7", true, 2));
		observableLocal.onNext(new SourceDataModel(null,"komiinform.ru", "KIMLong", "KIM", true, 3));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceDataModel> observableRemote = ReplaySubject.create();
		observableRemote.onNext(new SourceDataModel(null,"bnkomi.ru", "BNKLong", "BNK"));
		observableRemote.onNext(new SourceDataModel(null,"7x7.ru", "7x7Long", "7x7"));
		observableRemote.onNext(new SourceDataModel(null,"komiinform.ru", "KIMLong", "KIM"));
		observableRemote.onComplete();
		when(sourceRepository.createSourceStreamRemote()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcePreferencesStream();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(3);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1),
						new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2),
						new SourceBusinessModel(null, "komiinform.ru", "KIMLong", "KIM", true, 3));
		testObserver.assertValueSequence(expected);
	}

	/**
	 * Изменения есть: на сервере появился новый источник.
	 */
	@Test
	public void createCombinedSourcePreferencesStream_NewFromRemote() throws Exception {
		// prepare
		ReplaySubject<SourceDataModel> observableLocal = ReplaySubject.create();
		observableLocal.onNext(new SourceDataModel(new Long(1),"bnkomi.ru", "BNKLong", "BNK", true, 1));
		observableLocal.onNext(new SourceDataModel(new Long(2),"7x7.ru", "7x7Long", "7x7", true, 2));
		observableLocal.onNext(new SourceDataModel(new Long(3),"komiinform.ru", "KIMLong", "KIM", true, 3));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceDataModel> observableRemote = ReplaySubject.create();
		observableRemote.onNext(new SourceDataModel(null,"bnkomi.ru", "BNKLong", "BNK"));
		observableRemote.onNext(new SourceDataModel(null,"7x7.ru", "7x7Long", "7x7"));
		observableRemote.onNext(new SourceDataModel(null,"komiinform.ru", "KIMLong", "KIM"));
		observableRemote.onNext(new SourceDataModel(null,"new_source.ru", "NSLong", "NS"));
		observableRemote.onComplete();
		when(sourceRepository.createSourceStreamRemote()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcePreferencesStream();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(4);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(new Long(1), "bnkomi.ru", "BNKLong", "BNK", true, 1),
						new SourceBusinessModel(new Long(2), "7x7.ru", "7x7Long", "7x7", true, 2),
						new SourceBusinessModel(new Long(3), "komiinform.ru", "KIMLong", "KIM", true, 3),
						new SourceBusinessModel(null, "new_source.ru", "NSLong", "NS", true, -1));
		testObserver.assertValueSequence(expected);

	}

	/**
	 * Изменения есть: на сервере пропал ранее существующий источник (молча возвращаем меньшее
	 * кол-во).
	 */

	@Test
	public void createCombinedSourcePreferencesStream_AbsentOnRemote() throws Exception {
		// prepare
		ReplaySubject<SourceDataModel> observableLocal = ReplaySubject.create();
		observableLocal.onNext(new SourceDataModel(null,"bnkomi.ru", "BNKLong", "BNK", true, 1));
		observableLocal.onNext(new SourceDataModel(null,"7x7.ru", "7x7Long", "7x7", true, 2));
		observableLocal.onNext(new SourceDataModel(null,"komiinform.ru", "KIMLong", "KIM", true, 3));
		observableLocal.onComplete();
		when(sourceRepository.createSourceStreamLocal()).thenReturn(observableLocal);

		ReplaySubject<SourceDataModel> observableRemote = ReplaySubject.create();
		observableRemote.onNext(new SourceDataModel(null,"bnkomi.ru", "BNKLong", "BNK"));
		observableRemote.onNext(new SourceDataModel(null,"7x7.ru", "7x7Long", "7x7"));
		observableRemote.onComplete();
		when(sourceRepository.createSourceStreamRemote()).thenReturn(observableRemote);

		//call
		Observable<SourceBusinessModel> resultStream = testable
				.createCombinedSourcePreferencesStream();

		// subscribe
		TestObserver<SourceBusinessModel> testObserver = TestObserver.create();
		resultStream.subscribe(testObserver);

		// check
		testObserver.assertNoErrors();
		testObserver.assertComplete();
		testObserver.assertValueCount(2);
		List<SourceBusinessModel> expected = Arrays
				.asList(new SourceBusinessModel(null, "bnkomi.ru", "BNKLong", "BNK", true, 1),
						new SourceBusinessModel(null, "7x7.ru", "7x7Long", "7x7", true, 2)
				);
		testObserver.assertValueSequence(expected);

	}

}
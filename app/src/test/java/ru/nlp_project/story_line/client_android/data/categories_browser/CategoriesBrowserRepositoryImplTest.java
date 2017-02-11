package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by fedor on 11.02.17.
 */
public class CategoriesBrowserRepositoryImplTest {

	int counter = 0;

	@Before
	public void setUp() {
		counter = 0;
	}


	private int getNextCounter() {
		return counter++;
	}

	/**
	 * create 2 connectable and check results ....
	 */
	@Test
	public void testConnectable() throws InterruptedException {
		/*
		Observable<Integer> obs = Observable.fromCallable(() -> {
			return Arrays.asList(getNextCounter(), getNextCounter(), getNextCounter());
		}).flatMap(Observable::fromIterable);
		// obs = obs.subscribeOn(Schedulers.io());
		Observable<Integer> connectableObservable = obs.replay(200, TimeUnit.MILLISECONDS).autoConnect();
		connectableObservable.subscribe(i -> {
			System.out.println("1: " + i);
		});
		TimeUnit.SECONDS.sleep(2);

		Observable<Integer> obs2 = connectableObservable;
		obs2.subscribe(i -> {
			System.out.println("22: " + i);
		});
		// connectableObservable.connect();
		*/
	}
}
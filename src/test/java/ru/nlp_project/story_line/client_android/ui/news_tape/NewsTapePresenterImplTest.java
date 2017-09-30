package ru.nlp_project.story_line.client_android.ui.news_tape;

import android.content.Context;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class NewsTapePresenterImplTest {

	private NewsTapePresenterImpl testable;
	private Context context;

	@Before
	public void setUp() {
		context = RuntimeEnvironment.application.getApplicationContext();
		testable = new NewsTapePresenterImpl();
	}


	@Test
	@Ignore
	public void testGetPublicationDatePresentation_SameDate() {
		Date date = new Date();
		String presentation = testable.getPublicationDatePresentation(context, date);
		Assertions.assertThat(presentation).isNotNull().isNotBlank().isEqualTo("11:24PM, 11:24PM");
	}

	@Test
	public void testGetPublicationDatePresentation_Null() {
		Date date = new Date();
		String presentation = testable.getPublicationDatePresentation(context, null);
		Assertions.assertThat(presentation).isNotNull().isEmpty();
	}

}
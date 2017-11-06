package ru.nlp_project.story_line.client_android.ui.changes;

import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel;
import ru.nlp_project.story_line.client_android.business.models.ChangeRecordBusinessModel.ChangeRecordTypes;

public class ChangesPresenterImplTest {

	private ChangesPresenterImpl testable;

	@Before
	public void setUp() {
		testable = new ChangesPresenterImpl();
	}

	@Test
	public void formatPresentation() throws Exception {
		Date date = new Date(1_000_000);
		ChangeRecordBusinessModel rec = new ChangeRecordBusinessModel(null,
				ChangeRecordTypes.NEW_SOURCE, date, false, "komiinform.ru");
		String pres = testable.formatPresentation(rec);
		Assertions.assertThat(pres)
				.containsSequence("Был добавлен новый источник - сайт 'komiinform.ru'\n");
	}

}
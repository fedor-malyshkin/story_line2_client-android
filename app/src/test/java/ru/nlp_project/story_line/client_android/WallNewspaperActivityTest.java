
package ru.nlp_project.story_line.client_android;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by fedor on 03.02.17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class WallNewspaperActivityTest {

	@Test
	public void clickingButton_shouldChangeResultsViewText() throws Exception {
		// WallNewspaperActivity activity = Robolectric.setupActivity(WallNewspaperActivity.class);

		//RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rvNews);
		//assertThat(results.getText().toString()).isEqualTo("Robolectric Rocks!");
	}

}
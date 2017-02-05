package ru.nlp_project.story_line.client_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.nlp_project.story_line.client_android.ui.browser.BrowserActivity;

public class StartupActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setVisible(false);
		Intent intent = new Intent(this, BrowserActivity.class);
		startActivity(intent);
	}


}

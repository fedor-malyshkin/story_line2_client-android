package ru.nlp_project.story_line.client_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StartupActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setVisible(false);
		// initailize somthing
		Intent intent = new Intent(this, WallNewspaperActivity.class);
		startActivity(intent);
	}



}

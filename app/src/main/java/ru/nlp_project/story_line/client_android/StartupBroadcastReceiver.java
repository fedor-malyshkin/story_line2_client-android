package ru.nlp_project.story_line.client_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupBroadcastReceiver extends BroadcastReceiver {
	public StartupBroadcastReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.w(this.getClass().getName(), intent.toString());
		throw new UnsupportedOperationException("Not yet implemented");
	}
}

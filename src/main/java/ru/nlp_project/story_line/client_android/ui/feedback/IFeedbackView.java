package ru.nlp_project.story_line.client_android.ui.feedback;


import android.content.Context;

public interface IFeedbackView {

	Context getContext();

	void clearTextFields();

	void lockTextFields();

	void unlockTextFields();

	void convertSendToUnlockButton();

	void convertUnlockToSendButton();

	void loadAboutInfo(String s);

	void loadAboutInfoError(Throwable throwable);
}

package ru.nlp_project.story_line.client_android.business.sources_browser;

import javax.inject.Inject;

import ru.nlp_project.story_line.client_android.data.news_tape.INewsTapeRepository;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;

/**
 * Created by fedor on 07.02.17.
 */
public class SourcesBrowserInteractorImpl implements  ISourcesBrowserInteractor {

	@Inject
	ISourcesBrowserRepository repository;


	@Inject
	public SourcesBrowserInteractorImpl() {
	}
}

package ru.nlp_project.story_line.client_android.business;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.change_records.IChangeRecordsInteractor;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources.ISourcesInteractor;
import ru.nlp_project.story_line.client_android.data.IStartupRepository;
import ru.nlp_project.story_line.client_android.ui.IStartupPresenter;

public class StartupInteractorImpl implements IStartupInteractor {

	@Inject
	IStartupRepository repository;
	@Inject
	ISourcesInteractor sourcesBrowserInteractor;

	@Inject
	IChangeRecordsInteractor changeRecordsInteractor;


	private IStartupPresenter presenter;

	@Inject
	public StartupInteractorImpl() {
	}


	@Override
	public void initializeInteractor() {

	}

	@Override
	public void bindPresenter(IStartupPresenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public void startupInitialization(Date lastStartupDate) {
		// if not requested previously sources - request it first time (but not use it - not set addition date an other)
		sourcesBrowserInteractor
				.createSourceStreamRemoteCached().toList().blockingGet();

		// use data enrichied with addition info
		List<SourceBusinessModel> sourceBusinessModels = sourcesBrowserInteractor
				.createSourceStreamCached().toList().blockingGet();

		if (lastStartupDate != null) {
			for (SourceBusinessModel source : sourceBusinessModels) {
				if (source.getAdditionDate() != null && source.getAdditionDate().getTime() > lastStartupDate
						.getTime()) {
					changeRecordsInteractor
							.addNewSourceRecord(source.getAdditionDate(), source.getName());
				}

			}
		}
	}
}

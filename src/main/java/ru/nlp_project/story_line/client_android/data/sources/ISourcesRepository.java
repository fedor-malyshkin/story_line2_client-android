package ru.nlp_project.story_line.client_android.data.sources;

import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.IRepository;

public interface ISourcesRepository extends IRepository {

	Observable<SourceBusinessModel> createSourceStreamLocal();

	Observable<SourceBusinessModel> createSourceRemoteStream();

	Observable<SourceBusinessModel> createSourceStreamRemoteCached();

	void upsetSources(List<SourceBusinessModel> list);

	void updateSourceState(String sourceName, boolean checked);

	long getActiveSourcesCount();
}

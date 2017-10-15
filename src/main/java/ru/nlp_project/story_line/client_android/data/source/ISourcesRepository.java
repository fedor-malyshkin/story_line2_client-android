package ru.nlp_project.story_line.client_android.data.source;

import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.IRepository;

public interface ISourcesRepository extends IRepository {

	Observable<SourceBusinessModel> createSourceLocalStream();

	Observable<SourceBusinessModel> createSourceRemoteStream();

	Observable<SourceBusinessModel> createSourceRemoteCachedStream();

	void upsetSources(List<SourceBusinessModel> list);

	void updateSourceState(String sourceName, boolean checked);

	long getActiveSourcesCount();
}

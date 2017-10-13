package ru.nlp_project.story_line.client_android.data.source;

import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

public interface ISourcesRepository {

	Observable<SourceBusinessModel> createSourceLocalStream();

	Observable<SourceBusinessModel> createSourceRemoteStream();

	Observable<SourceBusinessModel> createSourceRemoteCachedStream();

	void upsetSources(List<SourceBusinessModel> list);
}

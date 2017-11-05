package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.changes.ChangesDialog;

@Subcomponent(modules = ChangesModule.class)
@ChangesScope
public abstract class ChangesComponent {

	public abstract void inject(ChangesDialog dialog);
}

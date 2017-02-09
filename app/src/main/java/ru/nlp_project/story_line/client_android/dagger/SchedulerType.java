package ru.nlp_project.story_line.client_android.dagger;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

/**
 * Created by fedor on 10.02.17.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface SchedulerType {

	String background = "background";
	String ui = "ui";

	String value() default "";
}

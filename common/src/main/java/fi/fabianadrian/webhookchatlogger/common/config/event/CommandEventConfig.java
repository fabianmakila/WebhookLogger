package fi.fabianadrian.webhookchatlogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface CommandEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether commands will be logged.")
	boolean enabled();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <audience_display_name>: <command>")
	@ConfComments({
			"The webhook format for executed commands. Available placeholders:",
			"<audience_name>, <audience_display_name>, <command>, <timestamp>, <cancelled>"
	})
	String format();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether cancelled commands will be logged.")
	boolean logCancelled();

	@AnnotationBasedSorter.Order(3)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether console commands will be logged.")
	boolean logConsole();

	@AnnotationBasedSorter.Order(4)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether other entities commands will be logged.")
	boolean logOther();
}

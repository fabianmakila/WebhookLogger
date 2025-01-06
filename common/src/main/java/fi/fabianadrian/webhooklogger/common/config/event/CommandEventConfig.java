package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface CommandEventConfig extends CancellableEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <display_name>: <command>")
	@ConfComments({
			"The webhook format for executed commands. Available placeholders:",
			"<name>, <display_name>, <command>, <timestamp>, <cancelled>"
	})
	String format();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether console commands will be logged.")
	boolean logConsole();

	@AnnotationBasedSorter.Order(3)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether other entities commands will be logged.")
	boolean logOther();
}

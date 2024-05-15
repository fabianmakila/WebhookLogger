package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface DeathEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether player deaths will be logged.")
	boolean enabled();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <message> (<location>)")
	@ConfComments({
			"The webhook format for when a entity dies. Available placeholders:",
			"<audience_name>, <audience_display_name>, <message>, <timestamp>, <cancelled>, <location>"
	})
	String format();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultBoolean(false)
	@ConfComments("Whether cancelled death events will be logged.")
	boolean logCancelled();
}

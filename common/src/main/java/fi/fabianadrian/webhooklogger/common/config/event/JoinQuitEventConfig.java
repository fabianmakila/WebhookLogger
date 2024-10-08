package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface JoinQuitEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("[<timestamp>] <message>")
	@ConfComments({
			"The webhook format for when a player joins or quits. Available placeholders:",
			"<audience_name>, <audience_display_name>, <message>, <timestamp>, <address>"
	})
	String format();
}

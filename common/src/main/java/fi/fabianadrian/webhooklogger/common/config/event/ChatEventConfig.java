package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface ChatEventConfig extends CancellableEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <name>: <message>")
	@ConfComments({
			"The webhook format for chat messages. Available placeholders:",
			"<name>, <display_name>, <message>, <timestamp>, <cancelled>"
	})
	String format();
}

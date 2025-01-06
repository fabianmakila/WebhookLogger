package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface DeathEventConfig extends CancellableEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <message> (<location>)")
	@ConfComments({
			"The webhook format for when a player dies. Available placeholders:",
			"<name>, <display_name>, <message>, <timestamp>, <cancelled>, <location>"
	})
	String format();
}

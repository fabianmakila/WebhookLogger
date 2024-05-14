package fi.fabianadrian.webhookchatlogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface DeathEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether commands will be logged.")
	boolean enabled();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <message>")
	@ConfComments({
			"The webhook format for when a entity dies. Supports MiniMessage and MiniPlaceholders.",
			"Keep in mind that not all webhooks support all features e.g. colors.",
			"Available placeholders:",
			"<audience_name>, <audience_display_name>, <message>, <timestamp>, <cancelled>"
	})
	String format();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether cancelled death events will be logged.")
	boolean cancelled();
}

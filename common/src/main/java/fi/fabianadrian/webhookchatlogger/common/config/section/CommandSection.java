package fi.fabianadrian.webhookchatlogger.common.config.section;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface CommandSection {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether commands will be logged.")
	boolean enabled();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <author_name>: <message>")
	@ConfComments({
			"The webhook format for executed commands. Supports MiniMessage and MiniPlaceholders.",
			"Keep in mind that not all webhooks support all features e.g. colors.",
			"Available placeholders:",
			"<sender_name>, <sender_display_name>, <command>, <timestamp>, <cancelled>"
	})
	String format();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether cancelled commands will be logged.")
	boolean cancelled();

	@AnnotationBasedSorter.Order(3)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether console commands will be logged.")
	boolean console();
}

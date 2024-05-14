package fi.fabianadrian.webhookchatlogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface JoinEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether player joins will be logged.")
	boolean enabled();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <audience_name> joined the game")
	@ConfComments({
			"The webhook format for player joins. Supports MiniMessage and MiniPlaceholders.",
			"Keep in mind that not all webhooks support all features e.g. colors.",
			"Available placeholders:",
			"<audience_name>, <audience_display_name>, <timestamp>, <cancelled>"
	})
	String format();
}
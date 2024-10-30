package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface QuitEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("[<timestamp>] <audience_name> left the game")
	@ConfComments({
			"The webhook format for when a player leaves the server. Available placeholders:",
			"<audience_name>, <audience_display_name>, <message>, <timestamp>"
	})
	String format();
}

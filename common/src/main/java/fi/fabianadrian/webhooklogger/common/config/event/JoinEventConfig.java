package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface JoinEventConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("[<timestamp>] <name> joined the game")
	@ConfComments({
			"The webhook format for when a player joins the server. Available placeholders:",
			"<name>, <display_name>, <message>, <timestamp>, <address>"
	})
	String format();
}

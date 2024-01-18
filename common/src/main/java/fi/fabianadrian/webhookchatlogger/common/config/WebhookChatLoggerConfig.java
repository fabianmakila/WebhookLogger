package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.text.SimpleDateFormat;


public interface WebhookChatLoggerConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("[<timestamp>] <author_name>: <message>")
	@ConfComments({
			"Which format logged messages will use. Supports MiniMessage.",
			"Keep in mind that not all webhooks support all features e.g. colors.",
			"Available placeholders:",
			"<author_name>, <author_display_name>, <message>, <timestamp>"
	})
	String messageFormat();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("HH:mm:ss")
	@ConfComments({
			"Format for the <timestamp> placeholder."
	})
	SimpleDateFormat timestampFormat();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether cancelled chat messages should be sent to the webhook.")
	boolean logCancelledMessages();

	@AnnotationBasedSorter.Order(3)
	@SubSection
	@ConfKey("discord")
	@ConfComments("Configuration options for the Discord client.")
	DiscordConfigSection discordSection();
}

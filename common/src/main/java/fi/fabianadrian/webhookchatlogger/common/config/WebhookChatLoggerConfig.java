package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import fi.fabianadrian.webhookchatlogger.common.config.section.PlaceholderConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface WebhookChatLoggerConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("<cancelled>[<timestamp>] <author_name>: <message>")
	@ConfComments({
			"Which format logged messages will use. Supports MiniMessage and MiniPlaceholders.",
			"Keep in mind that not all webhooks support all features e.g. colors.",
			"Available placeholders:",
			"<author_name>, <author_display_name>, <message>, <timestamp>, <cancelled>"
	})
	String messageFormat();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether cancelled chat messages should be sent to the webhook.")
	boolean logCancelledMessages();

	@AnnotationBasedSorter.Order(2)
	@ConfComments("Configuration options for various placeholders.")
	@SubSection
	PlaceholderConfigSection placeholders();

	@AnnotationBasedSorter.Order(3)
	@SubSection
	@ConfKey("discord")
	@ConfComments("Configuration options for the Discord client.")
	DiscordConfigSection discordSection();
}

package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;


public interface WebhookChatLoggerConfig {
	@ConfDefault.DefaultString("[<timestamp>] <author_name>: <message>")
	@ConfComments({
			"Which format logged messages will use. Supports MiniMessage.",
			"Keep in mind that not all webhooks support all features e.g. colors.",
			"Available placeholders:",
			"<author_name>, <author_display_name>, <message>, <timestamp>"
	})
	String messageFormat();

	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether cancelled chat messages should be sent to the webhook.")
	boolean logCancelledMessages();

	@SubSection
	@ConfKey("discord")
	@ConfComments("Configuration options for the Discord client.")
	DiscordConfigSection discordSection();
}

package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;


public interface WebhookChatLoggerConfig {
    @ConfDefault.DefaultBoolean(true)
    @ConfComments("Whether cancelled chat messages should be sent to the webhook.")
    boolean logCancelledMessages();

    @SubSection
    @ConfKey("discord")
    @ConfComments("Configuration options for the Discord client.")
    DiscordConfigSection discordSection();
}

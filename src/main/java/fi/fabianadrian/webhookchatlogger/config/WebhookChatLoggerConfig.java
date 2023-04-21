package fi.fabianadrian.webhookchatlogger.config;

import fi.fabianadrian.webhookchatlogger.client.ClientType;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.SubSection;


public interface WebhookChatLoggerConfig {
    @ConfDefault.DefaultString("")
    String url();

    @ConfDefault.DefaultString("DISCORD")
    ClientType webhookType();

    @ConfDefault.DefaultBoolean(false)
    boolean logCancelledMessages();

    @SubSection
    DiscordClientConfig discordClientConfig();

    interface DiscordClientConfig {
        @ConfDefault.DefaultString("MESSAGE")
        MessageStyle messageStyle();

        @ConfDefault.DefaultString("**%1$s > ** %2$s")
        String messageFormat();

        enum MessageStyle {
            EMBED_PRETTY, EMBED_COMPACT, MESSAGE
        }
    }
}

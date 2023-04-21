package fi.fabianadrian.webhookchatlogger.config;

import fi.fabianadrian.webhookchatlogger.client.ClientType;
import space.arim.dazzleconf.annote.ConfDefault;


public interface WebhookChatLoggerConfig {
    String url();

    @ConfDefault.DefaultString("DISCORD")
    ClientType webhookType();

    boolean logCancelledMessages();

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

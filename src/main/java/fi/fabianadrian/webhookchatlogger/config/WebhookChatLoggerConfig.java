package fi.fabianadrian.webhookchatlogger.config;

import fi.fabianadrian.webhookchatlogger.client.ClientType;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;


public interface WebhookChatLoggerConfig {
    @ConfDefault.DefaultString("")
    @ConfComments("Webhook URL.")
    String url();

    @ConfDefault.DefaultString("DISCORD")
    @ConfComments({
        "Supported webhook clients:",
        "DISCORD"
    })
    ClientType client();

    @ConfDefault.DefaultBoolean(true)
    @ConfComments("Whether cancelled chat messages should be sent to the webhook.")
    boolean logCancelledMessages();

    @SubSection
    @ConfKey("discord")
    @ConfComments("Configuration options for the Discord client.")
    DiscordClientConfig discordClientConfig();

    interface DiscordClientConfig {
        @ConfDefault.DefaultString("MESSAGE")
        @ConfComments({
            "Possible values:",
            "EMBED_COMPACT, EMBED_PRETTY, MESSAGE"
        })
        MessageStyle messageStyle();

        @ConfDefault.DefaultString("**%1$s > ** %2$s")
        @ConfComments({
            "Placeholders:",
            "%1$s - Player name",
            "%2$s - Message"
        })
        String messageFormat();

        enum MessageStyle {
            EMBED_COMPACT, EMBED_PRETTY, MESSAGE
        }
    }
}

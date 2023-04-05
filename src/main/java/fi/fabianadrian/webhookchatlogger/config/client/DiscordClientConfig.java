package fi.fabianadrian.webhookchatlogger.config.client;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class DiscordClientConfig {
    private MessageStyle messageStyle = MessageStyle.MESSAGE;

    private String chatFormat = "**%1$s > ** %2$s";

    public MessageStyle messageStyle() {
        return this.messageStyle;
    }

    public String chatFormat() {
        return this.chatFormat;
    }

    public enum MessageStyle {
        EMBED_PRETTY, EMBED_COMPACT, MESSAGE
    }
}

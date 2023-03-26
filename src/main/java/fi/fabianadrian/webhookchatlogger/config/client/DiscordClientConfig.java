package fi.fabianadrian.webhookchatlogger.config.client;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class DiscordClientConfig {
    private MessageStyle messageStyle = MessageStyle.MESSAGE;

    public MessageStyle messageStyle() {
        return this.messageStyle;
    }
    public enum MessageStyle {
        EMBED_PRETTY, EMBED_COMPACT, MESSAGE
    }
}

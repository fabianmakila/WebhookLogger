package fi.fabianadrian.webhookchatlogger.config.client;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class DiscordClientConfig {
    private EmbedStyle embedStyle = EmbedStyle.PRETTY;

    public EmbedStyle embedStyle() {
        return this.embedStyle;
    }
    public enum EmbedStyle {
        PRETTY, COMPACT
    }
}

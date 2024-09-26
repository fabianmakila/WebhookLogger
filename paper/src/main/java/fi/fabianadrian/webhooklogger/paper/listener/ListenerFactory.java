package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import org.bukkit.event.Listener;

public final class ListenerFactory {
	private final WebhookLoggerPaper plugin;

	public ListenerFactory(WebhookLoggerPaper plugin) {
		this.plugin = plugin;
	}

	public void register(EventType type) {
		Listener listener = create(type);
		this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
	}

	private Listener create(EventType type) {
		switch (type) {
			case CHAT -> {
				return new ChatListener(this.plugin);
			}
			case COMMAND -> {
				return new CommandListener(this.plugin);
			}
			case DEATH -> {
				return new DeathListener(this.plugin);
			}
			case JOINQUIT -> {
				return new JoinQuitListener(this.plugin);
			}
		}
		throw new IllegalStateException("Unknown EventType");
	}
}

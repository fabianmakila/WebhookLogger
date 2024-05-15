package fi.fabianadrian.webhooklogger.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.sponge.listener.ChatListener;
import fi.fabianadrian.webhooklogger.sponge.listener.CommandListener;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;
import java.util.List;

@Plugin("webhookchatlogger")
public final class WebhookLoggerSponge {
	private WebhookLogger webhookLogger;
	private final PluginContainer container;
	private final Path configDir;

	@Inject
	public WebhookLoggerSponge(PluginContainer container, @ConfigDir(sharedRoot = false) Path configDir) {
		this.container = container;
		this.configDir = configDir;
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		this.webhookLogger = new WebhookLogger(LoggerFactory.getLogger("webhookchatlogger"), this.configDir);
		registerListeners();
	}

	@Listener
	public void onServerStopping(final StoppingEngineEvent<Server> event) {
		this.webhookLogger.shutdown();
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	private void registerListeners() {
		EventManager manager = Sponge.eventManager();
		List.of(
				new ChatListener(this),
				new CommandListener(this)
		).forEach(listener -> manager.registerListeners(this.container, listener));
	}
}

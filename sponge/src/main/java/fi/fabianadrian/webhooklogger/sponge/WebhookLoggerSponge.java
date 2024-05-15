package fi.fabianadrian.webhooklogger.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
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
public class WebhookLoggerSponge {
	private WebhookChatLogger wcl;
	private final PluginContainer container;
	private final Path configDir;

	@Inject
	public WebhookLoggerSponge(PluginContainer container, @ConfigDir(sharedRoot = false) Path configDir) {
		this.container = container;
		this.configDir = configDir;
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		this.wcl = new WebhookChatLogger(LoggerFactory.getLogger("webhookchatlogger"), this.configDir);
		registerListeners();
	}

	@Listener
	public void onServerStopping(final StoppingEngineEvent<Server> event) {
		this.wcl.shutdown();
	}

	public WebhookChatLogger wcl() {
		return this.wcl;
	}

	private void registerListeners() {
		EventManager manager = Sponge.eventManager();
		List.of(
				new ChatListener(this),
				new CommandListener(this)
		).forEach(listener -> manager.registerListeners(this.container, listener));
	}
}

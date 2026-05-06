package fi.fabianadrian.webhooklogger.common;

import fi.fabianadrian.webhooklogger.common.config.ConfigManager;
import fi.fabianadrian.webhooklogger.common.config.EventsConfig;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.common.locale.TranslationManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.common.webhook.WebhookManager;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class WebhookLogger {
	public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
	private final Platform platform;
	private final ConfigManager configManager;
	private final WebhookManager webhookManager;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	public WebhookLogger(Platform platform) {
		this.platform = platform;

		new TranslationManager();
		this.configManager = new ConfigManager(platform.configPath());
		this.webhookManager = new WebhookManager(this);
	}

	public void startup() throws ConfigurateException {
		this.configManager.reload();
		this.platform.listenerManager().registerListeners();
		dependencyManager().register();
		this.webhookManager.reload();
	}

	public void reload() throws ConfigurateException {
		this.configManager.reload();

		this.platform.listenerManager().clearRegisteredWebhooks();
		this.webhookManager.reload();
	}

	public void shutdown() {
		this.scheduler.shutdown();
		this.platform.listenerManager().clearRegisteredWebhooks();
	}

	public MainConfig mainConfig() {
		return this.configManager.mainConfig();
	}

	public EventsConfig eventsConfig() {
		return this.configManager.eventsConfig();
	}

	public Logger logger() {
		return this.platform.logger();
	}

	public ScheduledExecutorService scheduler() {
		return this.scheduler;
	}

	public DependencyManager dependencyManager() {
		return this.platform.dependencyManager();
	}

	public ListenerManager listenerManager() {
		return this.platform.listenerManager();
	}

	public ComponentFlattener componentFlattener() {
		return this.platform.componentFlattener();
	}
}

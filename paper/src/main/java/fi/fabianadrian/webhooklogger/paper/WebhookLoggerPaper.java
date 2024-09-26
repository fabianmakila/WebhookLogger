package fi.fabianadrian.webhooklogger.paper;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.paper.listener.PaperListenerManager;
import net.kyori.adventure.audience.Audience;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class WebhookLoggerPaper extends JavaPlugin implements Platform {
	private WebhookLogger webhookLogger;
	private LegacyPaperCommandManager<Audience> commandManager;
	private PaperListenerManager listenerRegistry;

	@Override
	public void onEnable() {
		createCommandManager();
		this.webhookLogger = new WebhookLogger(this);
		this.listenerRegistry = new PaperListenerManager(this);
		this.webhookLogger.reload();

		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			this.webhookLogger.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}

		// bStats
		new Metrics(this, 18436);
	}

	@Override
	public void onDisable() {
		this.webhookLogger.shutdown();
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	@Override
	public Logger logger() {
		return getSLF4JLogger();
	}

	@Override
	public Path configPath() {
		return getDataFolder().toPath();
	}

	@Override
	public CommandManager<Audience> commandManager() {
		return this.commandManager;
	}

	@Override
	public ListenerManager listenerManager() {
		return this.listenerRegistry;
	}

	private void createCommandManager() {
		SenderMapper<CommandSender, Audience> mapper = SenderMapper.create(
				commandSender -> commandSender,
				audience -> (CommandSender) audience
		);
		this.commandManager = new LegacyPaperCommandManager<>(this, ExecutionCoordinator.simpleCoordinator(), mapper);
	}
}

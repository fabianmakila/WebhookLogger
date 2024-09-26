package fi.fabianadrian.webhooklogger.paper;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.command.Commander;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.paper.command.PaperCommander;
import fi.fabianadrian.webhooklogger.paper.listener.*;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class WebhookLoggerPaper extends JavaPlugin implements Platform {
	private WebhookLogger webhookLogger;
	private CommandManager<Commander> commandManager;

	@Override
	public void onEnable() {
		createCommandManager();
		this.webhookLogger = new WebhookLogger(this);

		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			this.webhookLogger.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}

		registerListeners();

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
	public void registerListeners() {
		// Unregister any existing listeners
		HandlerList.unregisterAll(this);

		ListenerFactory factory = new ListenerFactory(this);
		this.webhookLogger.clientManager().registry().registeredEventTypes().forEach(factory::register);
	}

	@Override
	public Logger logger() {
		return getSLF4JLogger();
	}

	@Override
	public Path configPath() {
		return getDataPath();
	}

	@Override
	public CommandManager<Commander> commandManager() {
		return this.commandManager;
	}

	private void createCommandManager() {
		SenderMapper<CommandSourceStack, Commander> senderMapper = SenderMapper.create(
				PaperCommander::new,
				commander -> ((PaperCommander) commander).stack()
		);

		this.commandManager = PaperCommandManager.builder(senderMapper)
				.executionCoordinator(ExecutionCoordinator.simpleCoordinator())
				.buildOnEnable(this);
	}
}

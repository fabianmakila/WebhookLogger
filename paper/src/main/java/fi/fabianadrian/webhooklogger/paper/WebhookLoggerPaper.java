package fi.fabianadrian.webhooklogger.paper;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.paper.listener.PaperListenerManager;
import fi.fabianadrian.webhooklogger.paper.platform.PaperCommandSender;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;

public final class WebhookLoggerPaper extends JavaPlugin implements Platform {
	private WebhookLogger webhookLogger;
	private PaperCommandManager<Audience> commandManager;
	private PaperListenerManager listenerManager;

	@Override
	public void onEnable() {
		createCommandManager();
		this.listenerManager = new PaperListenerManager(this);

		this.webhookLogger = new WebhookLogger(this);
		try {
			this.webhookLogger.startup();
		} catch (ConfigurateException e) {
			getSLF4JLogger().error("Couldn't load configuration", e);
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

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
		return this.listenerManager;
	}

	@Override
	public ComponentFlattener componentFlattener() {
		return PaperComponents.flattener();
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	private void createCommandManager() {
		SenderMapper<CommandSourceStack, Audience> mapper = SenderMapper.create(
				PaperCommandSender::new,
				audience -> ((PaperCommandSender) audience).stack()
		);

		this.commandManager = PaperCommandManager.builder(mapper)
				.executionCoordinator(ExecutionCoordinator.simpleCoordinator())
				.buildOnEnable(this);
	}
}

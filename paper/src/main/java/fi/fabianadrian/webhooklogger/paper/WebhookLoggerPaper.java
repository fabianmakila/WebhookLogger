package fi.fabianadrian.webhooklogger.paper;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.paper.command.RootCommandExecutor;
import fi.fabianadrian.webhooklogger.paper.listener.ChatListener;
import fi.fabianadrian.webhooklogger.paper.listener.CommandListener;
import fi.fabianadrian.webhooklogger.paper.listener.DeathListener;
import fi.fabianadrian.webhooklogger.paper.listener.JoinQuitListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class WebhookLoggerPaper extends JavaPlugin {
	private WebhookLogger webhookLogger;

	@Override
	public void onEnable() {
		this.webhookLogger = new WebhookLogger(getSLF4JLogger(), getDataFolder().toPath());

		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			this.webhookLogger.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}

		registerCommands();
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

	private void registerCommands() {
		PluginCommand rootCommand = getCommand("webhooklogger");
		if (rootCommand == null) {
			return;
		}

		RootCommandExecutor rootCommandExecutor = new RootCommandExecutor(this);
		rootCommand.setExecutor(rootCommandExecutor);
		rootCommand.setTabCompleter(rootCommandExecutor);
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		Stream.of(
				new ChatListener(this),
				new CommandListener(this),
				new DeathListener(this),
				new JoinQuitListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}

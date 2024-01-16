package fi.fabianadrian.webhookchatlogger.paper;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.dependency.Dependency;
import fi.fabianadrian.webhookchatlogger.paper.command.RootCommandExecutor;
import fi.fabianadrian.webhookchatlogger.paper.listener.ChatListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class WebhookChatLoggerPaper extends JavaPlugin {
	private WebhookChatLogger wcl;

	@Override
	public void onEnable() {
		this.wcl = new WebhookChatLogger(getSLF4JLogger(), getDataFolder().toPath());

		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			this.wcl.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}

		registerCommands();
		registerListeners();

		// bStats
		new Metrics(this, 18436);
	}

	@Override
	public void onDisable() {
		this.wcl.shutdown();
	}

	public WebhookChatLogger wcl() {
		return this.wcl;
	}

	private void registerCommands() {
		PluginCommand rootCommand = getCommand("webhookchatlogger");
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
				new ChatListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}

package fi.fabianadrian.webhooklogger.sponge;

import fi.fabianadrian.webhooklogger.common.CommandManager;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.plugin.PluginContainer;

public final class SpongeCommandManager extends CommandManager {
	private final WebhookLogger webhookLogger;
	private final Command.Parameterized command;
	private final PluginContainer container;

	public SpongeCommandManager(WebhookLogger webhookLogger, PluginContainer container) {
		this.webhookLogger = webhookLogger;
		this.container = container;

		Command.Parameterized reloadCommand = Command.builder()
				.permission(PERMISSION_RELOAD)
				.shortDescription(Component.text("Reloads the plugin."))
				.executor(this::executeReload)
				.build();

		this.command = Command.builder()
				.addChild(reloadCommand, "reload")
				.build();
	}

	@Listener
	public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
		event.register(this.container, this.command, "webhooklogger");
	}

	private CommandResult executeReload(CommandContext context) {
		try {
			this.webhookLogger.reload();
			context.sendMessage(COMPONENT_RELOAD_SUCCESS);
		} catch (ConfigurateException e) {
			context.sendMessage(COMPONENT_RELOAD_FAILURE);
			this.webhookLogger.logger().error("Couldn't load configuration", e);
		}
		return CommandResult.success();
	}
}

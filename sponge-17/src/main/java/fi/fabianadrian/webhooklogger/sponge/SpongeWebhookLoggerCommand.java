package fi.fabianadrian.webhooklogger.sponge;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.WebhookLoggerCommand;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.configurate.ConfigurateException;

public final class SpongeWebhookLoggerCommand extends WebhookLoggerCommand {
	private final WebhookLogger webhookLogger;

	public SpongeWebhookLoggerCommand(WebhookLoggerSponge plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	public Command.Parameterized command() {
		var reloadCommand = Command.builder()
				.permission(PERMISSION_RELOAD)
				.shortDescription(Component.text("Reloads the plugin."))
				.executor(this::executeReload)
				.build();

		return Command.builder()
				.addChild(reloadCommand, "reload")
				.build();
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

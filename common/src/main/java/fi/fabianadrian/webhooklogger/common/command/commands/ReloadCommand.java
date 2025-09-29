package fi.fabianadrian.webhooklogger.common.command.commands;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.command.BaseCommand;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.context.CommandContext;
import org.spongepowered.configurate.ConfigurateException;

import static net.kyori.adventure.text.Component.translatable;

public final class ReloadCommand extends BaseCommand {
	private static final Component COMPONENT_SUCCESS = translatable("webhooklogger.command.reload.success");
	private static final Component COMPONENT_FAILURE = translatable("webhooklogger.command.reload.failure");

	public ReloadCommand(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public void register() {
		super.manager.command(rootBuilder()
				.literal("reload")
				.permission("webhooklogger.command.reload")
				.handler(this::executeReload)
		);
	}

	private void executeReload(CommandContext<Audience> context) {
		try {
			super.webhookLogger.reload();
			context.sender().sendMessage(COMPONENT_SUCCESS);
		} catch (ConfigurateException e) {
			context.sender().sendMessage(COMPONENT_FAILURE);
			super.webhookLogger.logger().error("Couldn't load configuration", e);
		}
	}
}

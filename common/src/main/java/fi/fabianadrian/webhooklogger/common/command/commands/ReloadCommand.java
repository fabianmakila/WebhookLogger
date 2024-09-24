package fi.fabianadrian.webhooklogger.common.command.commands;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.command.Commander;
import fi.fabianadrian.webhooklogger.common.command.WebhookLoggerCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.context.CommandContext;

import static net.kyori.adventure.text.Component.translatable;

public final class ReloadCommand extends WebhookLoggerCommand {
	private static final Component COMPONENT_SUCCESS = translatable()
			.key("webhooklogger.command.reload.success")
			.color(NamedTextColor.GREEN)
			.build();
	private static final Component COMPONENT_FAILURE = translatable()
			.key("webhooklogger.command.reload.failure")
			.color(NamedTextColor.RED)
			.build();

	public ReloadCommand(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public void register() {
		this.manager.command(rootBuilder()
				.literal("reload")
				.permission("webhooklogger.command.reload")
				.handler(this::executeReload)
		);
	}

	private void executeReload(CommandContext<Commander> context) {
		if (this.webhookLogger.reload()) {
			context.sender().sendMessage(COMPONENT_SUCCESS);
		} else {
			context.sender().sendMessage(COMPONENT_FAILURE);
		}
	}
}

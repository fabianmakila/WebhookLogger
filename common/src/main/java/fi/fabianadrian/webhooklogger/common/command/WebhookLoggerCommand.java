package fi.fabianadrian.webhooklogger.common.command;

import net.kyori.adventure.text.Component;

public abstract class WebhookLoggerCommand {
	protected static final String PERMISSION_RELOAD = "webhooklogger.command.reload";
	protected static final Component COMPONENT_RELOAD_FAILURE = Component.translatable("webhooklogger.command.reload.failure");
	protected static final Component COMPONENT_RELOAD_SUCCESS = Component.translatable("webhooklogger.command.reload.success");
}

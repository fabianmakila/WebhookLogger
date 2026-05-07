package fi.fabianadrian.webhooklogger.common;

import net.kyori.adventure.text.Component;

public abstract class CommandManager {
	protected static final Component COMPONENT_RELOAD_FAILURE = Component.translatable("webhooklogger.command.reload.failure");
	protected static final Component COMPONENT_RELOAD_SUCCESS = Component.translatable("webhooklogger.command.reload.success");
	protected static final String PERMISSION_RELOAD = "webhooklogger.command.reload";
}

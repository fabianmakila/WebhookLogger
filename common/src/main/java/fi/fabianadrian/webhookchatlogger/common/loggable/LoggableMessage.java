package fi.fabianadrian.webhookchatlogger.common.loggable;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class LoggableMessage extends Loggable {
	private final Component message;

	public LoggableMessage(Audience sender, Component message, boolean cancelled) {
		super(sender, cancelled);
		this.message = message;
	}

	public Component message() {
		return message;
	}
}

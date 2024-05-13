package fi.fabianadrian.webhookchatlogger.common.loggable;

import net.kyori.adventure.audience.Audience;

public final class LoggableCommand extends Loggable {
	private final String command;

	public LoggableCommand(Audience sender, String command, boolean cancelled) {
		super(sender, cancelled);
		this.command = command;
	}

	public String command() {
		return command;
	}
}

package fi.fabianadrian.webhookchatlogger.common.loggable;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

public abstract class Loggable {
	private final Audience sender;
	private final boolean cancelled;

	public Loggable(Audience sender, boolean cancelled) {
		this.sender = sender;
		this.cancelled = cancelled;
	}

	public Audience sender() {
		return this.sender;
	}

	public String senderName() {
		return sender.getOrDefault(Identity.NAME, "unknown");
	}

	public Component senderDisplayName() {
		return sender.getOrDefault(Identity.DISPLAY_NAME, Component.text(senderName()));
	}

	public boolean cancelled() {
		return this.cancelled;
	}
}

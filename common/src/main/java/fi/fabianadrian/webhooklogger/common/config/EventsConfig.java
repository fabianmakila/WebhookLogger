package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.config.event.*;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EventsConfig {
	private ChatEventConfig chat = new ChatEventConfig();
	private CommandEventConfig command = new CommandEventConfig();
	private DeathEventConfig death = new DeathEventConfig();
	private JoinEventConfig join = new JoinEventConfig();
	private QuitEventConfig quit = new QuitEventConfig();

	public ChatEventConfig chat() {
		return chat;
	}

	public CommandEventConfig command() {
		return command;
	}

	public DeathEventConfig death() {
		return death;
	}

	public JoinEventConfig join() {
		return join;
	}

	public QuitEventConfig quit() {
		return quit;
	}
}

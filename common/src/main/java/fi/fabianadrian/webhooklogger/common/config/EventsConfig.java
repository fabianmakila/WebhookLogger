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
	private CarbonEventConfig carbon = new CarbonEventConfig();

	public ChatEventConfig chat() {
		return this.chat;
	}

	public CommandEventConfig command() {
		return this.command;
	}

	public DeathEventConfig death() {
		return this.death;
	}

	public JoinEventConfig join() {
		return this.join;
	}

	public QuitEventConfig quit() {
		return this.quit;
	}

	public CarbonEventConfig carbon() {
		return this.carbon;
	}
}

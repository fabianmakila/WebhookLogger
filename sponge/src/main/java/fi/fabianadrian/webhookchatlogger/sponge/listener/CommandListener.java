package fi.fabianadrian.webhookchatlogger.sponge.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.event.CommandEventComponentBuilder;
import fi.fabianadrian.webhookchatlogger.sponge.WebhookChatLoggerSponge;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;

import java.util.Optional;

public class CommandListener {
	private final WebhookChatLogger wcl;

	public CommandListener(WebhookChatLoggerSponge plugin) {
		this.wcl = plugin.wcl();
	}

	@Listener
	public void onCommand(ExecuteCommandEvent event) {
		Optional<Player> playerOptional = event.cause().first(Player.class);
		if (playerOptional.isEmpty()) {
			return;
		}

		Component component = new CommandEventComponentBuilder(this.wcl)
				.audience(playerOptional.get())
				.command(event.command())
				.build();
		this.wcl.clientManager().send(component);
	}
}

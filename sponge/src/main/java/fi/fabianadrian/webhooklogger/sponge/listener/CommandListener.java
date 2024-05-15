package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
import fi.fabianadrian.webhooklogger.common.event.CommandEventComponentBuilder;
import fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;

import java.util.Optional;

public class CommandListener {
	private final WebhookChatLogger wcl;

	public CommandListener(WebhookLoggerSponge plugin) {
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

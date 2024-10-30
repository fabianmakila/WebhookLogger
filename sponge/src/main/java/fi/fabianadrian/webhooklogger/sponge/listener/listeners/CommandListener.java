package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

//TODO Log console and other commands
public final class CommandListener extends AbstractListener {
	public CommandListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onCommand(ExecuteCommandEvent.Pre event) {
		CommandEventConfig config = webhookLogger.eventsConfig().command();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Optional<Player> playerOptional = event.cause().first(Player.class);
		if (playerOptional.isEmpty()) {
			return;
		}

		//TODO Is this fine?
		SpongePlayer player = new SpongePlayer((ServerPlayer) playerOptional.get());

		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.cancelled(event.isCancelled()),
				placeholderFactory.command(event.command())
		);

		queue(config.format(), builder);
	}
}

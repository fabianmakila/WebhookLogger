package fi.fabianadrian.webhooklogger.sponge.platform;

import fi.fabianadrian.webhooklogger.common.platform.Location;
import fi.fabianadrian.webhooklogger.common.platform.PlatformPlayer;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import java.net.InetSocketAddress;

public final class SpongePlayer implements PlatformPlayer {
	private final ServerPlayer player;

	public SpongePlayer(ServerPlayer player) {
		this.player = player;
	}

	@Override
	public InetSocketAddress address() {
		return this.player.connection().address();
	}

	@Override
	public Location location() {
		return new Location(
				this.player.location().blockX(),
				this.player.location().blockY(),
				this.player.location().blockZ()
		);
	}

	@Override
	public @NotNull Audience audience() {
		return this.player;
	}
}

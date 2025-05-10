package fi.fabianadrian.webhooklogger.paper.platform;

import fi.fabianadrian.webhooklogger.common.platform.Location;
import fi.fabianadrian.webhooklogger.common.platform.PlatformPlayer;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

public final class PaperPlayer implements PlatformPlayer {
	private final Player player;

	public PaperPlayer(org.bukkit.entity.Player player) {
		this.player = player;
	}

	@Override
	public InetSocketAddress address() {
		return this.player.getAddress();
	}

	@Override
	public Location location() {
		org.bukkit.Location location = this.player.getLocation();
		return new Location(
				location.getBlockX(),
				location.getBlockY(),
				location.getBlockZ()
		);
	}

	@Override
	public @NotNull Audience audience() {
		return this.player;
	}
}

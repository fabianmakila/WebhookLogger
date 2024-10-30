package fi.fabianadrian.webhooklogger.paper.platform;

import fi.fabianadrian.webhooklogger.common.platform.Location;
import fi.fabianadrian.webhooklogger.common.platform.Player;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

public final class PaperPlayer implements Player {
	private final org.bukkit.entity.Player player;

	public PaperPlayer(org.bukkit.entity.Player player) {
		this.player = player;
	}

	@Override
	public InetSocketAddress address() {
		return player.getAddress();
	}

	@Override
	public Location location() {
		org.bukkit.Location location = player.getLocation();
		return new Location(
				location.getBlockX(),
				location.getBlockY(),
				location.getBlockX()
		);
	}

	@Override
	public @NotNull Audience audience() {
		return player;
	}
}

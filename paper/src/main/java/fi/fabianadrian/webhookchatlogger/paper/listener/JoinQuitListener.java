package fi.fabianadrian.webhookchatlogger.paper.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.event.JoinQuitEventComponentBuilder;
import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPaper;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitListener implements Listener {
	private final WebhookChatLogger wcl;

	public JoinQuitListener(WebhookChatLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Location loc = event.getPlayer().getLocation();
		Component component = new JoinQuitEventComponentBuilder(this.wcl)
				.audience(event.getPlayer())
				.message(event.joinMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(String.valueOf(event.getPlayer().getAddress()))
				.build();
		this.wcl.clientManager().send(component);
	}

	@EventHandler
	public void onJoin(PlayerQuitEvent event) {
		Location loc = event.getPlayer().getLocation();
		Component component = new JoinQuitEventComponentBuilder(this.wcl)
				.audience(event.getPlayer())
				.message(event.quitMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(String.valueOf(event.getPlayer().getAddress()))
				.build();
		this.wcl.clientManager().send(component);
	}
}

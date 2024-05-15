package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinQuitEventConfig;
import fi.fabianadrian.webhooklogger.common.event.JoinQuitEventComponentBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitListener implements Listener {
	private final WebhookChatLogger wcl;

	public JoinQuitListener(WebhookLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		JoinQuitEventConfig config = this.wcl.eventsConfig().joinQuit();
		if (!config.enabled()) {
			return;
		}

		Component joinMessage = event.joinMessage();
		String joinMessageAsString = joinMessage != null ? PlainTextComponentSerializer.plainText().serialize(joinMessage) : "";
		Location loc = event.getPlayer().getLocation();

		Component component = new JoinQuitEventComponentBuilder(this.wcl)
				.audience(event.getPlayer())
				.message(joinMessageAsString)
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(String.valueOf(event.getPlayer().getAddress()))
				.build();
		this.wcl.clientManager().send(component);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		JoinQuitEventConfig config = this.wcl.eventsConfig().joinQuit();
		if (!config.enabled()) {
			return;
		}

		Component quitMessage = event.quitMessage();
		String quitMessageAsString = quitMessage != null ? PlainTextComponentSerializer.plainText().serialize(quitMessage) : "";
		Location loc = event.getPlayer().getLocation();
		Component component = new JoinQuitEventComponentBuilder(this.wcl)
				.audience(event.getPlayer())
				.message(quitMessageAsString)
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(String.valueOf(event.getPlayer().getAddress()))
				.build();
		this.wcl.clientManager().send(component);
	}
}

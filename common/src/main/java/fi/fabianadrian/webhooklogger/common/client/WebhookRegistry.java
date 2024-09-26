package fi.fabianadrian.webhooklogger.common.client;

import fi.fabianadrian.webhooklogger.common.event.EventType;

import java.util.*;

public final class WebhookRegistry {
	private final Map<EventType, List<DiscordClient>> eventClientMap = new EnumMap<>(EventType.class);
	private final List<DiscordClient> clients = new ArrayList<>();

	public void register(DiscordClient client, List<EventType> events) {
		this.clients.add(client);
		events.forEach(event -> {
			List<DiscordClient> clients = eventClientMap.getOrDefault(event, new ArrayList<>());
			clients.add(client);
			this.eventClientMap.put(event, clients);
		});
	}

	public void clear() {
		this.clients.clear();
		this.eventClientMap.clear();
	}

	public List<DiscordClient> forEventType(EventType type) {
		return this.eventClientMap.get(type);
	}

	public List<DiscordClient> webhooks() {
		return List.copyOf(this.clients);
	}

	public Set<EventType> registeredEventTypes() {
		return Set.copyOf(this.eventClientMap.keySet());
	}
}

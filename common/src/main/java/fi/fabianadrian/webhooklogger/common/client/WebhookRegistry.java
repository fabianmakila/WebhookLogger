package fi.fabianadrian.webhooklogger.common.client;

import fi.fabianadrian.webhooklogger.common.event.EventType;

import java.util.*;

public final class WebhookRegistry {
	private final Map<EventType, List<DiscordClient>> eventClientMap = new EnumMap<>(EventType.class);
	private final List<DiscordClient> clients = new ArrayList<>();

	public void register(final DiscordClient client, final List<EventType> events) {
		this.clients.add(client);
		events.forEach(event -> this.eventClientMap.computeIfAbsent(event, key -> new ArrayList<>()).add(client));
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

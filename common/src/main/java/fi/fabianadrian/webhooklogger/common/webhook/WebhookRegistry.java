package fi.fabianadrian.webhooklogger.common.webhook;

import fi.fabianadrian.webhooklogger.common.event.EventType;

import java.util.*;

public final class WebhookRegistry {
	private final Map<EventType, List<WebhookClient>> eventClientMap = new EnumMap<>(EventType.class);
	private final Set<WebhookClient> clients = new HashSet<>();

	public void register(final WebhookClient client, final List<EventType> events) {
		this.clients.add(client);
		events.forEach(event -> this.eventClientMap.computeIfAbsent(event, key -> new ArrayList<>()).add(client));
	}

	public void clear() {
		this.clients.clear();
		this.eventClientMap.clear();
	}

	public List<WebhookClient> forEventType(EventType type) {
		return this.eventClientMap.get(type);
	}

	public Set<WebhookClient> webhooks() {
		return Set.copyOf(this.clients);
	}

	public Set<EventType> registeredEventTypes() {
		return Set.copyOf(this.eventClientMap.keySet());
	}
}

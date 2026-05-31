package fi.fabianadrian.webhooklogger.api;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public interface WebhookManager {
	void post(Key key, Component component);
}

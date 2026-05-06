package fi.fabianadrian.webhooklogger.common;

import fi.fabianadrian.webhooklogger.common.listener.CarbonListener;
import net.draycia.carbon.api.CarbonChat;
import net.draycia.carbon.api.CarbonChatProvider;
import org.slf4j.Logger;

public abstract class DependencyManager {
	private final Logger logger;
	private final WebhookLogger webhookLogger;
	private boolean miniPlaceholders = false;

	public DependencyManager(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.logger = webhookLogger.logger();
	}

	public abstract void register();

	public boolean isMiniPlaceholders() {
		return this.miniPlaceholders;
	}

	protected void registerMiniPlaceholders() {
		this.miniPlaceholders = true;
	}

	protected void registerCarbon() {
		CarbonChat carbonChat = CarbonChatProvider.carbonChat();
		CarbonListener listener = new CarbonListener(this.webhookLogger, carbonChat);
		listener.register();
		this.logger.info("Enabling CarbonChat events");
	}
}

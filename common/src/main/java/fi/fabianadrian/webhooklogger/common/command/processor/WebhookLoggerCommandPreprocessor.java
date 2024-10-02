package fi.fabianadrian.webhooklogger.common.command.processor;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.command.ContextKeys;
import net.kyori.adventure.audience.Audience;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;

public final class WebhookLoggerCommandPreprocessor implements CommandPreprocessor<Audience> {
	private final WebhookLogger webhookLogger;

	public WebhookLoggerCommandPreprocessor(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	@Override
	public void accept(@NonNull CommandPreprocessingContext<Audience> context) {
		CommandContext<Audience> commandContext = context.commandContext();
		commandContext.store(ContextKeys.WEBHOOK_LOGGER_KEY, webhookLogger);
	}
}

package fi.fabianadrian.webhooklogger.common.command.processor;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.command.Commander;
import fi.fabianadrian.webhooklogger.common.command.ContextKeys;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;

public final class WebhookLoggerCommandPreprocessor implements CommandPreprocessor<Commander> {
	private final WebhookLogger webhookLogger;

	public WebhookLoggerCommandPreprocessor(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	@Override
	public void accept(@NonNull CommandPreprocessingContext<Commander> context) {
		CommandContext<Commander> commandContext = context.commandContext();
		commandContext.store(ContextKeys.WEBHOOK_LOGGER_KEY, this.webhookLogger);
	}
}

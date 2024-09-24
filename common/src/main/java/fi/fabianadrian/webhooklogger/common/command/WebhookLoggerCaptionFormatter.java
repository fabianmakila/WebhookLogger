package fi.fabianadrian.webhooklogger.common.command;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter;
import org.incendo.cloud.minecraft.extras.caption.RichVariable;

import java.util.List;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public final class WebhookLoggerCaptionFormatter implements ComponentCaptionFormatter<Commander> {
	@Override
	public @NonNull Component formatCaption(@NonNull Caption captionKey, @NonNull Commander recipient, @NonNull String caption, @NonNull List<@NonNull CaptionVariable> variables) {
		return translatable("cloud." + captionKey.key(), variables.stream().map(variable -> {
			if (variable instanceof RichVariable) {
				return (RichVariable) variable;
			} else {
				return text(variable.value());
			}
		}).collect(Collectors.toList()));
	}
}

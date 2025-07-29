package fi.fabianadrian.webhooklogger.paper.platform;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.NotNull;

public record PaperCommandSender(CommandSourceStack stack) implements ForwardingAudience.Single {
	@Override
	public @NotNull Audience audience() {
		return this.stack.getSender();
	}
}

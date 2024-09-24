package fi.fabianadrian.webhooklogger.paper.command;

import fi.fabianadrian.webhooklogger.common.command.Commander;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public record PaperCommander(CommandSourceStack stack) implements Commander {

	@Override
	public @NotNull Audience audience() {
		return this.stack.getSender();
	}


}

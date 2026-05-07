package fi.fabianadrian.webhooklogger.paper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.webhooklogger.common.CommandManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.spongepowered.configurate.ConfigurateException;

import java.util.ArrayList;
import java.util.List;

import static io.papermc.paper.command.brigadier.Commands.literal;

public final class PaperCommandManager extends CommandManager {
	private final WebhookLoggerPaper plugin;
	private final List<LiteralCommandNode<CommandSourceStack>> nodes = new ArrayList<>();

	public PaperCommandManager(WebhookLoggerPaper plugin) {
		this.plugin = plugin;

		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("webhooklogger");
		this.nodes.add(rootBuilder.then(Commands.literal("reload")
				.requires(stack -> stack.getSender().hasPermission(PERMISSION_RELOAD))
				.executes(this::executeReload)
		).build());
	}

	public void register() {
		this.plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			this.nodes.forEach(commands::register);
		});
	}

	private int executeReload(CommandContext<CommandSourceStack> context) {
		try {
			this.plugin.webhookLogger().reload();
			context.getSource().getSender().sendMessage(COMPONENT_RELOAD_SUCCESS);
		} catch (ConfigurateException e) {
			context.getSource().getSender().sendMessage(COMPONENT_RELOAD_FAILURE);
			this.plugin.logger().error("Couldn't load configuration", e);
		}
		return Command.SINGLE_SUCCESS;
	}
}

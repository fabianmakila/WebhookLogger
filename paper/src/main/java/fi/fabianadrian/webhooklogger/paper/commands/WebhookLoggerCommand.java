package fi.fabianadrian.webhooklogger.paper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;

import static io.papermc.paper.command.brigadier.Commands.literal;

@SuppressWarnings("UnstableApiUsage")
public final class WebhookLoggerCommand {
	private final WebhookLogger webhookLogger;
	private final LifecycleEventManager<Plugin> manager;

	public WebhookLoggerCommand(WebhookLoggerPaper plugin) {
		this.webhookLogger = plugin.webhookLogger();
		this.manager = plugin.getLifecycleManager();
	}

	public void register() {
		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("webhooklogger");

		LiteralCommandNode<CommandSourceStack> reloadNode = rootBuilder.then(literal("reload")
				.requires(stack -> stack.getSender().hasPermission("webhooklogger.command.reload"))
				.executes(this::executeReload)
		).build();

		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(reloadNode);
		});
	}

	private int executeReload(CommandContext<CommandSourceStack> ctx) {
		this.webhookLogger.reload();
		ctx.getSource().getSender().sendMessage(Component.translatable("webhooklogger.command.reload"));
		return Command.SINGLE_SUCCESS;
	}
}

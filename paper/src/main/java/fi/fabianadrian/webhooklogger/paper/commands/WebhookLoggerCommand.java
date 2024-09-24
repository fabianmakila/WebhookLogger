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
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import static io.papermc.paper.command.brigadier.Commands.literal;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("UnstableApiUsage")
public final class WebhookLoggerCommand {
	private static final Component COMPONENT_SUCCESS = translatable()
			.key("webhooklogger.command.reload.success")
			.color(NamedTextColor.GREEN)
			.build();
	private static final Component COMPONENT_FAILURE = translatable()
			.key("webhooklogger.command.reload.failure")
			.color(NamedTextColor.RED)
			.build();
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
		CommandSender sender = ctx.getSource().getSender();
		if (this.webhookLogger.reload()) {
			sender.sendMessage(COMPONENT_SUCCESS);
		} else {
			sender.sendMessage(COMPONENT_FAILURE);
		}

		return Command.SINGLE_SUCCESS;
	}
}

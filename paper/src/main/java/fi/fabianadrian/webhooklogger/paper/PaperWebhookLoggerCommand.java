package fi.fabianadrian.webhooklogger.paper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.webhooklogger.common.WebhookLoggerCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.spongepowered.configurate.ConfigurateException;

import static io.papermc.paper.command.brigadier.Commands.literal;

public final class PaperWebhookLoggerCommand extends WebhookLoggerCommand {
	private final WebhookLoggerPaper plugin;
	private final LifecycleEventManager<Plugin> manager;

	public PaperWebhookLoggerCommand(WebhookLoggerPaper plugin) {
		this.plugin = plugin;
		this.manager = plugin.getLifecycleManager();
	}

	public void register() {
		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("webhooklogger")
				.requires(stack -> stack.getSender().hasPermission(PERMISSION_RELOAD));

		LiteralCommandNode<CommandSourceStack> reloadNode = rootBuilder.then(literal("reload")
				.executes(this::executeReload)
		).build();

		this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(reloadNode);
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

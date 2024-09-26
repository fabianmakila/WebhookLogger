package fi.fabianadrian.webhooklogger.common.platform;

import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface Platform {
	Logger logger();
	Path configPath();
	CommandManager<Audience> commandManager();
	void registerListeners();
}

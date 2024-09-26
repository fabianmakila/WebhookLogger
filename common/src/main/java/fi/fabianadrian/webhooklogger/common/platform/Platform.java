package fi.fabianadrian.webhooklogger.common.platform;

import fi.fabianadrian.webhooklogger.common.command.Commander;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface Platform {
	Logger logger();
	Path configPath();
	CommandManager<Commander> commandManager();
	void registerListeners();
}

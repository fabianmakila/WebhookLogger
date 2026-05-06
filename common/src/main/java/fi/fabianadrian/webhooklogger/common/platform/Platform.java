package fi.fabianadrian.webhooklogger.common.platform;

import fi.fabianadrian.webhooklogger.common.DependencyManager;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface Platform {
	Logger logger();

	Path configPath();

	ListenerManager listenerManager();

	ComponentFlattener componentFlattener();

	DependencyManager dependencyManager();
}

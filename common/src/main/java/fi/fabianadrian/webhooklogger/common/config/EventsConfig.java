package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.config.event.JoinQuitEventConfig;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface EventsConfig {
	@AnnotationBasedSorter.Order(0)
	@SubSection
	ChatEventConfig chat();

	@AnnotationBasedSorter.Order(1)
	@SubSection
	CommandEventConfig command();

	@AnnotationBasedSorter.Order(2)
	@SubSection
	DeathEventConfig death();

	@AnnotationBasedSorter.Order(3)
	@SubSection
	JoinQuitEventConfig joinQuit();
}

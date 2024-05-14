package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.event.*;
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
	JoinEventConfig join();

	@AnnotationBasedSorter.Order(4)
	@SubSection
	QuitEventConfig quit();
}

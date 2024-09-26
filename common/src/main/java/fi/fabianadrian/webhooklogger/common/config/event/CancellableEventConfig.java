package fi.fabianadrian.webhooklogger.common.config.event;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface CancellableEventConfig {
	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultBoolean(true)
	@ConfComments("Whether this event should be logged even when cancelled.")
	boolean logCancelled();
}

package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.event.EventType;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.List;

public interface Webhook {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("")
	String url();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultStrings({})
	List<EventType> events();
}

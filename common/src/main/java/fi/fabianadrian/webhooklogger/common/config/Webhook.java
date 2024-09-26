package fi.fabianadrian.webhooklogger.common.config;

import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.regex.Pattern;

public interface Webhook {
	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("")
	String url();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultStrings({})
	Pattern regex();
}

package fi.fabianadrian.webhookchatlogger.common.config.section;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.time.ZoneId;

public interface PlaceholderConfigSection {

	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultString("HH:mm:ss")
	@ConfComments({
			"Format for the <timestamp> placeholder."
	})
	String timestampFormat();

	@AnnotationBasedSorter.Order(1)
	@ConfDefault.DefaultString("default")
	@ConfComments({
			"The timezone used in <timestamp> placeholder.",
			"Set to 'default' to use the server timezone."
	})
	ZoneId timeZone();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultString("[Cancelled] ")
	@ConfComments("The text used in <cancelled> placeholder.")
	String cancelled();
}

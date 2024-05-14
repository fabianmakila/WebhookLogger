package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import fi.fabianadrian.webhookchatlogger.common.config.section.PlaceholderConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface MainConfig {
	@AnnotationBasedSorter.Order(0)
	@ConfComments("Configuration options for various placeholders.")
	@SubSection
	PlaceholderConfigSection placeholders();

	@AnnotationBasedSorter.Order(1)
	@SubSection
	@ConfComments("Configuration options for the Discord client.")
	DiscordConfigSection discord();
}

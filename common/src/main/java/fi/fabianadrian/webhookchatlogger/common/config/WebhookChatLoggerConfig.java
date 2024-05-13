package fi.fabianadrian.webhookchatlogger.common.config;

import fi.fabianadrian.webhookchatlogger.common.config.section.CommandSection;
import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import fi.fabianadrian.webhookchatlogger.common.config.section.ChatSection;
import fi.fabianadrian.webhookchatlogger.common.config.section.PlaceholderConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

public interface WebhookChatLoggerConfig {
	@AnnotationBasedSorter.Order(0)
	@SubSection
	ChatSection chat();

	@AnnotationBasedSorter.Order(1)
	@SubSection
	CommandSection command();


	@AnnotationBasedSorter.Order(2)
	@ConfComments("Configuration options for various placeholders.")
	@SubSection
	PlaceholderConfigSection placeholders();

	@AnnotationBasedSorter.Order(3)
	@SubSection
	@ConfKey("discord")
	@ConfComments("Configuration options for the Discord client.")
	DiscordConfigSection discordSection();
}

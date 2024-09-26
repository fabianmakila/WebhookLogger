package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.config.section.PlaceholderConfigSection;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface MainConfig {
	static List<Webhook> defaultWebhooks() {
		return List.of(new Webhook() {
			@Override
			public String url() {
				return "";
			}

			@Override
			public List<EventType> events() {
				return List.of(EventType.CHAT);
			}
		});
	}

	static Map<String, String> defaultTextReplacements() {
		Map<String, String> replacementMap = new HashMap<>();
		replacementMap.put("@", "(at)");
		replacementMap.put("#", "(hashtag)");
		return replacementMap;
	}

	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultObject("defaultWebhooks")
	@ConfComments("You should only configure 1 webhook per Discord channel to avoid rate limits.")
	List<@SubSection Webhook> webhooks();

	@AnnotationBasedSorter.Order(1)
	@ConfComments({
			"How often to send messages (in seconds).",
			"Should be a value between 1 and 10.",
			"The default value is 5."
	})
	@ConfDefault.DefaultInteger(5)
	int sendRate();

	@AnnotationBasedSorter.Order(2)
	@ConfDefault.DefaultObject("defaultTextReplacements")
	@ConfComments({
			"You can use this to filter or replace text.",
			"Supports regex."
	})
	Map<Pattern, String> textReplacements();

	@AnnotationBasedSorter.Order(3)
	@ConfComments("Configuration options for various placeholders.")
	@SubSection
	PlaceholderConfigSection placeholders();

	interface Webhook {
		@AnnotationBasedSorter.Order(0)
		@ConfDefault.DefaultString("")
		String url();

		@AnnotationBasedSorter.Order(1)
		@ConfDefault.DefaultStrings({})
		@ConfComments("Available events: CHAT, COMMAND, DEATH and JOINQUIT")
		List<EventType> events();
	}
}

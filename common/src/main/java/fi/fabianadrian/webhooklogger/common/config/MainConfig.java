package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.config.section.PlaceholderConfigSection;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.HashMap;
import java.util.Map;

public interface MainConfig {
	static Map<String, String> defaultWebhooks() {
		return Map.of("default", "");
	}

	static Map<String, String> defaultTextReplacements() {
		Map<String, String> replacementMap = new HashMap<>();
		replacementMap.put("@", "(at)");
		replacementMap.put("#", "(hashtag)");
		return replacementMap;
	}

	@AnnotationBasedSorter.Order(0)
	@ConfDefault.DefaultObject("defaultWebhooks")
	Map<String, String> webhooks();

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
	Map<String, String> textReplacements();

	@AnnotationBasedSorter.Order(3)
	@ConfComments("Configuration options for various placeholders.")
	@SubSection
	PlaceholderConfigSection placeholders();
}

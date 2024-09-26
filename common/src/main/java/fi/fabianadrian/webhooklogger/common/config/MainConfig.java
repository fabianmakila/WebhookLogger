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
	@ConfComments({
			"Here you can configure your webhook URL's.",
			"If no webhook is defined for a specific event the \"default\" webhook will be used instead.",
			"Available events can be found in the events.yml file."
	})
	List<Webhook> webhooks();

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
	Map<String, String> textReplacements();

	@AnnotationBasedSorter.Order(3)
	@ConfComments("Configuration options for various placeholders.")
	@SubSection
	PlaceholderConfigSection placeholders();
}

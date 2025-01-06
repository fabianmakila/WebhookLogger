package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.webhook.MessageStyle;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.SubSection;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface MainConfig {
	static List<WebhookConfig> defaultWebhooks() {
		return List.of(new WebhookConfig() {
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
			"You should only configure 1 webhook per Discord channel to avoid rate limits.",
			"Available events: CHAT, COMMAND, DEATH and JOINQUIT"
	})
	List<@SubSection WebhookConfig> webhooks();

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
	MainConfig.PlaceholderConfig placeholders();

	@AnnotationBasedSorter.Order(4)
	@ConfDefault.DefaultString("DEFAULT")
	MessageStyle messageStyle();

	@SubSection
	interface PlaceholderConfig {

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
		ZoneId timestampTimezone();

		@AnnotationBasedSorter.Order(2)
		@ConfDefault.DefaultString("[Cancelled] ")
		@ConfComments("The text used in <cancelled> placeholder.")
		String cancelled();

		@AnnotationBasedSorter.Order(3)
		@ConfDefault.DefaultString("x<x>, y<y>, z<z>")
		@ConfComments({
				"Format for the <location> placeholder."
		})
		String locationFormat();
	}

	interface WebhookConfig {
		@AnnotationBasedSorter.Order(0)
		String url();

		@AnnotationBasedSorter.Order(1)
		List<EventType> events();
	}
}

package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.webhook.MessageStyle;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@ConfigSerializable
public class MainConfig {
	@Comment("""
			You should only configure 1 webhook per Discord channel to avoid rate limits.
			Available events: CHAT, COMMAND, DEATH, JOIN and QUIT
			""")
	private List<WebhookConfig> webhooks = List.of(new WebhookConfig());
	@Comment("""
			You can use this to filter or replace text.
			Supports regex.
			""")
	private Map<Pattern, String> textReplacements = Map.of(
			Pattern.compile("@"), "(at)",
			Pattern.compile("#"), "(hashtag)"
	);

	private PlaceholderConfig placeholders = new PlaceholderConfig();

	public List<WebhookConfig> webhooks() {
		return this.webhooks;
	}

	public Map<Pattern, String> textReplacements() {
		return this.textReplacements;
	}

	public PlaceholderConfig placeholders() {
		return this.placeholders;
	}

	@ConfigSerializable
	public static class WebhookConfig {
		private String url = "";
		private List<EventType> events = List.of(EventType.CHAT);
		private Integer sendRate;
		private MessageStyle messageStyle;
		private Integer minimumQueueSize;

		public String url() {
			return this.url;
		}

		public List<EventType> events() {
			return this.events;
		}

		public Integer sendRate() {
			return this.sendRate;
		}

		public MessageStyle messageStyle() {
			return this.messageStyle;
		}

		public Integer minimumQueueSize() {
			return this.minimumQueueSize;
		}
	}

	@ConfigSerializable
	public static class PlaceholderConfig {
		@Comment("Format for the <timestamp> placeholder.")
		private String timestampFormat = "HH:mm:ss";
		@Comment("""
				The timezone used in <timestamp> placeholder.
				Set to 'default' to use the server timezone.
				"""
		)
		private ZoneId timestampTimezone = ZoneId.systemDefault();
		@Comment("Format for the <location> placeholder."
		)
		private String locationFormat = "x<x>, y<y>, z<z>";
		@Comment("The text used in <cancelled> placeholder.")
		private String cancelled = "[Cancelled] ";

		public String timestampFormat() {
			return this.timestampFormat;
		}

		public ZoneId timestampTimezone() {
			return this.timestampTimezone;
		}

		public String locationFormat() {
			return this.locationFormat;
		}

		public String cancelled() {
			return this.cancelled;
		}
	}
}

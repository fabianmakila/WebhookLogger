package fi.fabianadrian.webhookchatlogger.common.config.section;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.HashMap;
import java.util.Map;

public interface DiscordConfigSection {

	static Map<String, String> defaultTextReplacements() {
		Map<String, String> replacementMap = new HashMap<>();
		replacementMap.put("@", "(at)");
		replacementMap.put("#", "(hashtag)");
		return replacementMap;
	}

	@AnnotationBasedSorter.Order(0)
	@ConfComments("Discord webhook's ID.")
	@ConfDefault.DefaultString("")
	String id();

	@AnnotationBasedSorter.Order(1)
	@ConfComments("Dicsord webhook's secret token.")
	@ConfDefault.DefaultString("")
	String token();

	@AnnotationBasedSorter.Order(2)
	@ConfComments({
			"How often to send messages (in seconds).",
			"Should be a value between 1 and 10.",
			"The default value is 5."
	})
	@ConfDefault.DefaultInteger(5)
	int sendRate();

	@AnnotationBasedSorter.Order(3)
	@ConfDefault.DefaultObject("defaultTextReplacements")
	Map<String, String> textReplacements();
}

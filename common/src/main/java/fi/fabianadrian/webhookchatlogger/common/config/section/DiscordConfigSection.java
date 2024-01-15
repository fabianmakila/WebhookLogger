package fi.fabianadrian.webhookchatlogger.common.config.section;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;

public interface DiscordConfigSection {
	@ConfComments("Discord webhook's ID.")
	@ConfDefault.DefaultString("")
	String id();

	@ConfComments("Dicsord webhook's secret token.")
	@ConfDefault.DefaultString("")
	String token();

	@ConfDefault.DefaultString("<author>: <message>")
	@ConfComments("""
			Placeholders:
			<author> - Author's name
			<message> - Message content
			""")
	String messageFormat();

	@ConfComments("""
			How often to send messages (in seconds).
			Should be a value between 1 and 10.
			The default value is 5.
			""")
	@ConfDefault.DefaultInteger(5)
	int sendRate();
}

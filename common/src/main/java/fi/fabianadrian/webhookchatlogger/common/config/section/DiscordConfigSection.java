package fi.fabianadrian.webhookchatlogger.common.config.section;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;

public interface DiscordConfigSection {

	@ConfDefault.DefaultString("")
	String id();

	@ConfDefault.DefaultString("")
	String token();

	@ConfDefault.DefaultString("**%1$s > ** %2$s")
	@ConfComments({
			"Placeholders:",
			"%1$s - Player name",
			"%2$s - Message"
	})
	String messageFormat();

	@ConfDefault.DefaultInteger(5)
	int sendRate();
}

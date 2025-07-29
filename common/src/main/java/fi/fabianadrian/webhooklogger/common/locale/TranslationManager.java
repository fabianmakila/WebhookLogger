package fi.fabianadrian.webhooklogger.common.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TranslationManager {
	public static final List<Locale> BUNDLED_LOCALES = List.of(Locale.US, Locale.of("fi", "FI"));

	private final MiniMessageTranslationStore store;

	public TranslationManager() {
		this.store = MiniMessageTranslationStore.create(Key.key("webhooklogger", "main"));
		loadFromResourceBundle();
		GlobalTranslator.translator().addSource(this.store);
	}

	private void loadFromResourceBundle() {
		BUNDLED_LOCALES.forEach(locale -> {
			ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
			this.store.registerAll(locale, bundle, false);
		});
	}
}

package fi.fabianadrian.webhooklogger.common.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.slf4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TranslationManager {
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	public static final List<Locale> BUNDLED_LOCALES = List.of(Locale.of("fi", "FI"));

	private final Logger logger;
	private final TranslationRegistry registry;

	public TranslationManager(Logger logger) {
		this.logger = logger;

		registry = TranslationRegistry.create(Key.key("webhooklogger", "main"));
		registry.defaultLocale(DEFAULT_LOCALE);

		loadFromResourceBundle();

		GlobalTranslator.translator().addSource(registry);
	}

	private void loadFromResourceBundle() {
		ResourceBundle defaultBundle = ResourceBundle.getBundle("messages", DEFAULT_LOCALE);
		try {
			registry.registerAll(DEFAULT_LOCALE, defaultBundle, false);
			BUNDLED_LOCALES.forEach(locale -> {
				ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
				registry.registerAll(locale, bundle, false);
			});
		} catch (IllegalArgumentException e) {
			logger.warn("Error loading default locale file", e);
		}
	}
}

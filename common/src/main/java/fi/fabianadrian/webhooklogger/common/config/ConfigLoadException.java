package fi.fabianadrian.webhooklogger.common.config;

public class ConfigLoadException extends Exception {
	public ConfigLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigLoadException(Throwable cause) {
		super(cause);
	}
}

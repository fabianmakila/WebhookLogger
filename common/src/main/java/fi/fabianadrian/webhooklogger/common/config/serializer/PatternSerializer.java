package fi.fabianadrian.webhooklogger.common.config.serializer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

public final class PatternSerializer implements TypeSerializer<Pattern> {
	public static final PatternSerializer INSTANCE = new PatternSerializer();

	@Override
	public Pattern deserialize(Type type, ConfigurationNode node) throws SerializationException {
		String regex = node.getString();
		if (regex == null) {
			throw new SerializationException("Regex can't be empty");
		}

		return Pattern.compile(regex);
	}

	@Override
	public void serialize(Type type, @Nullable Pattern pattern, ConfigurationNode node) throws SerializationException {
		if (pattern == null) {
			node.raw(null);
			return;
		}
		node.set(pattern.pattern());
	}
}

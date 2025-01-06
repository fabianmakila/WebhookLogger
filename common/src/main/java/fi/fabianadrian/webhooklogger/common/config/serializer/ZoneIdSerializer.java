package fi.fabianadrian.webhooklogger.common.config.serializer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.time.ZoneId;

public final class ZoneIdSerializer implements TypeSerializer<ZoneId> {
	public static final ZoneIdSerializer INSTANCE = new ZoneIdSerializer();

	@Override
	public ZoneId deserialize(Type type, ConfigurationNode node) throws SerializationException {
		String string = node.getString();

		if (string == null || "default".equalsIgnoreCase(string) || string.isBlank()) {
			return ZoneId.systemDefault();
		}

		return ZoneId.of(string);
	}

	@Override
	public void serialize(Type type, @Nullable ZoneId zoneId, ConfigurationNode node) throws SerializationException {
		if (zoneId == null) {
			node.raw(null);
			return;
		}
		if (ZoneId.systemDefault().equals(zoneId)) {
			node.set("default");
		} else {
			node.set(zoneId.getId());
		}
	}
}

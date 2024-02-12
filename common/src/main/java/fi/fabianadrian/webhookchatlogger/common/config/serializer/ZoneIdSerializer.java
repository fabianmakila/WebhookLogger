package fi.fabianadrian.webhookchatlogger.common.config.serializer;

import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

import java.time.ZoneId;

public class ZoneIdSerializer implements ValueSerialiser<ZoneId> {
	@Override
	public Class<ZoneId> getTargetClass() {
		return ZoneId.class;
	}

	@Override
	public ZoneId deserialise(FlexibleType flexibleType) throws BadValueException {
		String string = flexibleType.getString();

		if ("default".equalsIgnoreCase(string) || string.isBlank()) {
			return ZoneId.systemDefault();
		}

		return ZoneId.of(string);
	}

	@Override
	public Object serialise(ZoneId zoneId, Decomposer decomposer) {
		return zoneId.getId();
	}
}

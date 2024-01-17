package fi.fabianadrian.webhookchatlogger.common.config.serializer;

import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

import java.text.SimpleDateFormat;

public class SimpleDateFormatSerializer implements ValueSerialiser<SimpleDateFormat> {
	@Override
	public Class<SimpleDateFormat> getTargetClass() {
		return SimpleDateFormat.class;
	}

	@Override
	public SimpleDateFormat deserialise(FlexibleType flexibleType) throws BadValueException {
		String value = flexibleType.getString();
		return new SimpleDateFormat(value);
	}

	@Override
	public Object serialise(SimpleDateFormat value, Decomposer decomposer) {
		return value.toPattern();
	}
}

package fi.fabianadrian.webhooklogger.common.config.serializer;

import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

import java.util.regex.Pattern;

public final class PatternSerializer implements ValueSerialiser<Pattern> {
	@Override
	public Class<Pattern> getTargetClass() {
		return Pattern.class;
	}

	@Override
	public Pattern deserialise(FlexibleType flexibleType) throws BadValueException {
		String string = flexibleType.getString();
		return Pattern.compile(string);
	}

	@Override
	public Object serialise(Pattern value, Decomposer decomposer) {
		return value.pattern();
	}
}

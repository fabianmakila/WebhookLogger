package fi.fabianadrian.webhookchatlogger.common;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public record Message(Audience author, Component message) {
}

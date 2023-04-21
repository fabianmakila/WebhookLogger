package fi.fabianadrian.webhookchatlogger.common;

import net.kyori.adventure.text.Component;

import java.util.UUID;

public record Message(UUID authorUUID, String authorName, Component content) {
}

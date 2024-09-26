![Banner](/assets/webhooklogger-banner.svg)

> [!IMPORTANT]
> This README is intended for plugin developers. See the [Modrinth page](https://modrinth.com/plugin/webhooklogger) for a more user-friendly description.

# WebhookLogger
WebhookLogger is a cross-platform Minecraft plugin for logging various in-game events to Discord webhooks.

### Supported platforms
- [Paper](https://github.com/PaperMC/Paper)

## Structure
The project uses Adventure for most of the platform independent abstraction minimizing the need for writing our own platform adapters.  
All configurable messages use the MiniMessage format and also support MiniPlaceholders placeholders.

Configuration is handled using DazzleConf.

## Building
Building WebhookLogger requires Java 21. The recommended command for building is `./gradlew build`

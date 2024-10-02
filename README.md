![Banner](/assets/webhooklogger-banner.svg)

[![Modrinth](https://img.shields.io/modrinth/dt/yOG0TUXA?style=for-the-badge&logo=modrinth&labelColor=%23111827&color=%238b5cf6)](https://modrinth.com/plugin/webhooklogger)
![Repository Size Badge](https://img.shields.io/github/repo-size/fabianmakila/webhooklogger?style=for-the-badge&labelColor=%23111827&color=%238b5cf6)
[![Releases](https://img.shields.io/github/v/release/fabianmakila/webhooklogger?include_prereleases&style=for-the-badge&labelColor=%23111827&color=%238b5cf6)](https://github.com/fabianmakila/webhooklogger/releases)

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

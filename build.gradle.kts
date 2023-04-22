plugins {
    alias(libs.plugins.indra)
    java
}

group = "fi.fabianadrian"
version = "1.4.0"
description = "A simple plugin that forwards chat messages to a webhook."

indra {
    javaVersions().target(17)
}
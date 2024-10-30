package fi.fabianadrian.webhooklogger.common.platform;

import net.kyori.adventure.audience.ForwardingAudience;

import java.net.InetSocketAddress;

public interface Player extends ForwardingAudience.Single {
	InetSocketAddress address();
	Location location();
}

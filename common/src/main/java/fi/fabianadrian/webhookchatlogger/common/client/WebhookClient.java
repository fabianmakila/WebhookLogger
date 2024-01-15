package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.Message;

public interface WebhookClient {

    void log(Message message);

    void reload();
}

package coolbeans.microthings8266hub.esp8266;

public interface ThingClientConnection {

    void connect();
    void close();
    ActionResponse invokeAction(ActionRequest req);
}

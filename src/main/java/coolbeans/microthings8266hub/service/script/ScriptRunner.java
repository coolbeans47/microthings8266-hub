package coolbeans.microthings8266hub.service.script;

public interface ScriptRunner {

    Object execute(String script, String name);
    void setContext(ScriptContext context);
    ScriptContext getContext();
}

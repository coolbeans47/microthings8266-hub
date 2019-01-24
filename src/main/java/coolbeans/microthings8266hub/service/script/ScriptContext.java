package coolbeans.microthings8266hub.service.script;

import java.util.HashMap;
import java.util.Map;

public class ScriptContext {

    private Map<String, Object> context = new HashMap<>();

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}

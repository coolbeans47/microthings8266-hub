package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.Esp8266CommandExecutor;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class RhinoScriptRunner implements ScriptRunner {

    private final Esp8266CommandExecutor esp8266Executor;

    public RhinoScriptRunner(Esp8266CommandExecutor esp8266Executor) {
        this.esp8266Executor = esp8266Executor;
    }


    @Override
    public Object execute(String script, String name) {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();

            // Add a global variable "out" that is a JavaScript reflection
            // of System.out
            Object gpio = Context.javaToJS(esp8266Executor, scope);
            ScriptableObject.putProperty(scope, "gpio", gpio);

            Object result = cx.evaluateString(scope, script, name, 1, null);
            if (result instanceof NativeJavaObject) {
                result = ((NativeJavaObject) result).unwrap();
            }
            return result; //cx.toString(result);

        } finally {
            Context.exit();
        }
    }


}

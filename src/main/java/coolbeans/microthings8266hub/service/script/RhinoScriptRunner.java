package coolbeans.microthings8266hub.service.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class RhinoScriptRunner implements ScriptRunner {

    private ScriptContext globalContext;
    private Context ctx;
    private Scriptable globalScope;


    public RhinoScriptRunner() {
    }

    @Override
    public Object execute(String script, String name) {
        createContext();
        Scriptable scope = ctx.initStandardObjects();
        scope.setParentScope(globalScope);

        Object result = ctx.evaluateString(scope, script, name, 1, null);
        if (result instanceof NativeJavaObject) {
            result = ((NativeJavaObject) result).unwrap();
        }
        return result; //cx.toString(result);
    }

    @Override
    public void setContext(ScriptContext globalContext) {
        this.globalContext = globalContext;
        reloadGlobalContext();
    }

    public void reloadGlobalContext() {
        if (globalContext == null || globalContext.getContext().isEmpty()) return;
        createContext();
        globalScope = ctx.initStandardObjects();
        globalContext.getContext().keySet().forEach(key -> {
            Object val = Context.javaToJS(globalContext.getContext().get(key), globalScope);
            ScriptableObject.putConstProperty(globalScope, key, val);
        });
    }

    @Override
    public ScriptContext getContext() {
        return globalContext;
    }

    public void createContext() {
        if (ctx == null) {
            ctx = Context.enter();
        }
    }

}

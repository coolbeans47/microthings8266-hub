package coolbeans.microthings8266hub.service.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Scope("prototype")
public class RhinoScriptRunner implements ScriptRunner {

    private Logger logger = Logger.getLogger(RhinoScriptRunner.class.getName());

    private ScriptContext globalContext;
    private Scriptable globalScope;

    public RhinoScriptRunner() {
    }

    @Override
    public Object execute(String script, String name) {

        Context ctx = Context.enter();
        try {
            logger.info("Executing Script: " + name + " - " + script);

            if (globalScope == null) {
                globalScope = ctx.initStandardObjects();
                initGlobalScope();
            }

            Scriptable scope = ctx.newObject(globalScope);
            scope.setPrototype(globalScope);
            scope.setParentScope(null);

            Object result = ctx.evaluateString(scope, script, name, 1, null);
            if (result instanceof NativeJavaObject) {
                result = ((NativeJavaObject) result).unwrap();
            }
            return result; //cx.toString(result);

        } finally {
            Context.exit();
        }
    }

    @Override
    public void setContext(ScriptContext globalContext) {
        this.globalContext = globalContext;
    }

    public void initGlobalScope() {
        globalContext.getContext().keySet().forEach(key -> {
            Object val = Context.javaToJS(globalContext.getContext().get(key), globalScope);
            ScriptableObject.putConstProperty(globalScope, key, val);
        });
    }

    @Override
    public ScriptContext getContext() {
        return globalContext;
    }


}

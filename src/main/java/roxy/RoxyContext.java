package roxy;

import com.oracle.truffle.api.ExecutionContext;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 */
public final class RoxyContext extends ExecutionContext {

    private final BufferedReader input;
    private final PrintWriter output;
    private final TruffleLanguage.Env env;

    public RoxyContext(TruffleLanguage.Env env, BufferedReader in, PrintWriter out) {
        this.input = in;
        this.output = out;
        this.env = env;
    }


    public static NodeInfo lookupNodeInfo(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        NodeInfo info = clazz.getAnnotation(NodeInfo.class);
        if (info != null) {
            return info;
        } else {
            return lookupNodeInfo(clazz.getSuperclass());
        }
    }
}

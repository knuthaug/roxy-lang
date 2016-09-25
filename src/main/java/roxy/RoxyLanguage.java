package roxy;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.ObjectLocation;
import com.oracle.truffle.api.source.Source;

import com.sun.org.apache.xpath.internal.ExpressionNode;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import roxy.antlr.RoxyBaseListener;
import roxy.antlr.RoxyLexer;
import roxy.antlr.RoxyListener;
import roxy.antlr.RoxyParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;


/**
 *
 */
@TruffleLanguage.Registration(name = "Roxy", version = "0.0.1", mimeType = RoxyLanguage.MIME_TYPE)
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, DebuggerTags.AlwaysHalt.class})
public final class RoxyLanguage extends TruffleLanguage<RoxyContext> {

    public static final String MIME_TYPE = "application/x-roxy";

    /**
     * The singleton instance of the language.
     */
    public static final RoxyLanguage INSTANCE = new RoxyLanguage();

    /**
     * No instances allowed apart from the {@link #INSTANCE singleton instance}.
     */
    private RoxyLanguage() {
    }

    @Override
    protected RoxyContext createContext(Env env) {
        return new RoxyContext();
    }

    @Override
    protected CallTarget parse(Source source, Node node, String... argumentNames) throws IOException {
        ExpressionNode body = RoxyLanguage.parseSource(source);
        RoxyRootNode root = new RoxyRootNode(null, body), null, "[no-main]");
        return Truffle.getRuntime().createCallTarget(root);
    }

    private static ExpressionNode parseSource(Source source) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(source.getInputStream());
        RoxyLexer lexer = new RoxyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RoxyParser parser = new RoxyParser(tokens);

        RoxyParser.ProgContext context = parser.prog();

        ParseTreeWalker walker = new ParseTreeWalker();
        RoxyListener listener = new RoxyBaseListener();
        walker.walk(listener, context);

        return listener.getExpression();

    }

    @Override
    protected Object findExportedSymbol(RoxyContext context, String globalName, boolean onlyExplicit) {
      return new Object();
    }

    @Override
    protected Object getLanguageGlobal(RoxyContext context) {
        /*
         * The context itself is the global function registry. SL does not have global variables.
         */
        return context;
    }

    @Override
    protected boolean isObjectOfLanguage(Object object) {
        return object instanceof RoxyFunction;
    }

    @Override
    protected Object evalInContext(Source source, Node node, MaterializedFrame mFrame) throws IOException {
        throw new IllegalStateException("evalInContext not supported in SL");
    }

    @Override
    protected String toString(RoxyContext context, Object value) {
        if (value == RoxyNull.SINGLETON) {
            return "NULL";
        }
        if (value instanceof Long) {
            return Long.toString((Long) value);
        }
        return super.toString(context, value);
    }


}

package roxy;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.vm.PolyglotEngine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;

/**
 *
 */
public final class RoxyMain {

    public static void main(String[] args) throws IOException {
        Source source;
        if (args.length == 0) {
            // @formatter:off
            source = Source.newBuilder(new InputStreamReader(System.in)).
                name("<stdin>").
                mimeType(RoxyLanguage.MIME_TYPE).
                build();
            // @formatter:on
        } else {
            source = Source.newBuilder(new File(args[0])).build();
        }

        executeSource(source, System.in, System.out);
    }

    private static void executeSource(Source source, InputStream in, PrintStream out) {
        out.println("== running on " + Truffle.getRuntime().getName());

        PolyglotEngine engine = PolyglotEngine.newBuilder().setIn(in).setOut(out).build();
        assert engine.getLanguages().containsKey(RoxyLanguage.MIME_TYPE);

        try {
            PolyglotEngine.Value result = engine.eval(source);

            if (result == null) {
                throw new RoxyException("No function main() defined in Roxy source file.");
            } else if (result.get() != RoxyNull.SINGLETON) {
                out.println(result.get());
            }

        } catch (Throwable ex) {
            /*
             * PolyglotEngine.eval wraps the actual exception in an IOException, so we have to
             * unwrap here.
             */
            Throwable cause = ex.getCause();
            if (cause instanceof UnsupportedSpecializationException) {
                out.println(formatTypeError((UnsupportedSpecializationException) cause));
            } else if (cause instanceof RoxyUndefinedNameException) {
                out.println(cause.getMessage());
            } else {
                /* Unexpected error, just print out the full stack trace for debugging purposes. */
                ex.printStackTrace(out);
            }
        }

        engine.dispose();
    }

    /**
     * Provides a user-readable message for run-time type errors. SL is strongly typed, i.e., there
     * are no automatic type conversions of values. Therefore, Truffle does the type checking for
     * us: if no matching node specialization for the actual values is found, then we have a type
     * error. Specialized nodes use the {@link UnsupportedSpecializationException} to report that no
     * specialization was found. We therefore just have to convert the information encapsulated in
     * this exception in a user-readable form.
     */
    public static String formatTypeError(UnsupportedSpecializationException ex) {
        StringBuilder result = new StringBuilder();
        result.append("Type error");
        if (ex.getNode() != null && ex.getNode().getSourceSection() != null) {
            SourceSection ss = ex.getNode().getSourceSection();
            if (ss != null && ss.getSource() != null) {
                result.append(" at ").append(ss.getSource().getName()).append(" line ").append(ss.getStartLine()).append(" col ").append(ss.getStartColumn());
            }
        }
        result.append(": operation");
        if (ex.getNode() != null) {
            NodeInfo nodeInfo = RoxyContext.lookupNodeInfo(ex.getNode().getClass());
            if (nodeInfo != null) {
                result.append(" \"").append(nodeInfo.shortName()).append("\"");
            }
        }
        result.append(" not defined for");

        String sep = " ";
        for (int i = 0; i < ex.getSuppliedValues().length; i++) {
            Object value = ex.getSuppliedValues()[i];
            Node node = ex.getSuppliedNodes()[i];
            if (node != null) {
                result.append(sep);
                sep = ", ";

                if (value instanceof Long || value instanceof BigInteger) {
                    result.append("Number ").append(value);
                } else if (value instanceof Boolean) {
                    result.append("Boolean ").append(value);
                } else if (value instanceof String) {
                    result.append("String \"").append(value).append("\"");
                } else if (value instanceof RoxyFunction) {
                    result.append("Function ").append(value);
                } else if (value == RoxyNull.SINGLETON) {
                    result.append("NULL");
                } else if (value == null) {
                    // value is not evaluated because of short circuit evaluation
                    result.append("ANY");
                } else {
                    result.append(value);
                }
            }
        }
        return result.toString();
    }
}

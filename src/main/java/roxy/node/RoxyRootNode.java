package roxy.node;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.ObjectLocation;
import com.oracle.truffle.api.source.SourceSection;
import roxy.RoxyLanguage;

/**
 *
 */
@NodeInfo(language = "SL", description = "The root of all SL execution trees")
public class RoxyRootNode extends RootNode {

    /**
     * The function body that is executed, and specialized during execution.
     */
    @Child
    private ExpressionNode bodyNode;

    /**
     * The name of the function, for printing purposes only.
     */
    private final String name;

    @CompilerDirectives.CompilationFinal
    private boolean isCloningAllowed;

    public RoxyRootNode(FrameDescriptor frameDescriptor, ExpressionNode bodyNode, SourceSection sourceSection,
            String name) {
        super(RoxyLanguage.class, sourceSection, frameDescriptor);
        this.bodyNode = bodyNode;
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        //assert RoxyLanguage.INSTANCE.findContext() != null;
        try {
            return bodyNode.executeGeneric(frame);
        } catch (UnexpectedResultException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ExpressionNode getBodyNode() {
        return bodyNode;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setCloningAllowed(boolean isCloningAllowed) {
        this.isCloningAllowed = isCloningAllowed;
    }

    @Override
    public boolean isCloningAllowed() {
        return isCloningAllowed;
    }

    @Override
    public String toString() {
        return "root " + name;
    }
}

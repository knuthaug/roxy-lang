package roxy.node;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import roxy.RoxyTypes;

/**
 *
 */
@TypeSystemReference(RoxyTypes.class)
@NodeInfo(description = "The abstract base node for all expressions")
public abstract class ExpressionNode {

    public abstract Object executeGeneric(VirtualFrame frame) throws UnexpectedResultException;

    /*
     * Execute methods for specialized types. They all follow the same pattern: they call the
     * generic execution method and then expect a result of their return type. Type-specialized
     * subclasses overwrite the appropriate methods.
     */

    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return 1L;
    }

}

package roxy.node;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

/**
 *
 */
@NodeChildren({@NodeChild("leftNode"), @NodeChild("rightNode")})
public class BinaryNode extends ExpressionNode {


    @Override
    public Object executeGeneric(VirtualFrame frame) throws UnexpectedResultException {
        return null;
    }
}

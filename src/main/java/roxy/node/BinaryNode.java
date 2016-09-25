package roxy.node;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;

/**
 *
 */
@NodeChildren({@NodeChild("leftNode"), @NodeChild("rightNode")})
public abstract class BinaryNode extends ExpressionNode {


}

package roxy.node;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

/**
 *
 */
@NodeInfo(description = "A paranthesized expression")
public class ParenExpressionNode extends ExpressionNode {

    @Node.Child
    private ExpressionNode expression;

    public ParenExpressionNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void setExpression(ExpressionNode expr) {
        this.expression = expr;
    }

    @Override
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return expression.executeLong(frame);
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) throws UnexpectedResultException {
        return expression.executeGeneric(frame);
    }
}

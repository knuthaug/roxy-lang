package roxy.node;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

import java.math.BigDecimal;

/**
 *
 */
@NodeInfo(shortName = "/")
public class DivideNode extends BinaryNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long div(long left, long right) {
        long result = left / right;

        if ((left & right & result) < 0) {
          throw new ArithmeticException("Long overflow");
        }
        return result;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected BigDecimal div(BigDecimal left, BigDecimal right) {
        return left.divide(right);
    }

    @Override
    @Specialization
    @CompilerDirectives.TruffleBoundary
    public Object executeGeneric(VirtualFrame frame) throws UnexpectedResultException {
        return
    }
}

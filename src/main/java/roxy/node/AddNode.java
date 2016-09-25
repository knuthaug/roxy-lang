package roxy.node;


import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.math.BigDecimal;

/**
 *
 */
@NodeInfo(shortName = "+")
public abstract class AddNode extends BinaryNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long add(long left, long right) {
        return Math.addExact(left, right);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected BigDecimal add(BigDecimal left, BigDecimal right) {
        return left.add(right);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected BigDecimal add(Object left, Object right) {
        BigDecimal l = left instanceof BigDecimal ? (BigDecimal) left : BigDecimal.valueOf(Long.valueOf(left.toString()));
        BigDecimal r = left instanceof BigDecimal ? (BigDecimal) right : BigDecimal.valueOf(Long.valueOf(right.toString()));
        return l.add(r);
    }

}


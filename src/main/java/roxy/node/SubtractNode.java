package roxy.node;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ExactMath;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.math.BigInteger;

/**
 *
 */

@NodeInfo(shortName = "-")
public abstract class SubtractNode extends BinaryNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long sub(long left, long right) {
            return ExactMath.subtractExact(left, right);
        }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected BigInteger sub(BigInteger left, BigInteger right) {
            return left.subtract(right);
        }
}


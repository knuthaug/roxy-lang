package roxy.node;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.math.BigDecimal;

/**
 *
 */
@NodeInfo(shortName = "const")
public class BigDecimalNode extends BinaryNode {

    private final BigDecimal value;

    public BigDecimalNode(BigDecimal val) {
        this.value = val;
    }

    @Override
    public BigDecimal executeGeneric(VirtualFrame frame) {
        return value;
    }
}


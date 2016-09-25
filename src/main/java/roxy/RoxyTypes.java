package roxy;

import com.oracle.truffle.api.dsl.TypeSystem;
import com.oracle.truffle.api.dsl.internal.DSLOptions;

import java.math.BigDecimal;

/**
 *
 */
@TypeSystem({long.class, BigDecimal.class})
@DSLOptions
public abstract class RoxyTypes {

}

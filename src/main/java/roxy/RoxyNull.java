package roxy;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.TruffleObject;

/**
 *
 */
public final class RoxyNull implements TruffleObject {

    public static final Object SINGLETON = new RoxyNull();

    private RoxyNull() {

    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public ForeignAccess getForeignAccess() {
        return null;
    }
}

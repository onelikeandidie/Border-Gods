package net.onelikeandidie.bordergods.util;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IItemStackThrower {
    @Nullable
    UUID getThrownByUUID();
    void setThrownByUUID(@Nullable UUID thrownByUUID);
}

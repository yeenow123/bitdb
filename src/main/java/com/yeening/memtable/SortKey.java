package com.yeening.memtable;

import java.util.Arrays;
import java.util.Objects;

public class SortKey {

    byte[] key;
    int sequenceNumber;

    public SortKey(byte[] key, int sequenceNumber) {
        this.key = key;
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortKey sortKey = (SortKey) o;
        return sequenceNumber == sortKey.sequenceNumber &&
                Arrays.equals(key, sortKey.key);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sequenceNumber);
        result = 31 * result + Arrays.hashCode(key);
        return result;
    }
}

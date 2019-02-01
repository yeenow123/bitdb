package com.yeening.memtable;

import java.util.Comparator;

public class SortKeyComparator implements Comparator<SortKey> {
    @Override
    public int compare(SortKey o1, SortKey o2) {
        int keyCompare = compare(o1.key, o2.key);
        if (keyCompare != 0) {
            return keyCompare;
        }
        return Integer.compare(o1.sequenceNumber, o2.sequenceNumber);
    }

    // Taken from https://stackoverflow.com/questions/5108091/java-comparator-for-byte-array-lexicographic/5108711#5108711
    public int compare(byte[] left, byte[] right) {
        for (int i = 0, j = 0; i < left.length && j < right.length; i++, j++) {
            int a = (left[i] & 0xff);
            int b = (right[j] & 0xff);
            if (a != b) {
                return a - b;
            }
        }
        return left.length - right.length;
    }
}

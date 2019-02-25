package com.yeening.memtable;

import java.util.Map;
import java.util.TreeMap;

public class Memtable {

    private TreeMap<SortKey, byte[]> treeMap;

    public Memtable() {
        this.treeMap = new TreeMap<>(new SortKeyComparator());
    }

    public void set(int seqNumber, byte[] key, byte[] value) {
        SortKey s = new SortKey(key, seqNumber);

        treeMap.put(s, value);
    }

    public byte[] get(byte[] key, int lastSequence) {
        SortKey sk = new SortKey(key, lastSequence);

        Map.Entry<SortKey, byte[]> entry = treeMap.ceilingEntry(sk);

        if (entry == null) {
            return null;
        } else {
            return entry.getValue();
        }
    }
}

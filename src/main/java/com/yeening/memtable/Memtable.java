package com.yeening.memtable;

import java.util.TreeMap;

public class Memtable {

    private TreeMap<SortKey, byte[]> treeMap;
    private int lastSequenceNumber;

    public Memtable(int seqNumber) {
        this.treeMap = new TreeMap<>(new SortKeyComparator());
        this.lastSequenceNumber = seqNumber;
    }

    public void set(int seqNumber, byte[] key, byte[] value) {
        SortKey s = new SortKey(key, seqNumber);

        treeMap.put(s, value);
    }

    public byte[] get(byte[] key) {

    }
}

package com.yeening.record;

public class DataRecord {

    byte[] data;

    public DataRecord(byte[] data) {
        this.data = data;
    }

    public byte[] toByteArray() {
        return data;
    }
}

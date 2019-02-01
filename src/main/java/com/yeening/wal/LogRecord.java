package com.yeening.wal;

import java.nio.ByteBuffer;

public class LogRecord {

    // TODO: Account for log record header and data when calculating length

    int length;
    long sequenceNumber;
    long checksum;
    byte[] data;

    public final static int LENGTH_HEADER_SIZE = Integer.BYTES;
    public final static int CHECKSUM_HEADER_SIZE = Long.BYTES;
    public final static int SEQUENCE_HEADER_SIZE = Long.BYTES;

    public LogRecord(byte[] data, long sequenceNumber) {
        this.length = data.length;
        this.data = data;
        this.checksum = Utils.calculateChecksum(this.data);
        this.sequenceNumber = sequenceNumber;
    }

    public ByteBuffer toByteBuffer() {

        int bytesAllocated = LENGTH_HEADER_SIZE + CHECKSUM_HEADER_SIZE + SEQUENCE_HEADER_SIZE + length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(bytesAllocated);

        byteBuffer
                .putInt(length)
                .putLong(checksum)
                .putLong(sequenceNumber)
                .put(data);

        return byteBuffer;
    }
}

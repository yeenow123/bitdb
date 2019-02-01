package com.yeening.wal;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class LogRecord {

    // TODO: Account for log record header and data when calculating length
    int length;
    long checksum;
    byte[] data;

    public final static int LENGTH_HEADER_SIZE = Integer.BYTES;
    public final static int CHECKSUM_HEADER_SIZE = Long.BYTES;

    public LogRecord(byte[] data) {
        this.length = data.length;
        this.data = data;
        this.checksum = Utils.calculateChecksum(this.data);
    }

    public ByteBuffer toByteBuffer() {

        int bytesAllocated = LENGTH_HEADER_SIZE + CHECKSUM_HEADER_SIZE + length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(bytesAllocated);

        byteBuffer
                .putInt(length)
                .putLong(checksum)
                .put(data);

        return byteBuffer;
    }

    public static LogRecord fromChannel(FileChannel channel) throws Exception {
        ByteBuffer lengthBB = ByteBuffer.allocate(LENGTH_HEADER_SIZE);
        channel.read(lengthBB);
        lengthBB.flip();

        int dataLength = lengthBB.getInt();

        ByteBuffer checksumBB = ByteBuffer.allocate(CHECKSUM_HEADER_SIZE);
        channel.read(checksumBB);
        checksumBB.flip();

        long checksum = checksumBB.getLong();

        ByteBuffer dataBB = ByteBuffer.allocate(dataLength);
        channel.read(dataBB);
        dataBB.flip();

        byte[] dataRead = dataBB.array();
        long dataChecksum = Utils.calculateChecksum(dataRead);

        if (checksum != dataChecksum) {
            throw new Exception("Checksum mismatch!");
        }

        return new LogRecord(dataBB.array());
    }
}

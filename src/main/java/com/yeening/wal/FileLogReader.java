package com.yeening.wal;

import com.yeening.record.DataRecord;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileLogReader implements LogReader {

    private FileChannel fileChannel;

    private boolean eof = false;

    private long offset;

    public FileLogReader(FileChannel fileChannel) throws IOException {
        this.fileChannel = fileChannel;
        this.offset = fileChannel.position();
    }

    @Override
    public DataRecord readRecord() throws Exception {

        ByteBuffer bb = ByteBuffer.allocate(LogRecord.LENGTH_HEADER_SIZE);
        int bytesRead = readBytesSpecific(fileChannel, bb);
        if (bytesRead == -1) return null;

        bb = ByteBuffer.allocate(LogRecord.CHECKSUM_HEADER_SIZE);
        bytesRead = readBytesSpecific(fileChannel, bb);
        if (bytesRead == -1) return null;

        bb = ByteBuffer.allocate(LogRecord.CHECKSUM_HEADER_SIZE);
        bytesRead = readBytesSpecific(fileChannel, bb);
        if (bytesRead == -1) return null;

        try {
            LogRecord rec = LogRecord.fromChannel(fileChannel);
        } catch (IOException e) {

        }


        offset = fileChannel.position();

        return new DataRecord(rec.data);
    }

    @Override
    public long getOffset() {
        return offset;
    }

    private int readBytesSpecific(FileChannel channel, ByteBuffer bb) {
        int bytesRead;
        try {
            bytesRead = channel.read(bb);
        } catch (IOException e) {
            return -1;
        }

        bb.flip();
        return bytesRead;
    }

    private int readBytes(FileChannel channel) {
        ByteBuffer lengthBB = ByteBuffer.allocate(LogRecord.LENGTH_HEADER_SIZE);
        channel.read(lengthBB);
        lengthBB.flip();

        int dataLength = lengthBB.getInt();

        ByteBuffer checksumBB = ByteBuffer.allocate(LogRecord.CHECKSUM_HEADER_SIZE);
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

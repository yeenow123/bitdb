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
    public LogRecord readRecord() throws Exception {

        ByteBuffer bb = ByteBuffer.allocate(LogRecord.LENGTH_HEADER_SIZE);
        int bytesRead = readBytes(fileChannel, bb);
        if (bytesRead == -1) return null;

        int dataLength = bb.getInt();

        bb = ByteBuffer.allocate(LogRecord.CHECKSUM_HEADER_SIZE);
        bytesRead = readBytes(fileChannel, bb);
        if (bytesRead == -1) return null;

        bb = ByteBuffer.allocate(LogRecord.SEQUENCE_HEADER_SIZE);
        bytesRead = readBytes(fileChannel, bb);
        if (bytesRead == -1) return null;

        int seqNo = bb.getInt();

        bb = ByteBuffer.allocate(dataLength);
        bytesRead = readBytes(fileChannel, bb);
        if (bytesRead == -1) return null;

        LogRecord rec = new LogRecord(bb.array(), seqNo);

        offset = fileChannel.position();

        return rec;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    private int readBytes(FileChannel channel, ByteBuffer bb) {
        int bytesRead;
        try {
            bytesRead = channel.read(bb);
        } catch (IOException e) {
            return -1;
        }

        bb.flip();
        return bytesRead;
    }

}

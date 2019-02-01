package com.yeening.wal;

import com.yeening.record.DataRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class FileLogWriter implements LogWriter {

    private File file;
    private int sequenceNumber;
    private FileChannel fileChannel;

    public FileLogWriter(String dir, int sequenceNumber) throws FileNotFoundException {
        Objects.requireNonNull(dir);

        this.sequenceNumber = sequenceNumber;
        this.file = new File(dir + "/wal_" + sequenceNumber);
        this.fileChannel = new FileOutputStream(this.file, true).getChannel();
    }

    public synchronized void append(DataRecord record) throws IOException {

        LogRecord logRecord = new LogRecord(record.toByteArray());

        ByteBuffer bb = logRecord.toByteBuffer();
        bb.flip();

        fileChannel.write(bb);

    }

    public File getFile() {
        return file;
    }

    public void close() throws IOException {
        fileChannel.force(true);
        fileChannel.close();
    }
}

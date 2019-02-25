package com.yeening.sstable;

import java.nio.channels.FileChannel;

public class SSTableWriter {

    private DataBlockWriter dataBlockWriter;
    private IndexBlockWriter indexBlockWriter;

    public SSTableWriter() {

    }

    public void append(byte[] key, byte[] value, int sequenceNumber) {
        // Write to data block
    }

    public void flush() {
        // Flush data block out to disk

        // Create and write to index block
    }
}

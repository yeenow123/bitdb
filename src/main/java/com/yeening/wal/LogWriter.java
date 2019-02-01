package com.yeening.wal;

import com.yeening.record.DataRecord;

import java.io.File;
import java.io.IOException;

interface LogWriter {

    void append(DataRecord record) throws IOException;

    File getFile();

    void close() throws IOException;

}

package com.yeening.wal;

public interface LogReader {

    LogRecord readRecord() throws Exception;

    public long getOffset();
}

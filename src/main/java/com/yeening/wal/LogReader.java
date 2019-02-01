package com.yeening.wal;

import com.yeening.record.DataRecord;

public interface LogReader {

    DataRecord readRecord() throws Exception;

    public long getOffset();
}

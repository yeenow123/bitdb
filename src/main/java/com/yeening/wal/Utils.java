package com.yeening.wal;

import java.util.zip.CRC32;

public class Utils {
    public static long calculateChecksum(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }
}

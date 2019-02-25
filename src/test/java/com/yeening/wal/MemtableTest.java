package com.yeening.wal;

import com.yeening.memtable.Memtable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MemtableTest {

    @Test
    public void readWriteToMemtable() {

        Memtable memtable = new Memtable();

        String key = "abc";
        String value1 = "123";

        memtable.set(1, key.getBytes(), value1.getBytes());

        byte[] readValue1 = memtable.get(key.getBytes(), 1);

        assertEquals(new String(readValue1), value1);

        String value2 = "124";

        memtable.set(2, key.getBytes(), value2.getBytes());

        byte[] readValue2 = memtable.get(key.getBytes(), 2);

        assertEquals(new String(readValue2), value2);
    }

    @Test
    public void readLatestFromMemtable() {
        Memtable memtable = new Memtable();

        String key = "abc";
        String value1 = "123";
        String value2 = "124";

        memtable.set(1, key.getBytes(), value1.getBytes());
        memtable.set(4, key.getBytes(), value2.getBytes());

        byte[] ret = memtable.get(key.getBytes(), 2);

        assertEquals(new String(ret), value2);

        assertNull(memtable.get(key.getBytes(), 5));
    }
}

package com.yeening.wal;

import com.yeening.record.DataRecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

public class WALTest {

    @Test
    public void writeReadRecord() throws IOException, Exception {

        Path tempDirWithPrefix = Files.createTempDirectory("mydb");

        System.out.println("Directory: " + tempDirWithPrefix);

        FileLogWriter writer = new FileLogWriter(tempDirWithPrefix.toString(), 0);

        DataRecord record1 = createSampleRecord(20);
        DataRecord record2 = createSampleRecord(70);

        writer.append(record1);
        writer.append(record2);

        FileInputStream fis = new FileInputStream(writer.getFile());
        FileLogReader reader = new FileLogReader(fis.getChannel());

        LogRecord deserializedRecord1 = reader.readRecord();
        LogRecord deserializedRecord2 = reader.readRecord();

        assertTrue(Arrays.equals(record1.toByteArray(), deserializedRecord1.data));
        assertTrue(Arrays.equals(record2.toByteArray(), deserializedRecord2.data));

        reader.readRecord();
    }

    private DataRecord createSampleRecord(int dataLength) {
        byte[] b = new byte[dataLength];
        new Random().nextBytes(b);
        return new DataRecord(b);
    }
}

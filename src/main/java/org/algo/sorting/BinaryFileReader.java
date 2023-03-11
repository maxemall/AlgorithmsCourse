package org.algo.sorting;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryFileReader {
    public static long[] read(String pathname) throws IOException {
        int n = 10_000_000; // 10^7

        // читаем числа из файла
        File file = new File(pathname);
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        long[] numbers = new long[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = dis.readUnsignedShort(); // читаем два байта и преобразуем в 16-битное целое число
        }
        dis.close();
        fis.close();

        return numbers;

    }
}

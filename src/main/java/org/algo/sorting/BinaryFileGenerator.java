package org.algo.sorting;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class BinaryFileGenerator {
    public static void main(String[] args) throws IOException {
        int n = 10_000_000; // 10^7

        // создаем файл для записи
        File file = new File("numbers.bin");
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);

        // генерируем и записываем числа в файл
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int number = random.nextInt(65536); // генерируем число от 0 до 65535
            dos.writeShort(number); // записываем число в два байта
        }

        // закрываем потоки
        dos.close();
        fos.close();
    }
}

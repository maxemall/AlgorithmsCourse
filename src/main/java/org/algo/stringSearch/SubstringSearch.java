package org.algo.stringSearch;

import java.util.Arrays;import java.util.Random;public class SubstringSearch {

    public static void main(String[] args){

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 100_000_000; i++) {
            if (random.nextBoolean()) stringBuilder.append('a'); else stringBuilder.append('b');
        }

        testSearch(stringBuilder, 2);

        stringBuilder = new StringBuilder();
        for(int i = 0; i < 100_000_000; i++) {
            int nextInt = random.nextInt(33);
            stringBuilder.append(Character.toChars( nextInt + 'A'));
        }

        testSearch(stringBuilder, 33);

    } private static void testSearch(StringBuilder stringBuilder, int symbols) {
    char[] substring = new char[10];
        stringBuilder.replace(50_000_000, 50_000_001, "cc" );
        stringBuilder.getChars(50_000_000, 50_000_010, substring, 0);
        String sub = String.valueOf(substring);


        long start = System.currentTimeMillis();
        System.out.println(searchSubstringBMH(stringBuilder.toString(), sub));
        long time = System.currentTimeMillis() - start;
        System.out.println(String.format("Searching searchSubstringBMH alphabet %s symbols %s time", symbols, time));

        start = System.currentTimeMillis();
        System.out.println(searchSubstring(stringBuilder.toString(), sub));
        time = System.currentTimeMillis() - start;
        System.out.println(String.format("Searching searchSubstring alphabet %s symbols %s time", symbols, time));

        start = System.currentTimeMillis();
        System.out.println(searchSubstringReverse(stringBuilder.toString(), sub));
        time = System.currentTimeMillis() - start;
        System.out.println(String.format("Searching searchSubstringReverse alphabet %s symbols %s time", symbols, time));
}

    public static int searchSubstring(String text, String substring) {
        char[] textArray = text.toCharArray();
        char[] substringArray = substring.toCharArray();
        for(int i = 0; i <= textArray.length - substringArray.length; i++) {
            int m = 0;
            while (m < substringArray.length && substringArray[m] == textArray[i + m] ) {
                m++;
            }
            if (m >= substringArray.length) return i;
        }
        return -1;
    }

    public static int searchSubstringReverse(String text, String substring) {
        char[] textArray = text.toCharArray();
        char[] substringArray = substring.toCharArray();
        int substringMaxIndex = substringArray.length - 1;
        for(int i = textArray.length - 1; i >= substringArray.length; i--) {
            int m = substringMaxIndex;
            while (m > 0 && substringArray[m] == textArray[i - substringMaxIndex + m] ) {
                m--;
            }
            if (m <= 0) return i - substringMaxIndex;
        }
        return -1;
    }

    public static int searchSubstringBMH(String text, String substring) {
        char[] textArray = text.toCharArray();
        char[] substringArray = substring.toCharArray();
        if (textArray.length < substringArray.length) return -1;
        int[] offsetTable = createOffsetTable(substringArray);
        int substringMaxIndex = substringArray.length - 1;
        int currentIndex = substringMaxIndex - 1;


        while (currentIndex < textArray.length  ){
            int m = substringMaxIndex;
            while (m >= 0 && substringArray[m] == textArray[currentIndex - substringMaxIndex + m] ) {
                m--;
            }
            if (m <= 0) return currentIndex - substringMaxIndex;
            currentIndex += offsetTable[textArray[currentIndex]];
        }
        return -1;
    }

    public static int[] createOffsetTable(char[] substringArray) {
        int[] offset =  new int[128];
        Arrays.fill(offset, substringArray.length);

        for (int i = 0; i < substringArray.length - 1; i++) {
            offset[substringArray[i]] = substringArray.length - i - 1;
        }

        return offset;
    }
}

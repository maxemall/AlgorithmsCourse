package org.algo;

/**
 * Счастливые билеты 20
 *
 * <p>Билет с 2N значным номером считается счастливым, если сумма N первых цифр равна сумме
 * последних N цифр. Посчитать, сколько существует счастливых 2N-значных билетов.
 *
 * <p>Начальные данные: число N от 1 до 10. Вывод результата: количество 2N-значных счастливых
 * билетов.
 */
public class HappyTicket {

    public static void main(String[] args) {

        long[] sumArray = new long[1];
        sumArray[0] = 1;
        long sum = countVariants(sumArray, 1, 4);
        System.out.println(sum);
    }

    /** Решение задачи по аналогии с примером в Excel, показанном на занятии
     * @param sumArray количество возможных комбинаций. номер элемента в массиве это сумма чисел билета
     * @param step текущий шаг
     * @param maxStep количество цифр в билете
     * @return количество вариантов
     */
    public static long countVariants(long[] sumArray, int step, int maxStep) {

        long[][] array = new long[10][(1+step * 9)];
        long[] newSumArray = new long[1+step * 9];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (i == j) {
                    for (int i1 = 0; i1 < sumArray.length; i1++) {
                        array[i][j+i1] = sumArray[i1];
                    }
                }
                newSumArray[j] = newSumArray[j] + array[i][j];
            }
        }
        if (maxStep == step) return sqrSumArray(newSumArray);
        return countVariants(newSumArray, step + 1, maxStep);
    }

    /**
     * @param sumArray массив возможного количества комбинаций, дающих в сумме номер элемента массива
     * @return общее количество комбинаций цифр
     */
    private static long sqrSumArray(long[] sumArray) {
        long result = 0;
        for (long l : sumArray) {
            result = result + l * l;
        }
        return result;
    }
}

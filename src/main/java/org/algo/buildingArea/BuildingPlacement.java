package org.algo.buildingArea;

import java.util.Arrays;
import java.util.Random;

public class BuildingPlacement {

    public static void main(String[] args) {
        int[] sizes = {1000, 2000, 5000, 10000, 20000};
        int[] fillFactors = {10, 20, 50, 100};

        for (int N : sizes) {
            for (int F : fillFactors) {
                System.out.println("N = " + N + ", F = " + F);
                boolean[][] obstacles = generateObstacles(N, F);

//                // Решение с перебором площадей и битовыми маттрицами (не пошло)
//                long startTime = System.nanoTime();
//                int maxArea = getMaxBuildingAreaNaive(N, obstacles);
//                long endTime = System.nanoTime();
//                long durationNaive = endTime - startTime;

                // Решение с использованием гистограммы
                long startTime = System.nanoTime();
                int maxAreaHistogram = getMaxBuildingAreaHistogram(N, obstacles);
                long endTime = System.nanoTime();
                long durationHistogram = endTime - startTime;

                // Вывод результатов
//                System.out.println("Максимальная площадь постройки (перебор): " + maxArea);
//                System.out.println("Время выполнения (перебор): " + durationNaive + " нс");

                System.out.println("Максимальная площадь постройки (гистограмма): " + maxAreaHistogram);
                System.out.println("Время выполнения (гистограмма): " + durationHistogram + " нс");

                System.out.println("---------------------------------");
            }
        }
    }

    // Генерация случайного массива препятствий
    private static boolean[][] generateObstacles(int N, int fillFactor) {
        boolean[][] obstacles = new boolean[N][N];
    Random random = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                obstacles[i][j] = random.nextInt(100) < fillFactor;
            }
        }

        return obstacles;
    }
    private static int getMaxBuildingAreaNaive(int N, boolean[][] obstacles) {

        int maxArea = 0; // Максимальная площадь постройки

        // Преобразование входного массива obstacles в целочисленное значение obstaclesMask
        int obstaclesMask = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                obstaclesMask |= (obstacles[i][j] ? 1 : 0) << (i * N + j);
            }
        }
//        System.out.println("Маска obstaclesMask: " + Integer.toBinaryString(obstaclesMask));

        // Перебор площадей построек от больших к меньшим
        for (int height = N; height >= 1; height--) {
            for (int width = N; width >= 1; width--) {
                // Проверка ограничений границ
                if (height <= N && width <= N && height * width > maxArea) {
                    int mask = generateMask(height, width, N); // Генерация битовой маски для текущего размера
//                    System.out.println("Маска для размера " + height + "x" + width + ": " + Integer.toBinaryString(mask));

                    boolean found = false; // Флаг, указывающий на нахождение первой площади

                    for (int i = 0; i <= N - height && !found; i++) {
                        for (int j = 0; j <= N - width && !found; j++) {
                            boolean isPlaceable = isNoObstacle(obstaclesMask, mask, height, width, i, j); // Проверка наложения маски

                            if (isPlaceable) {
                                int area = height * width; // Вычисление площади постройки
//                                System.out.println("Текущая площадь постройки: " + area);
                                maxArea = Math.max(maxArea, area); // Обновление максимальной площади
                                found = true; // Установка флага, чтобы остановить перебор площадей
                            }
                        }
                    }
                }
            }
        }

//        System.out.println("Максимальная площадь постройки: " + maxArea);
        return maxArea;
    }

    // Генерация битовой маски для прямоугольника заданной высоты и ширины
    private static int generateMask(int height, int width, int N) {
        int mask = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mask |= 1 << (i * N + j);
            }
        }

        return mask;
    }

    // Проверка наложения маски на участок с препятствиями
    private static boolean isNoObstacle(int obstaclesMask, int mask, int height, int width, int row, int col) {
        int shift = row * height + col;

        int maskToCheck = mask << shift;

        return (obstaclesMask & maskToCheck) == 0;
    }

    // Решение с использованием гистограммы
    private static int getMaxBuildingAreaHistogram(int N, boolean[][] obstacles) {
        int maxArea = 0;

        int[] histogram = new int[N];
        for (int i = 0; i < N; i++) {
            histogram[i] = obstacles[0][i] ? 0 : 1;
        }
        maxArea = getMaxAreaFromHistogram(histogram);

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (obstacles[i][j]) {
                    histogram[j] = 0;
                } else {
                    histogram[j] += 1;
                }
            }
            maxArea = Math.max(maxArea, getMaxAreaFromHistogram(histogram));
        }

        return maxArea;
    }

    // Получение максимальной площади из гистограммы
    private static int getMaxAreaFromHistogram(int[] histogram) {
        int maxArea = 0;
        int N = histogram.length;

        int[] left = new int[N];
        int[] right = new int[N];

        Arrays.fill(right, N);

        int[] stack = new int[N];
        int top = -1;

        for (int i = 0; i < N; i++) {
            while (top >= 0 && histogram[stack[top]] >= histogram[i]) {
                right[stack[top]] = i;
                top--;
            }
            left[i] = (top >= 0) ? stack[top] : -1;
            stack[++top] = i;
        }

        for (int i = 0; i < N; i++) {
            int area = histogram[i] * (right[i] - left[i] - 1);
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}

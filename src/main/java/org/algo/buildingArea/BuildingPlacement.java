package org.algo.buildingArea;

public class BuildingPlacement {
    public static void main(String[] args) {
        int N = 3; // Размер участка (квадратная матрица NxN)

        // Пример участка с препятствиями (true - препятствие, false - свободное место)
        boolean[][] obstacles = {
                {false, false, false},
                {false, false, true},
                {true, false, true}
        };

        int maxArea = 0; // Максимальная площадь постройки

        // Преобразование входного массива obstacles в целочисленное значение obstaclesMask
        int obstaclesMask = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                obstaclesMask |= (obstacles[i][j] ? 1 : 0) << (i * N + j);
            }
        }
        System.out.println("Маска obstaclesMask: " + Integer.toBinaryString(obstaclesMask));


        // Перебор площадей построек от больших к меньшим
        for (int height = N; height >= 1; height--) {
            for (int width = N; width >= 1; width--) {
                // Проверка ограничений границ
                if (height <= N && width <= N) {
                    int mask = generateMask(height, width, N); // Генерация битовой маски для текущего размера
                    System.out.println("Маска для размера " + height + "x" + width + ": " + Integer.toBinaryString(mask));

                    for (int i = 0; i <= N - height; i++) {
                        for (int j = 0; j <= N - width; j++) {
                            boolean isPlaceable = isNoObstacle(obstaclesMask, mask, height, width, i, j); // Проверка наложения маски на участок

                            if (isPlaceable) {
                                int area = height * width; // Вычисление площади постройки
                                System.out.println("Текущая площадь постройки: " + area);
                                maxArea = Math.max(maxArea, area); // Обновление максимальной площади
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Максимальная площадь постройки: " + maxArea);
    }

    // Генерация битовой маски для прямоугольника заданной высоты и ширины
    private static int generateMask(int height, int width, int N) {
        int mask = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int bitPosition = j + i * N;
                mask |= (1 << bitPosition);
            }
        }

        return mask;
    }

    private static boolean isNoObstacle(int obstacles, int mask, int height, int width, int i, int j) {
        int N = Integer.bitCount(obstacles); // Количество битов в obstacles

        int maskShifted = mask << (i * N + j); // Сдвигаем маску в соответствии с текущим положением


        System.out.println("Маска fieldMask: " + Integer.toBinaryString(obstacles));
        System.out.println("Маска mask: " + Integer.toBinaryString(maskShifted));

        int overlap = obstacles & maskShifted; // Проверка наложения маски на поле побитовым умножением (AND)
        return overlap == 0;
    }
}

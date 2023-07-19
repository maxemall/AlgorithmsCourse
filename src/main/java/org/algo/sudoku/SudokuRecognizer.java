package org.algo.sudoku;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class SudokuRecognizer {

    public int[][] recognizeSudokuFromImage(File imageFile) {
        int[][] sudokuMatrix = new int[9][9];

        try {
            ITesseract tesseract = new Tesseract();
            System.setProperty("jna.library.path", "/opt/homebrew/Cellar/tesseract/5.3.2/lib");
//            tesseract.setDatapath("src/main/java/org/algo/sudoku/tesseract/tessdata/"); // Укажите путь к папке с
            // tessdata
            tesseract.setLanguage("eng"); // Указываем язык для распознавания (английский)

            String recognizedText = tesseract.doOCR(imageFile);

            // Преобразуем распознанный текст в матрицу int[][]
            String[] lines = recognizedText.split("\\r?\\n");
            for (int row = 0; row < 9; row++) {
                String[] numbers = lines[row].split(" ");
                for (int col = 0; col < 9; col++) {
                    sudokuMatrix[row][col] = Integer.parseInt(numbers[col]);
                }
            }
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return sudokuMatrix;
    }

    public static void main(String[] args) {
        SudokuRecognizer recognizer = new SudokuRecognizer();
        File imageFile = new File("src/main/java/org/algo/sudoku/tesseract/4х4-1.png"); // Замените на путь к вашему изображению судоку
        int[][] sudokuMatrix = recognizer.recognizeSudokuFromImage(imageFile);

        // Выводим результат распознавания
        for (int[] row : sudokuMatrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}

package example.com.lab3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class StripedMatrixMultiplication {
    public static ResultMatrix multiply(Matrix matrixA, Matrix matrixB, int threadCount) {
        Matrix transposedB = matrixB.transpose(); // Транспонована матриця B
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        ResultMatrix resultMatrix = new ResultMatrix(matrixA.getRows(), matrixA.getCols());

        for (int row = 0; row < matrixA.getRows(); row++) {
            final int[] rowA = matrixA.getRow(row); // Отримуємо рядок A
            final int currentRow = row;
            executor.execute(() -> {
                for (int col = 0; col < matrixB.getCols(); col++) {
                    int value = calculateCell(rowA, transposedB.getRow(col));
                    resultMatrix.set(currentRow, col, value);
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        return resultMatrix;
    }

    // Окремий метод для обчислення скалярного добутку двох рядків
    private static int calculateCell(int[] rowA, int[] rowB) {
        int sum = 0;
        for (int k = 0; k < rowA.length; k++) {
            sum += rowA[k] * rowB[k];
        }
        return sum;
    }
}

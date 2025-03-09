package example.com.lab3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class StripedMatrixMultiplication {
    public static ResultMatrix multiply(Matrix matrixA, Matrix matrixB, int threadCount) {
        Matrix transposedB = matrixB.transpose(); // Транспонована матриця B
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        ResultMatrix resultMatrix = new ResultMatrix(matrixA.getRows(), matrixA.getCols());
        for (int row = 0; row < matrixA.getRows(); row++) {
            final int currentRow = row;
            executor.execute(() -> {
                for (int col = 0; col < matrixB.getCols(); col++) {
                    int value = calculateCell(matrixA, transposedB, currentRow, col);
                    resultMatrix.set(currentRow, col, value);
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {};
        return resultMatrix;
    }

    // Окремий метод для обчислення скалярного добутку рядка і стовпця
    private static int calculateCell(Matrix matrixA, Matrix transposedB, int row, int col) {
        int sum = 0;
        for (int k = 0; k < matrixA.getCols(); k++) {
            sum += matrixA.get(row, k) * transposedB.get(col, k);
        }
        return sum;
    }
}



//
//package example.com.lab3;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//class StripedMatrixMultiplication {
//    public static void multiply(Matrix matrixA, Matrix matrixB, Matrix resultMatrix, int threadCount) {
//        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
//        for (int row = 0; row < matrixA.getRows(); row++) {
//            final int currentRow = row;
//            executor.execute(() -> {
//                for (int col = 0; col < matrixB.getCols(); col++) {
//                    int sum = 0;
//                    for (int k = 0; k < matrixA.getCols(); k++) {
//                        sum += matrixA.get(currentRow, k) * matrixB.get(k, col);
//                    }
//                    resultMatrix.set(currentRow, col, sum);
//                }
//            });
//        }
//        executor.shutdown();
//        while (!executor.isTerminated()) {}
//    }
//}

//
//import example.com.lab3.Matrix;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//class StripedMatrixMultiplication {
//    public static void multiply(Matrix matrixA, Matrix matrixB, Matrix resultMatrix, int threadCount) {
//        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
//        int rowsPerThread = (int) Math.ceil((double) matrixA.getRows() / threadCount);
//
//        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
//            final int startRow = threadIndex * rowsPerThread;
//            final int endRow = Math.min(startRow + rowsPerThread, matrixA.getRows());
//
//            executor.execute(() -> {
//                for (int row = startRow; row < endRow; row++) {
//                    for (int col = 0; col < matrixB.getCols(); col++) {
//                        int sum = 0;
//                        for (int k = 0; k < matrixA.getCols(); k++) {
//                            sum += matrixA.get(row, k) * matrixB.get(k, col);
//                        }
//                        resultMatrix.set(row, col, sum);
//                    }
//                }
//            });
//        }
//
//        executor.shutdown();
//        try {
//            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}

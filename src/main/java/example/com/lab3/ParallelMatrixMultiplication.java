package example.com.lab3.task1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Matrix {
    private final int[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    public static Matrix generate(int rows, int cols) {
        Random random = new Random();
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.data[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    public int get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, int value) {
        data[row][col] = value;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}

class StripedMatrixMultiplication {
    public static void multiply(Matrix matrixA, Matrix matrixB, Matrix resultMatrix, int threadCount) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int row = 0; row < matrixA.getRows(); row++) {
            final int currentRow = row;
            executor.execute(() -> {
                for (int col = 0; col < matrixB.getCols(); col++) {
                    int sum = 0;
                    for (int k = 0; k < matrixA.getCols(); k++) {
                        sum += matrixA.get(currentRow, k) * matrixB.get(k, col);
                    }
                    resultMatrix.set(currentRow, col, sum);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }
}

class FoxMatrixMultiplication {
    public static void multiply(Matrix matrixA, Matrix matrixB, Matrix resultMatrix, int threadCount) {
        int matrixSize = matrixA.getRows();
        int blockSize = Math.max(1, matrixSize / 4);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int rowBlock = 0; rowBlock < matrixSize; rowBlock += blockSize) {
            for (int colBlock = 0; colBlock < matrixSize; colBlock += blockSize) {
                final int startRow = rowBlock;
                final int startCol = colBlock;
                executor.execute(() -> {
                    for (int i = startRow; i < startRow + blockSize; i++) {
                        for (int j = startCol; j < startCol + blockSize; j++) {
                            int sum = 0;
                            for (int k = 0; k < matrixA.getCols(); k++) {
                                sum += matrixA.get(i, k) * matrixB.get(k, j);
                            }
                            resultMatrix.set(i, j, resultMatrix.get(i, j) + sum);
                        }
                    }
                });
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }
}

class SequentialMatrixMultiplication {
    public static void multiply(Matrix matrixA, Matrix matrixB, Matrix resultMatrix) {
        for (int i = 0; i < matrixA.getRows(); i++) {
            for (int j = 0; j < matrixB.getCols(); j++) {
                int sum = 0;
                for (int k = 0; k < matrixA.getCols(); k++) {
                    sum += matrixA.get(i, k) * matrixB.get(k, j);
                }
                resultMatrix.set(i, j, sum);
            }
        }
    }
}

public class ParallelMatrixMultiplication {
    public static void main(String[] args) {
        int[] sizes = {500, 1000, 2000};
        int[] threadCounts = {100, 200, 500};

        for (int size : sizes) {
            for (int threads : threadCounts) {
                System.out.println("Matrix Size: " + size + "x" + size + " | Threads: " + threads);
                Matrix matrixA = Matrix.generate(size, size);
                Matrix matrixB = Matrix.generate(size, size);

                Matrix resultSeq = new Matrix(size, size);
                long start = System.nanoTime();
                SequentialMatrixMultiplication.multiply(matrixA, matrixB, resultSeq);
                long end = System.nanoTime();
                System.out.println("Sequential Time: " + (end - start) / 1e6 + " ms");

                Matrix resultStriped = new Matrix(size, size);
                start = System.nanoTime();
                StripedMatrixMultiplication.multiply(matrixA, matrixB, resultStriped, threads);
                end = System.nanoTime();
                System.out.println("Striped Algorithm Time: " + (end - start) / 1e6 + " ms");

                Matrix resultFox = new Matrix(size, size);
                start = System.nanoTime();
                FoxMatrixMultiplication.multiply(matrixA, matrixB, resultFox, threads);
                end = System.nanoTime();
                System.out.println("Fox Algorithm Time: " + (end - start) / 1e6 + " ms");

                System.out.println("------------------------------------------------");
            }
        }
    }
}
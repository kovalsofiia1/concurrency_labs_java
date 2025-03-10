package example.com.lab3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FoxMatrixMultiplication {
    public static ResultMatrix multiply(Matrix matrixA, Matrix matrixB, int threadCount) {
        int matrixSize = matrixA.getRows();
        int blockSize = Math.max(1, matrixSize / (int) Math.sqrt(threadCount));
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        ResultMatrix resultMatrix = new ResultMatrix(matrixA.getRows(), matrixA.getCols());

        for (int rowBlock = 0; rowBlock < matrixSize; rowBlock += blockSize) {
            for (int colBlock = 0; colBlock < matrixSize; colBlock += blockSize) {
                for (int k = 0; k < matrixA.getCols(); k += blockSize) {
                    final int[][] blockA = matrixA.getBlock(rowBlock / blockSize, k / blockSize, blockSize);
                    final int[][] blockB = matrixB.getBlock(k / blockSize, colBlock / blockSize, blockSize);
                    final int blockRow = rowBlock / blockSize;
                    final int blockCol = colBlock / blockSize;

                    executor.execute(() -> {
                        int[][] blockResult = multiplyBlock(blockA, blockB, blockSize);
                        resultMatrix.addBlock(blockResult, blockRow, blockCol);
                    });
                }
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        return resultMatrix;
    }

    private static int[][] multiplyBlock(int[][] blockA, int[][] blockB, int blockSize) {
        int[][] blockResult = new int[blockSize][blockSize];

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                int sum = 0;
                for (int x = 0; x < blockSize; x++) {
                    sum += blockA[i][x] * blockB[x][j];
                }
                blockResult[i][j] = sum;
            }
        }
        return blockResult;
    }
}

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
                final int startRow = rowBlock;
                final int startCol = colBlock;
                executor.execute(() -> multiplyBlock(matrixA, matrixB, resultMatrix, startRow, startCol, blockSize));
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        return resultMatrix;
    }

    private static void multiplyBlock(Matrix matrixA, Matrix matrixB, Matrix resultMatrix, int startRow, int startCol, int blockSize) {
        for (int k = 0; k < matrixA.getCols(); k += blockSize) {
            int[][] blockA = matrixA.getBlock(startRow / blockSize, k / blockSize, blockSize);
            int[][] blockB = matrixB.getBlock(k / blockSize, startCol / blockSize, blockSize);
            int[][] blockResult = new int[blockSize][blockSize];

            for (int i = 0; i < blockSize && startRow + i < matrixA.getRows(); i++) {
                for (int j = 0; j < blockSize && startCol + j < matrixA.getCols(); j++) {
                    int sum = 0;
                    for (int x = 0; x < blockSize && k + x < matrixA.getCols(); x++) {
                        sum += blockA[i][x] * blockB[x][j];
                    }
                    blockResult[i][j] = sum;
                }
            }
            resultMatrix.addBlock(blockResult, startRow / blockSize, startCol / blockSize);
        }
    }
}

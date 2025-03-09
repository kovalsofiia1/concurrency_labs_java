package example.com.lab3;

import java.util.Random;

public class Matrix {
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

    // Метод для транспонування матриці
    public Matrix transpose() {
        Matrix transposed = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed.set(j, i, data[i][j]);
            }
        }
        return transposed;
    }
    public void addToCell(int row, int col, int value) {
        data[row][col] += value;
    }

    public int[][] getBlock(int rowBlock, int colBlock, int blockSize) {
        int[][] block = new int[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                int row = rowBlock * blockSize + i;
                int col = colBlock * blockSize + j;
                if (row < rows && col < cols) { // Ensure we are within the bounds of the matrix
                    block[i][j] = data[row][col];
                } else {
                    block[i][j] = 0; // If out of bounds, fill with 0 (for non-square matrices)
                }
            }
        }
        return block;
    }

    public void addBlock(int[][] block, int rowBlock, int colBlock) {
        int blockSize = block.length; // Assume square block (blockSize x blockSize)
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                int row = rowBlock * blockSize + i;
                int col = colBlock * blockSize + j;
                if (row < rows && col < cols) { // Ensure the indices are within bounds
                    data[row][col] += block[i][j]; // Add the block value to the corresponding position
                }
            }
        }
    }



}

package example.com.lab3;

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
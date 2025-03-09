package example.com.lab3;

public class ParallelMatrixMultiplication {
    public static void main(String[] args) {

        int[] sizes = {500, 800, 1000, 1500, 2000, 2500};
        int[] threadCounts = {4, 16, 64, 128};


        for (int size : sizes) {
            Matrix matrixA = Matrix.generate(size, size);
            Matrix matrixB = Matrix.generate(size, size);

            Matrix resultSeq = new Matrix(size, size);
            long start = System.nanoTime();
            SequentialMatrixMultiplication.multiply(matrixA, matrixB, resultSeq);
            long end = System.nanoTime();
            double seqTime = (end - start) / 1e6;

            for (int threads : threadCounts) {
                System.out.println("Matrix Size: " + size + "x" + size + " | Threads: " + threads);
                System.out.println("Sequential Time: " + seqTime + " ms");
                start = System.nanoTime();
                ResultMatrix resStriped = StripedMatrixMultiplication.multiply(matrixA, matrixB, threads);
                end = System.nanoTime();
//                resStriped.print();
                double strippedTime = (end - start) / 1e6;
                System.out.println("Striped Algorithm Time: " + strippedTime + " ms" + " | Speed up: " + seqTime / strippedTime);

                start = System.nanoTime();
                ResultMatrix resFox = FoxMatrixMultiplication.multiply(matrixA, matrixB, threads);
                end = System.nanoTime();
//                resFox.print();
                double foxTime = (end - start) / 1e6;
                System.out.println("Fox Algorithm Time: " + foxTime + " ms"+ " | Speed up: " + seqTime / foxTime);

                System.out.println("------------------------------------------------");
            }
        }
    }
}
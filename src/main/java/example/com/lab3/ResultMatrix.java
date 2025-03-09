package example.com.lab3;

public class ResultMatrix extends Matrix {
    public ResultMatrix(int rows, int cols) {
        super(rows, cols);
    }

    // You can add specific methods if needed, like result validation or formatting
    public void print() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }
}

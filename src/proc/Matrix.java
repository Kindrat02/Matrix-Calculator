package proc;
public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] matrix;

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensity must be positive!");
        }
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getElement(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            throw new IllegalArgumentException("ERROR: Try to access not existed element of matrix!");
        }
        return matrix[row][col];
    }

    public void setElement(double value, int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            throw new IllegalArgumentException("ERROR: Try to access to not existed element of matrix!");
        }
        matrix[row][col] = value;
    }

    public Matrix add(Matrix addition) {
        if (rows != addition.getRows() || cols != addition.getCols()) {
            System.out.println("ERROR: Sorry, we can`t perform such operation. Matrix dimensity must be the same!");
            return null;
        }

        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                double value = matrix[i][j] + addition.getElement(i, j);
                result.setElement(value, i, j);
            }
        }

        return result;
    }

    public Matrix multiply(double constant) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                double value = matrix[i][j] * constant;
                result.setElement(value, i, j);
            }
        }

        return result;
    }

    public Matrix multiply(Matrix multiplier) {
        if (cols != multiplier.getRows() || rows != multiplier.getCols()) {
            System.out.println("Sorry, but we can`t perform multiplication for matrix. For matrix multiplication, the number of columns in the first matrix must be equal to the number of rows in the second matrix");
            return null;
        }

        Matrix result = new Matrix(rows, multiplier.getCols());
        for (int i = 0; i < result.getRows(); ++i) {
            for (int j = 0; j < result.getCols(); ++j) {
                double value = 0;
                for (int k = 0; k < this.cols; ++k) {
                    value += matrix[i][k] * multiplier.getElement(k, j);
                }
                result.setElement(value, i, j);
            }
        }

        return result;
    }

    public Matrix transposeMainDiagonal() {
        Matrix transposedMatrix = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix.setElement(matrix[i][j], j, i);
            }
        }
        return transposedMatrix;
    }

    public double findDeterminant() {

        return matrixDeterminant(this);
    }

    private double matrixDeterminant (Matrix m) {

        double result = 0;

        if (m.getRows() == 1) {
            return m.matrix[0][0];
        }

        if (m.getCols() == 2) {
            return m.matrix[0][0] * m.matrix[1][1] - m.matrix[0][1] * m.matrix[1][0];
        }

        for (int i = 0; i < m.getCols(); i++) {
            Matrix temporary = deleteRowAndCol(m, 0, i);
            result += m.getElement(0, i) * Math.pow (-1, i) * matrixDeterminant (temporary);
        }
        return result;
    }

    private Matrix deleteRowAndCol(Matrix m, int rowToDelete, int colToDelete) {
        Matrix temporary = new Matrix(m.rows - 1, m.cols - 1);

        int currRow = 0;
        for (int i = 0; i < m.getRows(); i++) {
            int currCol = 0;
            for (int j = 0; j < m.getCols(); j++) {
                if (i != rowToDelete && j != colToDelete) {
                    temporary.setElement(m.matrix[i][j], currRow, currCol);
                    ++currCol;
                }
            }
            if (i != rowToDelete) {
                ++currRow;
            }
        }

        return temporary;
    }

    public Matrix getInverseMatrix() {
        double det = findDeterminant();
        if (det == 0) {
            return null;
        }
        Matrix adjugateMatrix = getAdjugateMatrix();

        Matrix adjugateT = adjugateMatrix.transposeMainDiagonal();

        return adjugateT.multiply(1 / det);
    }

    private Matrix getAdjugateMatrix() {
        Matrix adjugateMatrix = new Matrix(rows, cols);
        for (int row = 0; row < matrix.length; ++row) {
            for (int col = 0; col < matrix[0].length; col++) {
                Matrix temporary = deleteRowAndCol(this, row, col);

                double result = Math.pow(-1, row + col) * matrixDeterminant(temporary);
                adjugateMatrix.setElement(result, row, col);
            }
        }

        return adjugateMatrix;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                string.append(String.format("%8.2f", matrix[i][j])).append(" ");
            }
            string.append("\n");
        }

        return string.toString();
    }
}


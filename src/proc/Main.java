package proc;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String[] menuItems = {"1. Add matrices",
            "2. Multiply matrix by a constant",
            "3. Multiply matrices",
            "4. Transpose matrix",
            "5. Calculate a determinant",
            "6. Inverse matrix",
            "0. Exit"};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Your choice: ");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    performAddition();
                    break;
                case 2:
                    performMultiplicationByNumber();
                    break;
                case 3:
                    performMatrixMultiplication();
                    break;
                case 4:
                    performTransposition();
                    break;
                case 5:
                    findDeterminant();
                    break;
                case 6:
                    performInversion();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Sorry, but you`ve chosen non-existing menu item");
            }
        }
    }

    private static int[] getMatrixSize() {
        Scanner scan = new Scanner(System.in);
        int n = 0, m = 0;
        do {
            System.out.print("Enter matrix size: ");
            try {
                n = scan.nextInt();
                m = scan.nextInt();
            } catch (InputMismatchException ex) {
                //clear the buffer
                scan.nextLine();
                System.out.println("Please enter 2 INTEGERS, not word");
            }
        } while(n <= 0 || m <= 0);

        return new int[]{n, m};
    }

    private static void fillMatrix(Matrix matrix) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter matrix:");
        int n = matrix.getRows();
        int m = matrix.getCols();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                double value = scan.nextDouble();
                matrix.setElement(value, i, j);
            }
            scan.nextLine();
        }
    }

    private static void printMenu() {
        for (String item: menuItems) {
            System.out.println(item);
        }
    }

    private static void performAddition() {
        int[] arraySize = getMatrixSize();
        Matrix A = new Matrix(arraySize[0], arraySize[1]);
        fillMatrix(A);

        arraySize = getMatrixSize();
        Matrix B = new Matrix(arraySize[0], arraySize[1]);
        fillMatrix(B);

        Matrix result = A.add(B);
        if (result != null) {
            System.out.println(result);
        }
    }

    private static void performMultiplicationByNumber() {
        Scanner scan = new Scanner(System.in);
        int[] arraySize = getMatrixSize();

        Matrix A = new Matrix(arraySize[0], arraySize[1]);
        fillMatrix(A);

        System.out.println("Enter constant: ");
        double constant = scan.nextDouble();

        System.out.println("The result is: ");
        System.out.println(A.multiply(constant));
    }

    private static void performMatrixMultiplication() {
        int[] arraySize = getMatrixSize();
        Matrix A = new Matrix(arraySize[0], arraySize[1]);
        fillMatrix(A);

        arraySize = getMatrixSize();
        Matrix B = new Matrix(arraySize[0], arraySize[1]);
        fillMatrix(B);

        Matrix product = A.multiply(B);
        if (product != null) {
            System.out.println("The result is: ");
            System.out.println(product);
        }
    }

    private static void performTransposition() {
        int[] sizeArray = getMatrixSize();
        Matrix m = new Matrix(sizeArray[0], sizeArray[1]);
        fillMatrix(m);

        Matrix transposedMatrix = m.transposeMainDiagonal();

        System.out.println("The result is: ");
        System.out.println(transposedMatrix);
    }

    private static void findDeterminant() {
        int[] matrixSize = getMatrixSize();
        if (matrixSize[0] != matrixSize[1]) {
            System.out.println("Sorry, we can`t find a determinant for non-square matrix");
            return;
        }

        Matrix A = new Matrix(matrixSize[0], matrixSize[1]);
        System.out.println("Enter first matrix:");
        fillMatrix(A);


        System.out.println("The result is: ");
        System.out.println(A.findDeterminant());
    }

    private static void performInversion() {
        int[] matrixSize = getMatrixSize();
        if (matrixSize[0] != matrixSize[1]) {
            System.out.println("Sorry, we can`t find an inverse matrix for non-square matrix");
            return;
        }

        Matrix A = new Matrix(matrixSize[0], matrixSize[1]);
        System.out.println("Enter matrix:");
        fillMatrix(A);


        System.out.println("The result is: ");
        System.out.println(A.getInverseMatrix());
    }
}
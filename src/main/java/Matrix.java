import java.util.Arrays;
import java.util.Random;

public class Matrix implements IMatrix {

    private final float[][] elements;

    //конструктор, що створює пусту матрицю
    public Matrix() {
        elements = new float[0][0];
    }

    //конструктор, що створює матрицю заданого розміру
    public Matrix(int m, int n) {
        if (m < 0 || n < 0)
            throw new NegativeArraySizeException("Row or column value is negative");
        else
            elements = new float[m][n];
    }

    //конструктор, що створює копію іншої матриці
    public Matrix(IMatrix copiedMatrix) {
        elements = new float[copiedMatrix.getRows()][copiedMatrix.getColumns()];
        for (int i = 0; i < copiedMatrix.getRows(); i++)
            elements[i] = Arrays.copyOf(copiedMatrix.getMatrix()[i], copiedMatrix.getMatrix()[i].length);
    }

    //заповнює матрицю значеннями
    @Override
    public void setElem(int m, int n, float elem) {
        if (m < 0 || n < 0)
            throw new NegativeArraySizeException("Row or column value is negative");
        if (m >= elements.length || n >= elements[0].length)
            throw new ArrayIndexOutOfBoundsException("Row or column value out of matrix dimension");
        else
            elements[m][n] = elem;
    }

    //заповнює матрицю випадковими значеннями
    @Override
    public void setRandomElem() {
        if (elements.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Dimension of matrix is 0");
        }
        else {
            Random setRandom = new Random();
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[0].length; j++) {
                    elements[i][j] = setRandom.nextFloat(10);
                }
            }
        }
    }

    @Override
    public void fillElem(float[][] matrix){
        for (int i = 0; i < elements.length; i++)
            System.arraycopy(matrix[i], 0, elements[i], 0, elements[0].length);
    }

    //перевизначений метод equals
    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(getClass() != other.getClass())
            return false;
        Matrix newMatrix = (Matrix) other;
        return (elements.length == newMatrix.elements.length && elements[0].length == newMatrix.elements[0].length && Arrays.deepEquals(elements, newMatrix.elements));
    }

    //повертає кількість рядків
    @Override
    public int getRows(){
        return elements.length;
    }

    //повертає кількість стовпців
    @Override
    public int getColumns(){
        return elements[0].length;
    }

    //Повертає матрицю
    @Override
    public float[][] getMatrix() {
        return elements;
    }

}

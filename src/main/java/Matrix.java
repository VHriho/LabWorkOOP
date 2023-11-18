import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
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
    public void fillElem(float[][] matrix) {
        for (int i = 0; i < elements.length; i++)
            System.arraycopy(matrix[i], 0, elements[i], 0, elements[0].length);
    }

    //повертає елемент на претині рядка і стовпчика
    @Override
    public float getElem(int m, int n) {
        if (m < 0 || n < 0)
            throw new NegativeArraySizeException("Row or column value is negative");
        if (m >= elements.length || n >= elements[0].length)
            throw new ArrayIndexOutOfBoundsException("Row or column value out of matrix dimension");
        else
            return elements[m][n];
    }

    //повертає заданий рядок
    @Override
    public float[] getRow(int m) {
        if (m < 0)
            throw new NegativeArraySizeException("Row value is negative");
        if (m >= elements.length)
            throw new ArrayIndexOutOfBoundsException("Row value out of matrix dimension");
        else
            return elements[m];

    }

    //повертає заданий стовпчик
    @Override
    public float[] getColumn(int n) {
        if (n < 0)
            throw new NegativeArraySizeException("Column value is negative");
        if (n >= elements[0].length)
            throw new ArrayIndexOutOfBoundsException("Column value out of matrix dimension");
        else {
            float[] column = new float[elements[0].length];
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[0].length; j++) {
                    if (j == n)
                        column[i] = elements[i][j];
                }
            }
            return column;
        }
    }

    //повертає розмірність матриці
    @Override
    public HashMap<String, Integer> getDimension() {
        HashMap<String, Integer> map = new HashMap<>();
        if (elements.length == 0 || elements[0].length == 0) {
            map.put("Rows", 0);
            map.put("Columns", 0);
        }
        else {
            map.put("Rows", elements.length);
            map.put("Columns", elements[0].length);
        }
        return map;
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

    //перевизначений метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(elements.length, elements[0].length, Arrays.deepHashCode(elements), 31);
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

    //сума матриць
    @Override
    public IMatrix sumMatrix(IMatrix matrix) {
        if (this.getRows() != matrix.getRows() || this.getColumns() != matrix.getColumns())
            throw new IllegalArgumentException("Matrices must have an equal number of columns and rows");
        Matrix sumOfMatrix = new Matrix(matrix.getRows(), matrix.getColumns());
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                sumOfMatrix.setElem(i, j, this.getElem(i, j) + matrix.getElem(i, j));
            }
        }
        return sumOfMatrix;
    }

    //множення на скаляр
    @Override
    public IMatrix multMatrix(float mult) {
        Matrix resMult = new Matrix(this.elements.length, this.elements[0].length);
        for (int i = 0; i < this.elements.length; i++) {
            for (int j = 0; j < this.elements[0].length; j++) {
                resMult.setElem(i, j, elements[i][j] * mult);
            }
        }
        return resMult;
    }

    //множення матриць
    @Override
    public IMatrix multiplication(IMatrix matrix) {
        if (this.getColumns() != matrix.getColumns())
            throw new IllegalArgumentException("Matrices must have an equal number of columns and rows");
        Matrix multiplct = new Matrix(this.getRows(), matrix.getColumns());
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                multiplct.elements[i][j] = 0;
                for (int a = 0; a < this.getColumns(); a++) {
                    multiplct.elements[i][j] += this.elements[i][a] * matrix.getElem(a,j);
                }
            }
        }
        return multiplct;
    }

    //транспонування матриці
    @Override
    public IMatrix transposeMatrix() {
        Matrix transpose = new Matrix(this.elements.length, this.elements[0].length);
        for (int i = 0; i < this.elements.length; i++)  {
            for (int j = 0; j < this.elements[0].length; j++) {
                transpose.elements[i][j] = this.elements[j][i];
            }
        }
        return transpose;
    }

    //Повертає матрицю
    @Override
    public float[][] getMatrix() {
        return elements;
    }

}
